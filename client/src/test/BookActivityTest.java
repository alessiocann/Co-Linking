package test;

import main.CoLinking;
import main.controller.WorkstationController;
import main.model.Activity;
import main.model.Booking;
import main.model.CreditCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookActivityTest {

    private static CoLinking coLinking = CoLinking.getInstance();
    private static Activity activity;

    @BeforeAll
    static void setUp() {
        activity = new Activity("ST", 1, 1, 1, 100.0f, 50.0f, 200.0f);
        coLinking.setActivityMap("ST", activity);
    }

    @Test
    void testBookActivity_Accommodation() {
        int days = 3;
        Float totalPrice = coLinking.bookActivity("ST", 1, 0, 0, days);

        assertNotNull(totalPrice);
        assertEquals(100.0f * days, totalPrice);

        assertTrue(coLinking.getBookActivityMap().containsKey(1));

        System.out.println("Accommodation booking test passed.");
    }

    @Test
    void testBookActivity_Lunch() {
        int days = 2;
        Float totalPrice = coLinking.bookActivity("ST", 0, 1, 0, days);

        assertNotNull(totalPrice);
        assertEquals(50.0f * days, totalPrice);

        assertTrue(coLinking.getBookActivityMap().containsKey(1));

        System.out.println("Lunch booking test passed.");
    }

    @Test
    void testBookActivity_TeamBuilding() {
        int days = 2;
        Float totalPrice = coLinking.bookActivity("ST", 0, 0, 1, days);

        assertNotNull(totalPrice);
        assertEquals(50.0f * days, totalPrice);

        assertTrue(coLinking.getBookActivityMap().containsKey(1));

        System.out.println("Team Building booking test passed.");
    }

    @Test
    void testBookActivityMultipleServices() {
        int days = 1;
        Float totalPrice = coLinking.bookActivity("ST", 1, 1, 1, days);

        assertNotNull(totalPrice);
        assertEquals(200.0f * days, totalPrice);

        assertTrue(coLinking.getBookActivityMap().containsKey(1));

        System.out.println("Multiple services booking test passed. ");
    }
}
