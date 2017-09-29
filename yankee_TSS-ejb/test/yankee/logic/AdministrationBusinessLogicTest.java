/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
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
public class AdministrationBusinessLogicTest {
    private static EJBContainer ejbContainer;
    private static Context ctx;
    private static AdministrationBusinessLogic logic;
    public AdministrationBusinessLogicTest() {
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
        logic = (AdministrationBusinessLogic) ctx.lookup("java:global/classes/AdministrationBusinessLogicImpl");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAdminSetState method, of class AdministrationBusinessLogic.
     */
    @Test
    public void testGetAdminSetState() {
        Administration administration = logic.getAdminSetState();
        assertEquals(GermanyStatesEnum.values(), administration.getGermanState());
    }

    /**
     * Test of updateState method, of class AdministrationBusinessLogic.
     */
//    @Test
//    public void testUpdateState() {
//        System.out.println("updateState");
//        GermanyStatesEnum ge = null;
//        AdministrationBusinessLogic instance = new AdministrationBusinessLogicImpl();
//        instance.updateState(ge);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    public class AdministrationBusinessLogicImpl implements AdministrationBusinessLogic {
//
//        public Administration getAdminSetState() {
//            return null;
//        }
//
//        public void updateState(GermanyStatesEnum ge) {
//        }
//    }
//    
}
