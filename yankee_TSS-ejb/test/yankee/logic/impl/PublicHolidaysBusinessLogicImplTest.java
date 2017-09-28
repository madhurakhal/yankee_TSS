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
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.to.PublicHolidays;

/**
 *
 * @author T540P
 */
public class PublicHolidaysBusinessLogicImplTest {
    
    public PublicHolidaysBusinessLogicImplTest() {
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
     * Test of getPublicHolidaysByState method, of class PublicHolidaysBusinessLogicImpl.
     */
    @Test
    public void testGetPublicHolidaysByState() throws Exception {
        System.out.println("getPublicHolidaysByState");
        GermanyStatesEnum state = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PublicHolidaysBusinessLogic instance = (PublicHolidaysBusinessLogic)container.getContext().lookup("java:global/classes/PublicHolidaysBusinessLogicImpl");
        List<PublicHolidays> expResult = null;
        List<PublicHolidays> result = instance.getPublicHolidaysByState(state);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPublicHolidays method, of class PublicHolidaysBusinessLogicImpl.
     */
    @Test
    public void testCreatePublicHolidays() throws Exception {
        System.out.println("createPublicHolidays");
        String name = "";
        GermanyStatesEnum stateName = null;
        int day = 0;
        int month = 0;
        int year = 0;
        int dayOfWeek = 0;
        String localName = "";
        String englishName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PublicHolidaysBusinessLogic instance = (PublicHolidaysBusinessLogic)container.getContext().lookup("java:global/classes/PublicHolidaysBusinessLogicImpl");
        instance.createPublicHolidays(name, stateName, day, month, year, dayOfWeek, localName, englishName);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of databaseEmpty method, of class PublicHolidaysBusinessLogicImpl.
     */
    @Test
    public void testDatabaseEmpty() throws Exception {
        System.out.println("databaseEmpty");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PublicHolidaysBusinessLogic instance = (PublicHolidaysBusinessLogic)container.getContext().lookup("java:global/classes/PublicHolidaysBusinessLogicImpl");
        boolean expResult = false;
        boolean result = instance.databaseEmpty();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPublicHoliday method, of class PublicHolidaysBusinessLogicImpl.
     */
    @Test
    public void testIsPublicHoliday() throws Exception {
        System.out.println("isPublicHoliday");
        int day = 0;
        int month = 0;
        int year = 0;
        GermanyStatesEnum state = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        PublicHolidaysBusinessLogic instance = (PublicHolidaysBusinessLogic)container.getContext().lookup("java:global/classes/PublicHolidaysBusinessLogicImpl");
        boolean expResult = false;
        boolean result = instance.isPublicHoliday(day, month, year, state);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
