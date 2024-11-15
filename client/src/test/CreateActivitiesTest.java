package test;

import main.CoLinking;
import main.controller.UserController;
import main.controller.WorkstationController;
import main.model.Host;
import main.model.Activity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.exception.WorkstationException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreateActivitiesTest {

    private static CoLinking coLinking = CoLinking.getInstance();
    private static Host host;
    private static UserController userController = new UserController();


    @BeforeAll
    static void setUp() {
        host = userController.setHost("1000294","reply", "via Marina", "Catania", "alessio@example.it","1234566");
    }

    @Test
    void testCreateActivities_Success() {
        assertDoesNotThrow(() -> {
            coLinking.createActivities(host, 1, 1, 1, 100.0f, 50.0f, 200.0f);
        });

        assertTrue(coLinking.getActivityMap().containsKey(host.getName()));
    }

    @Test
    void testCreateActivities_Exists() {
        coLinking.setActivityMap(host.getName(), new Activity("companyName", 5, 10, 3, 100.0f, 50.0f, 200.0f));
        WorkstationException exception = assertThrows(WorkstationException.class, () ->
                coLinking.createActivities(host, 1, 1, 1, 100.0f, 50.0f, 200.0f)
        );

        assertEquals("Activity already exist", exception.getMessage());
        System.out.println("Exception: " + exception.getMessage());
    }
}
