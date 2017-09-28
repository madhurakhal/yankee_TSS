/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import yankee.entities.ContractEntity;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;

/**
 *
 * @author T540P
 */
public class TimeSheetBusinessLogicImplTest {
    
    public TimeSheetBusinessLogicImplTest() {
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
     * Test of createTimeSheet method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testCreateTimeSheet() throws Exception {
        System.out.println("createTimeSheet");
        String contractUUID = "";
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        List<TimeSheet> expResult = null;
        List<TimeSheet> result = instance.createTimeSheet(contractUUID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUpdateTimeSheetEntry method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testAddUpdateTimeSheetEntry() throws Exception {
        System.out.println("addUpdateTimeSheetEntry");
        TimeSheetEntry obj = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        String expResult = "";
        String result = instance.addUpdateTimeSheetEntry(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTimeSheet method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testDeleteTimeSheet() throws Exception {
        System.out.println("deleteTimeSheet");
        String contractUuid = "";
        Boolean isTerminateContract = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        String expResult = "";
        String result = instance.deleteTimeSheet(contractUuid, isTerminateContract);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContractByTimesheetUUID method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testGetContractByTimesheetUUID() throws Exception {
        System.out.println("getContractByTimesheetUUID");
        String uuid = "";
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        ContractEntity expResult = null;
        ContractEntity result = instance.getContractByTimesheetUUID(uuid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllTimeSheetsForContract method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testGetAllTimeSheetsForContract() throws Exception {
        System.out.println("getAllTimeSheetsForContract");
        String contractUUID = "";
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        List<TimeSheet> expResult = null;
        List<TimeSheet> result = instance.getAllTimeSheetsForContract(contractUUID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntriesForTimeSheet method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testGetEntriesForTimeSheet() throws Exception {
        System.out.println("getEntriesForTimeSheet");
        String timeSheetUuid = "";
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        List<TimeSheetEntry> expResult = null;
        List<TimeSheetEntry> result = instance.getEntriesForTimeSheet(timeSheetUuid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitTimeSheet method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testSubmitTimeSheet_String_Boolean() throws Exception {
        System.out.println("submitTimeSheet");
        String uuid = "";
        Boolean submittedByEmp = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        String expResult = "";
        String result = instance.submitTimeSheet(uuid, submittedByEmp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTimeSheetEntry method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testDeleteTimeSheetEntry() throws Exception {
        System.out.println("deleteTimeSheetEntry");
        String uuid = "";
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        String expResult = "";
        String result = instance.deleteTimeSheetEntry(uuid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllTimeSheetsByGivenDate method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testGetAllTimeSheetsByGivenDate() throws Exception {
        System.out.println("getAllTimeSheetsByGivenDate");
        LocalDate givenDate = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        List<TimeSheet> expResult = null;
        List<TimeSheet> result = instance.getAllTimeSheetsByGivenDate(givenDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteOldTimeSheetSignedBySupervisor method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testDeleteOldTimeSheetSignedBySupervisor() throws Exception {
        System.out.println("deleteOldTimeSheetSignedBySupervisor");
        LocalDate oldDate = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        instance.deleteOldTimeSheetSignedBySupervisor(oldDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllTimeSheetsSignedBySupervisor method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testGetAllTimeSheetsSignedBySupervisor() throws Exception {
        System.out.println("getAllTimeSheetsSignedBySupervisor");
        LocalDate givenDate = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        List<TimeSheet> expResult = null;
        List<TimeSheet> result = instance.getAllTimeSheetsSignedBySupervisor(givenDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitTimeSheet method, of class TimeSheetBusinessLogicImpl.
     */
    @Test
    public void testSubmitTimeSheet_TimeSheet() throws Exception {
        System.out.println("submitTimeSheet");
        TimeSheet obj = null;
        TimeSheetBusinessLogicImpl instance = new TimeSheetBusinessLogicImpl();
        String expResult = "";
        String result = instance.submitTimeSheet(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
