package upc.dsa.minim1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import upc.dsa.minim1.models.*;
import java.util.*;
package EETACGame;

import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = UserManagerImpl.getInstance(); // Cambiado a UserManager
        gameManager.clear();

        // Añadir un usuario y un punto de interés para pruebas iniciales
        gameManager.addUser("1", "John", "Doe", "john.doe@example.com", new Date());
        gameManager.addUser("Jane", "Smith", "jane.smith@example.com", new Date());
        gameManager.addIPoint(0, 0, ElementType.DOOR);
    }

    @Test
    void testAddAndFindAllUsers() {
        List<User> users = gameManager.findAllUsers();

        // Comprobaciones
        assertEquals(2, users.size());
        assertEquals("Doe", users.get(0).getSname());
        assertEquals("Smith", users.get(1).getLastName());
    }

    @Test
    void testRegisterUserAtPoint() throws Exception {
        InterPoint ip = new InterPoint(0, 0, ElementType.DOOR);

        // Registrar usuario en el punto
        gameManager.UssersIP(ip);
        List<InterPoint> points = gameManager.UpassIP(gameManager.getUser("1"));

        // Comprobaciones
        assertEquals(1, points.size());
        assertEquals(ElementType.DOOR, points.get(0).getType());
    }

    @AfterEach
    void tearDown() {
        gameManager.clear();
    }
}
