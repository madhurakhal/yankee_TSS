/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.to.Administration;

/**
 *
 * @author T540P
 */
public class AdministrationBusinessLogicImplTest {
    
    public AdministrationBusinessLogicImplTest() {
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
     * Test of getAdminSetState method, of class AdministrationBusinessLogicImpl.
     */
    @Test
    public void testGetAdminSetState() throws Exception {
        System.out.println("getAdminSetState");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AdministrationBusinessLogic instance = (AdministrationBusinessLogic)container.getContext().lookup("java:global/classes/AdministrationBusinessLogicImpl");
        Administration expResult = null;
        Administration result = instance.getAdminSetState();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateState method, of class AdministrationBusinessLogicImpl.
     */
    @Test
    public void testUpdateState() throws Exception {
        System.out.println("updateState");
        GermanyStatesEnum ge = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AdministrationBusinessLogic instance = (AdministrationBusinessLogic)container.getContext().lookup("java:global/classes/AdministrationBusinessLogicImpl");
        instance.updateState(ge);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
