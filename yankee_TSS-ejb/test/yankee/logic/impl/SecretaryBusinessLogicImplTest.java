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
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;

/**
 *
 * @author T540P
 */
public class SecretaryBusinessLogicImplTest {
    
    public SecretaryBusinessLogicImplTest() {
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
     * Test of getSecretaryList method, of class SecretaryBusinessLogicImpl.
     */
    @Test
    public void testGetSecretaryList() throws Exception {
        System.out.println("getSecretaryList");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SecretaryBusinessLogic instance = (SecretaryBusinessLogic)container.getContext().lookup("java:global/classes/SecretaryBusinessLogicImpl");
        List<Secretary> expResult = null;
        List<Secretary> result = instance.getSecretaryList();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createSecretary method, of class SecretaryBusinessLogicImpl.
     */
    @Test
    public void testCreateSecretary() throws Exception {
        System.out.println("createSecretary");
        String name = "";
        String personUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SecretaryBusinessLogic instance = (SecretaryBusinessLogic)container.getContext().lookup("java:global/classes/SecretaryBusinessLogicImpl");
        Secretary expResult = null;
        Secretary result = instance.createSecretary(name, personUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSecretariesByContract method, of class SecretaryBusinessLogicImpl.
     */
    @Test
    public void testGetSecretariesByContract() throws Exception {
        System.out.println("getSecretariesByContract");
        String contractUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SecretaryBusinessLogic instance = (SecretaryBusinessLogic)container.getContext().lookup("java:global/classes/SecretaryBusinessLogicImpl");
        List<Secretary> expResult = null;
        List<Secretary> result = instance.getSecretariesByContract(contractUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSecretaryByUUID method, of class SecretaryBusinessLogicImpl.
     */
    @Test
    public void testGetSecretaryByUUID() throws Exception {
        System.out.println("getSecretaryByUUID");
        String UUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SecretaryBusinessLogic instance = (SecretaryBusinessLogic)container.getContext().lookup("java:global/classes/SecretaryBusinessLogicImpl");
        Secretary expResult = null;
        Secretary result = instance.getSecretaryByUUID(UUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPersonsUnderSecretary method, of class SecretaryBusinessLogicImpl.
     */
    @Test
    public void testGetPersonsUnderSecretary() throws Exception {
        System.out.println("getPersonsUnderSecretary");
        String secretaryPersonUUID = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SecretaryBusinessLogic instance = (SecretaryBusinessLogic)container.getContext().lookup("java:global/classes/SecretaryBusinessLogicImpl");
        List<Person> expResult = null;
        List<Person> result = instance.getPersonsUnderSecretary(secretaryPersonUUID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
