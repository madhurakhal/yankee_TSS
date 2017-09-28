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
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;

/**
 *
 * @author T540P
 */
public class AssistantBusinessLogicImplTest {
    
    public AssistantBusinessLogicImplTest() {
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
     * Test of getAssistantList method, of class AssistantBusinessLogicImpl.
     */
    @Test
    public void testGetAssistantList() throws Exception {
        System.out.println("getAssistantList");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AssistantBusinessLogic instance = (AssistantBusinessLogic)container.getContext().lookup("java:global/classes/AssistantBusinessLogicImpl");
        List<Assistant> expResult = null;
        List<Assistant> result = instance.getAssistantList();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createAssistant method, of class AssistantBusinessLogicImpl.
     */
    @Test
    public void testCreateAssistant() throws Exception {
        System.out.println("createAssistant");
        String name = "";
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AssistantBusinessLogic instance = (AssistantBusinessLogic)container.getContext().lookup("java:global/classes/AssistantBusinessLogicImpl");
        Assistant expResult = null;
        Assistant result = instance.createAssistant(name, personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAssistantsByContract method, of class AssistantBusinessLogicImpl.
     */
    @Test
    public void testGetAssistantsByContract() throws Exception {
        System.out.println("getAssistantsByContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AssistantBusinessLogic instance = (AssistantBusinessLogic)container.getContext().lookup("java:global/classes/AssistantBusinessLogicImpl");
        List<Assistant> expResult = null;
        List<Assistant> result = instance.getAssistantsByContract(contractUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAssistantByUUID method, of class AssistantBusinessLogicImpl.
     */
    @Test
    public void testGetAssistantByUUID() throws Exception {
        System.out.println("getAssistantByUUID");
        String UUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AssistantBusinessLogic instance = (AssistantBusinessLogic)container.getContext().lookup("java:global/classes/AssistantBusinessLogicImpl");
        Assistant expResult = null;
        Assistant result = instance.getAssistantByUUID(UUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonsUnderAssistant method, of class AssistantBusinessLogicImpl.
     */
    @Test
    public void testGetPersonsUnderAssistant() throws Exception {
        System.out.println("getPersonsUnderAssistant");
        String assistantPersonUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AssistantBusinessLogic instance = (AssistantBusinessLogic)container.getContext().lookup("java:global/classes/AssistantBusinessLogicImpl");
        List<Person> expResult = null;
        List<Person> result = instance.getPersonsUnderAssistant(assistantPersonUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
