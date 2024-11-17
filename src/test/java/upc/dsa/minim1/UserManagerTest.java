package upc.dsa.minim1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import upc.dsa.minim1.exceptions.InterestPointNotFoundException;
import upc.dsa.minim1.exceptions.UserNotFoundException;
import upc.dsa.minim1.models.*;
import java.util.*;
import upc.dsa.minim1.*;


import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    UserManager gameManager;

    @BeforeEach
    void setUp() throws UserNotFoundException, InterestPointNotFoundException {
        this.gameManager = UserImplementation.getInstance(); // Cambiado a UserManager
        this.gameManager.clear();

        // Añadir un usuario y un punto de interés para pruebas iniciales
        this.gameManager.addUser("1", "John", "Doe", "john.doe@example.com", "2/11/1856");
        this.gameManager.addUser("Jane", "Smith", "jane.smith@example.com", "1/11/1975");
        this.gameManager.addIPoint(15,15,ElemType.TREE);
        this.gameManager.addIPoint(0,0,ElemType.DOOR);
        this.gameManager.addUPP("1",15,15);
    }

    @Test
    void testAddAndFindAllUsers() {
        gameManager.addUser("3", "Anna", "Brown", "anna.brown@example.com", "5/11/1500");
        List<User> users = gameManager.findAllUsers();

        // Comprobar orden
        assertEquals(3, users.size());
        assertEquals("Brown", users.get(0).getSname());
        assertEquals("Doe", users.get(1).getSname());
        assertEquals("Smith", users.get(2).getSname());
    }

    @Test
    void testGetUser() {
        User user = gameManager.getUser("1");

        // Comprobaciones
        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSname());
        assertEquals("john.doe@example.com", user.getMail());
    }

    @Test
    void testGetUserNotFound() {
        User user = gameManager.getUser("99");

        // Comprobación
        assertNull(user);
    }
    @Test
    void testAddUPPWithInvalidUser() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            gameManager.addUPP("99", 15, 15); // Usuario no existe
        });

        assertEquals(UserNotFoundException.class, exception.getClass());
    }

    @Test
    void testAddUPPWithInvalidPoint() {
        Exception exception = assertThrows(InterestPointNotFoundException.class, () -> {
            gameManager.addUPP("1", 99, 99); // Punto no existe
        });

        assertEquals(InterestPointNotFoundException.class, exception.getClass());
    }

    @Test
    void testUserPassedMultiplePoints() throws Exception {
        gameManager.addUPP("1", 0, 0); // Registrar paso por segundo punto

        List<InterPoint> points = gameManager.UpassIP("1");

        // Verificar resultados
        assertEquals(2, points.size());
        assertTrue(points.stream().anyMatch(ip -> ip.getE() == ElemType.TREE));
        assertTrue(points.stream().anyMatch(ip -> ip.getE() == ElemType.DOOR));
    }


    @Test
    void testRegisterUserAtPoint() throws Exception {
        gameManager.addUPP("1", 0, 0); // Registrar paso

        List<InterPoint> points = gameManager.UpassIP("1");

        // Comprobaciones
        assertEquals(1, points.size());
        assertEquals(ElemType.DOOR, points.get(0).getE());
    }
    @Test
    void testFindPointsByType() {
        // Agregar más puntos de interés de diferentes tipos
        gameManager.addIPoint(5, 5, ElemType.BRIDGE);
        gameManager.addIPoint(10, 10, ElemType.TREE);

        // Consultar puntos de tipo TREE
        List<InterPoint> treePoints = gameManager.TypeIp(ElemType.TREE);

        // Verificar resultados
        assertEquals(2, treePoints.size()); // Deben ser dos puntos tipo TREE
        assertTrue(treePoints.stream().allMatch(ip -> ip.getE() == ElemType.TREE));
    }
    @Test
    void testDeleteUser() {
        // Antes de eliminar, verificar que hay 2 usuarios
        assertEquals(2, gameManager.Usize());

        // Eliminar un usuario
        gameManager.deleteUser("1");

        // Verificar que hay solo 1 usuario
        assertEquals(1, gameManager.Usize());

        // Intentar obtener el usuario eliminado
        User deletedUser = gameManager.getUser("1");
        assertNull(deletedUser);
    }

    @Test
    void testDeleteIPoint() {
        // Antes de eliminar, verificar que hay 2 usuarios
        assertEquals(2, gameManager.Psize());

        // Eliminar un usuario
        gameManager.deleteIPoint(5,5);

        // Verificar que hay solo 1 usuario
        assertEquals(1, gameManager.Usize());

        // Intentar obtener el usuario eliminado
        InterPoint deletedIPoint = gameManager.getIP(5,5);
        assertNull(deletedIPoint);
    }

    @Test
    void testUsersAtInterestPoint() throws Exception {
        // Agregar un nuevo usuario y registrar su paso por un punto de interés
        gameManager.addUser("2", "Alice", "Wonder", "alice.wonder@example.com", "1/11/6565");
        gameManager.addUPP("2", 15, 15); // Registrar en el punto (15,15)

        // Consultar usuarios en el punto (15,15)
        InterPoint point = new InterPoint(15, 15, ElemType.TREE);
        List<User> usersAtPoint = gameManager.UssersIP(point);

        // Verificar resultados
        assertEquals(2, usersAtPoint.size()); // Deben ser 2 usuarios
        assertTrue(usersAtPoint.stream().anyMatch(user -> user.getIdU().equals("1")));
        assertTrue(usersAtPoint.stream().anyMatch(user -> user.getIdU().equals("2")));
    }

    @AfterEach
    void tearDown() {
        gameManager.clear();
    }
}
