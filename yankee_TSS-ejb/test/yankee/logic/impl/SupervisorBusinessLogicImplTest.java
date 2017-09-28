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
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;

/**
 *
 * @author T540P
 */
public class SupervisorBusinessLogicImplTest {
    
    public SupervisorBusinessLogicImplTest() {
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
     * Test of getSupervisorList method, of class SupervisorBusinessLogicImpl.
     */
    @Test
    public void testGetSupervisorList() throws Exception {
        System.out.println("getSupervisorList");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SupervisorBusinessLogic instance = (SupervisorBusinessLogic)container.getContext().lookup("java:global/classes/SupervisorBusinessLogicImpl");
        List<Person> expResult = null;
        List<Person> result = instance.getSupervisorList();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createSupervisor method, of class SupervisorBusinessLogicImpl.
     */
    @Test
    public void testCreateSupervisor() throws Exception {
        System.out.println("createSupervisor");
        String name = "";
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SupervisorBusinessLogic instance = (SupervisorBusinessLogic)container.getContext().lookup("java:global/classes/SupervisorBusinessLogicImpl");
        Supervisor expResult = null;
        Supervisor result = instance.createSupervisor(name, personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSupervisorByPerson method, of class SupervisorBusinessLogicImpl.
     */
    @Test
    public void testGetSupervisorByPerson() throws Exception {
        System.out.println("getSupervisorByPerson");
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SupervisorBusinessLogic instance = (SupervisorBusinessLogic)container.getContext().lookup("java:global/classes/SupervisorBusinessLogicImpl");
        List<Supervisor> expResult = null;
        List<Supervisor> result = instance.getSupervisorByPerson(personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSupervisorByContract method, of class SupervisorBusinessLogicImpl.
     */
    @Test
    public void testGetSupervisorByContract() throws Exception {
        System.out.println("getSupervisorByContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SupervisorBusinessLogic instance = (SupervisorBusinessLogic)container.getContext().lookup("java:global/classes/SupervisorBusinessLogicImpl");
        Supervisor expResult = null;
        Supervisor result = instance.getSupervisorByContract(contractUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContracts method, of class SupervisorBusinessLogicImpl.
     */
    @Test
    public void testGetContracts() throws Exception {
        System.out.println("getContracts");
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SupervisorBusinessLogic instance = (SupervisorBusinessLogic)container.getContext().lookup("java:global/classes/SupervisorBusinessLogicImpl");
        List<Contract> expResult = null;
        List<Contract> result = instance.getContracts(personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonsUnderSupervisor method, of class SupervisorBusinessLogicImpl.
     */
    @Test
    public void testGetPersonsUnderSupervisor() throws Exception {
        System.out.println("getPersonsUnderSupervisor");
        String supervisorPersonUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SupervisorBusinessLogic instance = (SupervisorBusinessLogic)container.getContext().lookup("java:global/classes/SupervisorBusinessLogicImpl");
        List<Person> expResult = null;
        List<Person> result = instance.getPersonsUnderSupervisor(supervisorPersonUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
