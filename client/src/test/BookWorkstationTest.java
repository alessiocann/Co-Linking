package test;

import main.CoLinking;
import main.controller.UserController;
import main.controller.WorkstationController;
import main.exception.BookingException;
import main.model.CreditCard;
import main.model.User;
import main.model.Workstation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookWorkstationTest {

    private static CoLinking coLinking = CoLinking.getInstance();;
    private static UserController userController = new UserController();
    private static WorkstationController workstationController = new WorkstationController();
    private static User user;
    private static Workstation workstation;

    @BeforeAll
    static void init() {
        workstation = workstationController.setWorkstation("replyCatania", "reply",  10, "Catania", "Via Martiri", "good plsce", 100.0f );
         coLinking.setWorkstationMap(workstation.getId(), workstation);
    }

    @Test
    void testBookWorkstation_Success() throws BookingException {
        String date = "10/12/2024";
        workstationController.setCapacityByDate(workstation, date, 10);

        coLinking.bookWorkstation(user, workstation.getId(), date);

        assertEquals(1, coLinking.getPendingBookingMap().size());
        System.out.println("Booking request sent successfully.");
    }

    @Test
    void testBookWorkstation_WorkstationNotExist() {
        String invalidWorkstationId = "5";
        String date = "10/12/2024";

        BookingException err = assertThrows(BookingException.class, () -> {
            coLinking.bookWorkstation(user, invalidWorkstationId, date);
        });

        assertEquals("Workstation doesn't exist", err.getMessage());
    }

    @Test
    void testBookWorkstation_MaxCapacity() throws BookingException {
        String date = "10/12/2024";
        workstationController.setCapacityByDate(workstation, date, 0);

        BookingException err = assertThrows(BookingException.class, () -> {
            coLinking.bookWorkstation(user, workstation.getId(), date);
        });

        assertEquals("Maximum Capability", err.getMessage());
    }
}

