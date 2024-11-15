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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DeleteBookingTest {

    private static CoLinking coLinking = CoLinking.getInstance();
    private static WorkstationController workstationController;
    private static UserController userController;
    private static Map<Integer, Booking> bookingMap;
    private static Map<Integer, Booking> pendingBookingMap;
    private static Workstation workstation;
    private static User user;

    @BeforeAll
    static void setUp() {
        bookingMap = new HashMap<>();
        pendingBookingMap = new HashMap<>();
        workstationController = new WorkstationController();
        userController = new UserController();
        user = userController.setUser("cnnlss", "alessio", "alessio@example.it","1234567", new CreditCard("5355530303", "123", "10/10/2028"));

        workstation = workstationController.setWorkstation("stCatania", "ST", 20, "Catania", "via consoli", "good place", 50);

        coLinking.setBookingMap(1, new Booking(user, workstation, "23 Aprile 2025"));
        coLinking.setPendingBookingMap(2, new Booking(user, workstation, "23 Aprile 2025"));
    }

    @Test
    void testDeleteConfirmedBooking_Success() throws BookingException {
        coLinking.deleteBooking(1, 1);

        assertFalse(coLinking.getBookingMap().containsKey(1));

        int updatedCapacity = workstationController.getCapacityByDate(workstation, "23 Aprile 2025");
        assertEquals(21, updatedCapacity);

        System.out.println("Confirmed booking and activities deleted successfully.");
    }

    @Test
    void testDeletePendingBooking_Success() throws BookingException {
        coLinking.deleteBooking(1, 2);

        assertFalse(pendingBookingMap.containsKey(2));

        int updatedCapacity = workstationController.getCapacityByDate(workstation, "2024-01-01");
        assertEquals(20, updatedCapacity);

        System.out.println("Pending booking deleted successfully with capacity updated.");
    }

    @Test
    void testDeleteBooking_NotFound() {
        BookingException exception = assertThrows(BookingException.class, () ->
                coLinking.deleteBooking(1, 999)
        );

        assertEquals("Booking not found", exception.getMessage());
        System.out.println("Exception: " + exception.getMessage());
    }

    @Test
    void testDeleteBooking_Refuse() throws BookingException {
        coLinking.deleteBooking(2, 1);

        assertFalse(bookingMap.containsKey(1));

        System.out.println("Delete operation was rejected as expected.");
    }
}

