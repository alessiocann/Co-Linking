package test;

import main.CoLinking;
import main.controller.UserController;
import main.controller.WorkstationController;
import main.exception.BookingException;
import main.model.Booking;
import main.model.CreditCard;
import main.model.Workstation;
import main.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmBookingTest {

    private static CoLinking coLinking = CoLinking.getInstance();;
    private static WorkstationController workstationController;
    private static UserController userController;
    private static Workstation workstation;
    private static Booking booking;
    private static User user;

    @BeforeAll
    static void setUp() {
        workstationController = new WorkstationController();
        userController = new UserController();
        user = userController.setUser("cnnlss", "alessio", "alessio@example.it","1234567", new CreditCard("5355530303", "123", "10/10/2028"));

        workstation = workstationController.setWorkstation("stCatania", "ST", 20, "Catania", "via consoli", "good place", 50);

        booking = new Booking(user, workstation, "23 Aprile 2025");

        coLinking.setPendingBookingMap(1, new Booking(user, workstation, "23 Aprile 2025"));
    }

    @Test
    void testConfirmBooking_Success() throws BookingException {

        workstationController.setCapacityByDate(workstation, "23 Aprile 2025", 5);
        coLinking.confirmBooking(1, 1);
        assertTrue(coLinking.getBookingMap().containsKey(1));

        int remainingCapacity = workstationController.getCapacityByDate(workstation, "23 Aprile 2025");
        assertEquals(4, remainingCapacity);

        System.out.println("Booking confirmed successfully with remaining capacity: " + remainingCapacity);
    }

    @Test
    void testConfirmBooking_MaxCapacity() {
        workstationController.setCapacityByDate(workstation, "23 Aprile 2025", 0);

        BookingException exception = assertThrows(BookingException.class, () ->
                coLinking.confirmBooking(1, 1)
        );

        assertEquals("Maximum Capability", exception.getMessage());
        assertFalse(coLinking.getBookingMap().containsKey(1));
        System.out.println("Exception: " + exception.getMessage());
    }

    @Test
    void testConfirmBooking_Refuse() throws BookingException {
        coLinking.confirmBooking(2, 1);

        assertFalse(coLinking.getBookingMap().containsKey(1));

        assertFalse(coLinking.getPendingBookingMap().containsKey(1));

        System.out.println("Booking refused successfully.");
    }
}
