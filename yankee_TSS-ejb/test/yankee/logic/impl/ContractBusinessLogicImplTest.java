/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;

/**
 *
 * @author T540P
 */
public class ContractBusinessLogicImplTest {
    
    public ContractBusinessLogicImplTest() {
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
     * Test of getContractList method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testGetContractList() throws Exception {
        System.out.println("getContractList");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        List<Contract> expResult = null;
        List<Contract> result = instance.getContractList();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createContract method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testCreateContract() throws Exception {
        System.out.println("createContract");
        String contractName = "";
        Person supervisor = null;
        Person assistant = null;
        Person secretary = null;
        Person employee = null;
        Date startDate = null;
        Date endDate = null;
        TimesheetFrequencyEnum timesheetFrequency = null;
        double hoursPerWeek = 0.0;
        int workingDaysPerWeek = 0;
        int vacationDaysPerYear = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        Contract expResult = null;
        Contract result = instance.createContract(contractName, supervisor, assistant, secretary, employee, startDate, endDate, timesheetFrequency, hoursPerWeek, workingDaysPerWeek, vacationDaysPerYear);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editContract method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testEditContract() throws Exception {
        System.out.println("editContract");
        String contractUUID = "";
        Person supervisorPerson = null;
        List<Person> secretaries = null;
        boolean secretariesChanged = false;
        List<Person> assistants = null;
        boolean assistantsChanged = false;
        Date startDate = null;
        Date endDate = null;
        TimesheetFrequencyEnum timesheetFrequency = null;
        int workingDaysPerWeek = 0;
        int vacationDaysPerYear = 0;
        double hoursPerWeek = 0.0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        Contract expResult = null;
        Contract result = instance.editContract(contractUUID, supervisorPerson, secretaries, secretariesChanged, assistants, assistantsChanged, startDate, endDate, timesheetFrequency, workingDaysPerWeek, vacationDaysPerYear, hoursPerWeek);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startContract method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testStartContract() throws Exception {
        System.out.println("startContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        Contract expResult = null;
        Contract result = instance.startContract(contractUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of terminateContract method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testTerminateContract() throws Exception {
        System.out.println("terminateContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        instance.terminateContract(contractUUID);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContractByUUID method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testGetContractByUUID() throws Exception {
        System.out.println("getContractByUUID");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        Contract expResult = null;
        Contract result = instance.getContractByUUID(contractUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calledForContractArchive method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testCalledForContractArchive() throws Exception {
        System.out.println("calledForContractArchive");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        instance.calledForContractArchive(contractUUID);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateContractStatistics method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testUpdateContractStatistics() throws Exception {
        System.out.println("updateContractStatistics");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        instance.updateContractStatistics(contractUUID);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteContract method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testDeleteContract() throws Exception {
        System.out.println("deleteContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        instance.deleteContract(contractUUID);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContractsByPerson method, of class ContractBusinessLogicImpl.
     */
    @Test
    public void testGetContractsByPerson() throws Exception {
        System.out.println("getContractsByPerson");
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ContractBusinessLogic instance = (ContractBusinessLogic)container.getContext().lookup("java:global/classes/ContractBusinessLogicImpl");
        List<Contract> expResult = null;
        List<Contract> result = instance.getContractsByPerson(personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
