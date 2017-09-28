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
//    @Test
//    public void testGetPersonList() {
//        System.out.println("getPersonList");
//        PersonBusinessLogic instance = new PersonBusinessLogicImpl();
//        List<Person> expResult = null;
//        List<Person> result = instance.getPersonList();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

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
//    @Test
//    public void testUpdatePersonDetails() {
//        System.out.println("updatePersonDetails");
//        String uuid = "";
//        RoleTypeEnum roleType = null;
//        PersonBusinessLogic instance = new PersonBusinessLogicImpl();
//        instance.updatePersonDetails(uuid, roleType);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of updateUserRoleRealm method, of class PersonBusinessLogic.
     */
//    @Test
//    public void testUpdateUserRoleRealm() {
//        System.out.println("updateUserRoleRealm");
//        String uuid = "";
//        String realmRole = "";
//        PersonBusinessLogic instance = new PersonBusinessLogicImpl();
//        instance.updateUserRoleRealm(uuid, realmRole);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getPersonByName method, of class PersonBusinessLogic.
     */
//    @Test
//    public void testGetPersonByName() {
//        System.out.println("getPersonByName");
//        String name = "";
//        PersonBusinessLogic instance = new PersonBusinessLogicImpl();
//        Person expResult = null;
//        Person result = instance.getPersonByName(name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of updateDetails method, of class PersonBusinessLogic.
     */
//    @Test
//    public void testUpdateDetails() {
//        System.out.println("updateDetails");
//        Person updatedperson = null;
//        PersonBusinessLogic instance = new PersonBusinessLogicImpl();
//        instance.updateDetails(updatedperson);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of updatePhoto method, of class PersonBusinessLogic.
     */
//    @Test
//    public void testUpdatePhoto() {
//        System.out.println("updatePhoto");
//        Person photoupdated = null;
//        PersonBusinessLogic instance = new PersonBusinessLogicImpl();
//        instance.updatePhoto(photoupdated);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

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
