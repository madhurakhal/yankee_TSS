/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import yankee.logic.to.Employee;

/**
 *
 * @author T540P
 */
public class EmployeeBusinessLogicImplTest {
    
    public EmployeeBusinessLogicImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEmployeeList method, of class EmployeeBusinessLogicImpl.
     */
    @Test
    public void testGetEmployeeList() throws Exception {
        System.out.println("getEmployeeList");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EmployeeBusinessLogic instance = (EmployeeBusinessLogic)container.getContext().lookup("java:global/classes/EmployeeBusinessLogicImpl");
        List<Employee> expResult = null;
        List<Employee> result = instance.getEmployeeList();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEmployee method, of class EmployeeBusinessLogicImpl.
     */
    @Test
    public void testCreateEmployee() throws Exception {
        System.out.println("createEmployee");
        String name = "";
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EmployeeBusinessLogic instance = (EmployeeBusinessLogic)container.getContext().lookup("java:global/classes/EmployeeBusinessLogicImpl");
        Employee expResult = null;
        Employee result = instance.createEmployee(name, personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmployeeByContract method, of class EmployeeBusinessLogicImpl.
     */
    @Test
    public void testGetEmployeeByContract() throws Exception {
        System.out.println("getEmployeeByContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        EmployeeBusinessLogic instance = (EmployeeBusinessLogic)container.getContext().lookup("java:global/classes/EmployeeBusinessLogicImpl");
        Employee expResult = null;
        Employee result = instance.getEmployeeByContract(contractUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
