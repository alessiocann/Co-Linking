package test;

import main.CoLinking;
import main.controller.UserController;
import main.exception.WorkstationException;
import main.model.Host;
import main.model.Workstation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateWorkstationTest {

    private static CoLinking coLinking = CoLinking.getInstance();;
    private static UserController userController = new UserController();
    private static Host host;

    @BeforeAll
    static void init() {
        host = userController.setHost("1000294","reply", "via Marina", "Catania", "alessio@example.it","1234566");
    }

    @Test
    void testCreateWorkStation_Success() throws WorkstationException {
        coLinking.createWorkStation(host, "good place", 10, 100.5f);

        String id = "replyCatania";
        assertNotNull(coLinking.getWorkstationMap().get(id));
        System.out.println("Workstation has been created successfully.");
    }

    @Test
    void testCreateWorkStation_Exists() {
        String id = "replyCatania";
        coLinking.setWorkstationMap(id, new Workstation(id, "reply", 10, "Catania", "via Martiri", "good place", 100.0f));

        WorkstationException exception = assertThrows(WorkstationException.class, () ->
                coLinking.createWorkStation(host, "good place", 10, 100.0f)
        );

        assertEquals("Workstation already exist", exception.getMessage());
        System.out.println("Exception: " + exception.getMessage());
    }
}
