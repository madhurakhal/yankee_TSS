/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
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
public class AssistantBusinessLogicTest {
    private static EJBContainer ejbContainer;
    private static Context ctx;
    
    private AssistantBusinessLogic assistantBusinessLogic;
    private PersonBusinessLogic personBusinessLogic;
    
    public AssistantBusinessLogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ejbContainer = EJBContainer.createEJBContainer();
        System.out.println("Starting the container");
        ctx = ejbContainer.getContext();
    }
    
    @AfterClass
    public static void tearDownClass() {
        ejbContainer.close();
        System.out.println("Closing the container");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Before
    public void obtainBean() throws NamingException{
        personBusinessLogic = (PersonBusinessLogic) ctx.lookup("java:global/classes/PersonBusinessLogicImpl");
        assistantBusinessLogic = (AssistantBusinessLogic) ctx.lookup("java:global/classes/AssistantBusinessLogicImpl");
    }

    /**
     * Test of getAssistantList method, of class AssistantBusinessLogic.
     */
    @Test
    public void testGetAssistantList() {
        Person person1 = personBusinessLogic.createPerson("Biplov");
        Person person2 = personBusinessLogic.createPerson("KC");
        Assistant assistant1 = assistantBusinessLogic.createAssistant("ass1", person1.getUuid());
        Assistant assistant2 = assistantBusinessLogic.createAssistant("ass2", person2.getUuid());
        
        List<Assistant> assistants = assistantBusinessLogic.getAssistantList();
        assertEquals(true, assistants.contains(assistant1));
        assertEquals(true, assistants.contains(assistant2));
        assertEquals(2, assistants.size());
    }

    /**
     * Test of createAssistant method, of class AssistantBusinessLogic.
     */
    @Test
    public void testCreateAssistant() {
        Person person = personBusinessLogic.createPerson("Biplov");
        Assistant assistant = assistantBusinessLogic.createAssistant("Biplov", person.getUuid());
        assertEquals("Biplov", assistant.getName());
    }

    /**
     * Test of getAssistantsByContract method, of class AssistantBusinessLogic.
     */
//    @Test
//    public void testGetAssistantsByContract() {
//        System.out.println("getAssistantsByContract");
//        String contractUUID = "";
//        AssistantBusinessLogic instance = new AssistantBusinessLogicImpl();
//        List<Assistant> expResult = null;
//        List<Assistant> result = instance.getAssistantsByContract(contractUUID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getAssistantByUUID method, of class AssistantBusinessLogic.
     */
    @Test
    public void testGetAssistantByUUID() {
        Person person = personBusinessLogic.createPerson("Biplov");
        Assistant assistant = assistantBusinessLogic.createAssistant("Biplov", person.getUuid());
        String uuid = assistant.getUuid();
        Assistant assistant1 = assistantBusinessLogic.getAssistantByUUID(uuid);
        assertEquals(assistant.getName(), assistant1.getName());
    }

    /**
     * Test of getPersonsUnderAssistant method, of class AssistantBusinessLogic.
     */
//    @Test
//    public void testGetPersonsUnderAssistant() {
//        
//    }

//    public class AssistantBusinessLogicImpl implements AssistantBusinessLogic {
//
//        public List<Assistant> getAssistantList() {
//            return null;
//        }
//
//        public Assistant createAssistant(String name, String personUUID) {
//            return null;
//        }
//
//        public List<Assistant> getAssistantsByContract(String contractUUID) {
//            return null;
//        }
//
//        public Assistant getAssistantByUUID(String UUID) {
//            return null;
//        }
//
//        public List<Person> getPersonsUnderAssistant(String assistantPersonUUID) {
//            return null;
//        }
//    }
    
}
