/*package edu.wpi.cs3733.c22.teamB;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.cs3733.c22.teamB.entity.objects.Employee;
import edu.wpi.cs3733.c22.teamB.entity.EmployeeDBI;
import org.junit.jupiter.api.Test;

public class EmployeeDBTest {

                                EmployeeDBI employeeDBI = new EmployeeDBI();
                                Employee employee1 = new Employee("1", "n", "n", "n", "n", "n");
                                Employee employee2 = new Employee("2", "a", "b", "c", "d", "a");
                                Employee employee3 = new Employee("2", "d", "d", "e", "q", "q");
                                Employee employee4 = new Employee("4", "ha", "b", "c", "d", "a");
                                Employee emptyEmp = new Employee();

                                @Test
                                public void testGetNode() {
                                                                employeeDBI.initConnection("jdbc:derby:bDB;create=true", "admin", "admin");

                                                                assertEquals(employee1.toString(), employeeDBI.getNode("1").toString());

                                                                employeeDBI.closeConnection();
                                }

                                @Test
                                public void testInsertNode() {
                                                                employeeDBI.initConnection("jdbc:derby:bDB;create=true", "admin", "admin");

                                                                employeeDBI.insertNode(employee4);
                                                                assertEquals("4", employee4.getEmployeeID());

                                                                employeeDBI.closeConnection();
                                }

                                @Test
                                public void testInsertEmptyNode() {
                                                                employeeDBI.initConnection("jdbc:derby:bDB;create=true", "admin", "admin");

                                                                assertNull(emptyEmp.getEmployeeID());

                                                                employeeDBI.closeConnection();
                                }

                                @Test
                                public void testDeleteNode() {
                                                                employeeDBI.initConnection("jdbc:derby:bDB;create=true", "admin", "admin");

                                                                employeeDBI.deleteNode("4");
                                                                assertFalse(employeeDBI.getAllNodes().contains(employee4));

                                                                employeeDBI.closeConnection();
                                }

                                @Test
                                public void testUpdateNode() {
                                                                employeeDBI.initConnection("jdbc:derby:bDB;create=true", "admin", "admin");

                                                                employeeDBI.insertNode(employee2);
                                                                employeeDBI.updateNode(employee3);
                                                                assertEquals(employee3.toString(), employeeDBI.getNode("2").toString());
                                                                employeeDBI.deleteNode("2");

                                                                employeeDBI.closeConnection();
                                }
}
*/
