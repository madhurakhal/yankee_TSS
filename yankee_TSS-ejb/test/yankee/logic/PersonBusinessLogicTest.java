/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import yankee.logic.ENUM.PreferredLanguageENUM;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.to.Person;

/**
 *
 * @author T540P
 */
public class PersonBusinessLogicTest {
    
    private static EJBContainer ejbContainer;
    private static Context ctx;
    
    private PersonBusinessLogic personLogic;
    
    public PersonBusinessLogicTest() {
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
    public void obtainBean() throws NamingException{
        personLogic = (PersonBusinessLogic) ctx.lookup("java:global/classes/PersonBusinessLogicImpl");
    }
    /**
     * Test of getPersonList method, of class PersonBusinessLogic.
     */
    @Test
    public void testGetPersonList() {
        Person person1 = personLogic.createPerson("Test1");
        Person person2 = personLogic.createPerson("test2");
        List<Person> persons = personLogic.getPersonList();
        assertEquals(2, persons.size());
    }

    /**
     * Test of createPerson method, of class PersonBusinessLogic.
     */
    @Test
    public void testCreatePerson() {
        long cntBefore = personLogic.getPersonCount();
        
        Person testPerson = personLogic.createPerson("TestUser");
        assertEquals("TestUser", testPerson.getName());
        assertNotNull(testPerson.getUuid());
        long cnt = personLogic.getPersonCount();
        assertEquals(cntBefore + 1, cnt);
    }

    /**
     * Test of updatePersonDetails method, of class PersonBusinessLogic.
     */
    @Test
    public void testUpdatePersonDetails() {
        Person testPerson = personLogic.createPerson("Biplov");
        assertEquals("TestUser", testPerson.getName());
        testPerson.setEmailAddress("biplov@uni-koblenz.de");
        testPerson.setFirstName("Biplov");
        testPerson.setLastName("KC");
        testPerson.setPreferredLanguage(PreferredLanguageENUM.EN);
        personLogic.updateDetails(testPerson);
        assertEquals("Biplov", testPerson.getFirstName());
        assertEquals("KC", testPerson.getLastName());
        assertEquals("biplov@uni-koblenz.de", testPerson.getEmailAddress());
        assertEquals(PreferredLanguageENUM.EN, testPerson.getPreferredLanguage());
    }

    /**
     * Test of updateUserRoleRealm method, of class PersonBusinessLogic.
     */
    @Test
    public void testUpdateUserRoleRealm() {
        Person testPerson = personLogic.createPerson("Biplov");
        testPerson.setUserRoleRealm("abc");
        personLogic.updateUserRoleRealm(testPerson.getUuid(), "def");
        assertEquals("def", testPerson.getUserRoleRealm());

    }

    /**
     * Test of getPersonByName method, of class PersonBusinessLogic.
     */
    @Test
    public void testGetPersonByName() {
        Person testPerson = personLogic.createPerson("Biplov");
        Person person = personLogic.getPersonByName("Biplov");
        assertEquals(testPerson.getName(), person.getName());
    }

  

//    public class PersonBusinessLogicImpl implements PersonBusinessLogic {
//
//        public List<Person> getPersonList() {
//            return null;
//        }
//
//        public Person createPerson(String name) {
//            return null;
//        }
//
//        public void updatePersonDetails(String uuid, RoleTypeEnum roleType) {
//        }
//
//        public void updateUserRoleRealm(String uuid, String realmRole) {
//        }
//
//        public Person getPersonByName(String name) {
//            return null;
//        }
//
//        public void updateDetails(Person updatedperson) {
//        }
//
//        public void updatePhoto(Person photoupdated) {
//        }
//    }
    
}
