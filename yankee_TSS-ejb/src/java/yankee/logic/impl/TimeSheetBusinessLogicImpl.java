/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.ContractEntity;
import yankee.entities.TimesheetEntity;
import yankee.entities.TimesheetEntryEntity;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.PublicHolidaysBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.dao.TimeSheetEntryAccess;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;
import yankee.logic.to.TimesheetT;

/**
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de).
 * @version 1.0
 *
 * Service class containing methods for all TS1 - TS8 requirements of the
 * TimeSheetSystem.
 *
 */
@Stateless
public class TimeSheetBusinessLogicImpl implements TimeSheetBusinessLogic {

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private TimeSheetAccess timeSheetAccess;

    @EJB
    private TimeSheetEntryAccess timeSheetEntryAccess;

    @EJB
    private PublicHolidaysBusinessLogic publicHolidaysBusinessLogic;

    /**
     * Call this method when the Assistant starts the contract
     *
     * @param contractUUID
     * @param uuid specifying the unique identifier for the contract
     */
    
    // BEGINS .....  TO REVIEW Code For CreateTimeSheet.   
    @Override
    public List<TimesheetT> createTimeSheet(final String contractUUID) {
        // Steps for creating timesheet
        // 1. Get the contract for the contractUUID.
        // 2. Check if contract started.
        // 3. Check if timesheetfrequency exists
        // 4. case of timesheetFrequency 
        // 5. Create time sheet in INPROGRESS state

        //1.
        ContractEntity ce = contractAccess.getByUuid(contractUUID);

        //2.
        if (ce.getStatus().equals(ContractStatusEnum.STARTED)) {
            if (ce.getFrequency() != null) {
                // Gets the period between these days. Can access months day and year by just period.days()..
                //Period period = Period.between(ce.getStartDate(), ce.getEndDate());
                switch (ce.getFrequency()) {
                    case MONTHLY:
                        int monthsInPeriod = (int) ChronoUnit.MONTHS.between(ce.getStartDate(), ce.getEndDate());
                        for (int i = 0; i <= monthsInPeriod; i++) {
                            TimesheetEntity tsEntity = timeSheetAccess.createEntity("TimeSheet");
                            tsEntity.setStartDate(ce.getStartDate().plusMonths(i));
                            tsEntity.setEndDate(ce.getStartDate().plusMonths(i + 1).minusDays(1));                           
                            tsEntity.setContract(ce);

                            // BEGIN END Hours Due Calculation variable initialisation                                                        
                            int workingDaysInPeriod = 0;
                            int publicHolidaysInPeriod = 0; 
                            int workingDaysPerWeek = ce.getWorkingDaysPerWeek();
                            double hoursPerweek = ce.getHoursPerWeek();                            
                            List<DayOfWeek> validWorkingDays = _getWorkingDays(workingDaysPerWeek);
                            // END Hours Due Calculation variable initialisation

                            // Now Create Time Sheet Entry at the timesheet period.                            
                            Period tEntryPeriod = Period.between(tsEntity.getStartDate(), tsEntity.getEndDate());
                            for (int j = 0; j <= tEntryPeriod.getDays(); j++) {
                                TimesheetEntryEntity entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry");
                                LocalDate tsEntryDate = tsEntity.getStartDate().plusDays(j);
                                entryEntity.setEntryDate(tsEntryDate);                                                                
                                entryEntity.setTimesheet(tsEntity);

                                // BEGINS Hours Due Value update for each date. 
                                DayOfWeek tsEntryDay = tsEntryDate.getDayOfWeek();
                                if (validWorkingDays.contains(tsEntryDay)) {
                                    workingDaysInPeriod += 1;
                                    
                                    // Check if that current date is public holiday for the Given state. NOTE state is to obtained from tss setup.
                                    if(publicHolidaysBusinessLogic.databaseEmpty()){
                                       throw new IllegalStateException("Public Holidays not loaded in DATABASE. Ask Admin to load public Holidays"); 
                                    }
                                    if (publicHolidaysBusinessLogic.isPublicHoliday(tsEntryDate.getDayOfMonth(), tsEntryDate.getMonthValue(), tsEntryDate.getYear(), GermanyStatesEnum.BADENWURTTENMERG)) {
                                        System.out.println("IS IT PUBLICCCCCCCCC HOLIDAYyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" + tsEntryDate);                                        
                                        publicHolidaysInPeriod += 1;
                                    }
                                }
                                // Ends Hours Due Value update for each date.
                            }
                            
                            // Now I have "WorkingDaysInPeriod" "publicHoldaysInPeriod" "hoursPerWeek" "WorkingDaysPerWeek"
                            double hoursDue = (workingDaysInPeriod - publicHolidaysInPeriod) * (hoursPerweek / workingDaysPerWeek);
                            TimesheetEntity tse = timeSheetAccess.getByUuid(tsEntity.getUuid());
                            tse.setHoursDue(hoursDue);
                        }
                        break;

                    case WEEKLY:
                        int daysInPeriod = (int) ChronoUnit.DAYS.between(ce.getStartDate(), ce.getEndDate());
                        for (int i = 0; i <= daysInPeriod / 7; i++) {
                            TimesheetEntity tsEntity = timeSheetAccess.createEntity("TimeSheet");
                            tsEntity.setStartDate(ce.getStartDate().plusWeeks(i));
                            // The last days end date might just be few days and not week. End date = contract end date.
                            if (i == ((daysInPeriod / 7))) {
                                tsEntity.setEndDate(ce.getEndDate());
                            } else {
                                tsEntity.setEndDate(ce.getStartDate().plusWeeks(i + 1).minusDays(1));
                            }                            
                            tsEntity.setContract(ce);


                             // BEGIN END Hours Due Calculation variable initialisation                                                        
                            int workingDaysInPeriod = 0;
                            int publicHolidaysInPeriod = 0; 
                            int workingDaysPerWeek = ce.getWorkingDaysPerWeek();
                            double hoursPerweek = ce.getHoursPerWeek();                            
                            List<DayOfWeek> validWorkingDays = _getWorkingDays(workingDaysPerWeek);
                            // END Hours Due Calculation variable initialisation
                            
                            // Now Create Time Sheet Entry at the timesheet period.                            
                            Period tEntryPeriod = Period.between(tsEntity.getStartDate(), tsEntity.getEndDate());
                            for (int j = 0; j <= tEntryPeriod.getDays(); j++) {
                                TimesheetEntryEntity entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry");
                                LocalDate tsEntryDate = tsEntity.getStartDate().plusDays(j);
                                entryEntity.setEntryDate(tsEntryDate);
                                entryEntity.setTimesheet(tsEntity);
                                
                                // BEGINS Hours Due Value update for each date. 
                                DayOfWeek tsEntryDay = tsEntryDate.getDayOfWeek();
                                if (validWorkingDays.contains(tsEntryDay)) {
                                    workingDaysInPeriod += 1;
                                    
                                    // Check if that current date is public holiday for the Given state. NOTE state is to obtained from tss setup.
                                    if(publicHolidaysBusinessLogic.databaseEmpty()){
                                       throw new IllegalStateException("Public Holidays not loaded in DATABASE. Ask Admin to load public Holidays"); 
                                    }
                                    if (publicHolidaysBusinessLogic.isPublicHoliday(tsEntryDate.getDayOfMonth(), tsEntryDate.getMonthValue(), tsEntryDate.getYear(), GermanyStatesEnum.BADENWURTTENMERG)) {
                                        System.out.println("IS IT PUBLICCCCCCCCC HOLIDAYyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" + tsEntryDate);                                        
                                        publicHolidaysInPeriod += 1;
                                    }
                                }
                                // Ends Hours Due Value update for each date.
                            }
                            
                            // Now I have "WorkingDaysInPeriod" "publicHoldaysInPeriod" "hoursPerWeek" "WorkingDaysPerWeek"
                            double hoursDue = (workingDaysInPeriod - publicHolidaysInPeriod) * (hoursPerweek / workingDaysPerWeek);
                            TimesheetEntity tse = timeSheetAccess.getByUuid(tsEntity.getUuid());
                            tse.setHoursDue(hoursDue);
                        }
                        break;

                    default:
                        break;
                }
            }
        }
        return null;
    }
    // ENDS ..... TO REView Code for Create TimeSheet
    
    

    // Helper method. Takes working days. for example 4. Working days becomes Monday Tuesday Wednesday Thursday
    private List<DayOfWeek> _getWorkingDays(int workingDaysPerWeek) {
        List<DayOfWeek> workingDaysEnum = new ArrayList<>();
        if (workingDaysPerWeek <= 7) {

            for (int workday = 0; workday < workingDaysPerWeek; workday++) {
                switch (workday) {
                    case 0:
                        workingDaysEnum.add(DayOfWeek.MONDAY);
                        break;
                    case 1:
                        workingDaysEnum.add(DayOfWeek.TUESDAY);
                        break;
                    case 2:
                        workingDaysEnum.add(DayOfWeek.WEDNESDAY);
                        break;
                    case 3:
                        workingDaysEnum.add(DayOfWeek.THURSDAY);
                        break;
                    case 4:
                        workingDaysEnum.add(DayOfWeek.FRIDAY);
                        break;
                    case 5:
                        workingDaysEnum.add(DayOfWeek.SATURDAY);
                        break;
                    case 6:
                        workingDaysEnum.add(DayOfWeek.SUNDAY);
                        break;
                    default:
                        break;

                }
            }
        }
        return workingDaysEnum;
    }

    
//    @Override
//    public void createTimeSheet(final String uuid) {
//
//        long diff;
//        LocalDate timeSheetStartDate;
//        LocalDate timeSheetEndDate;
//        float weeks;
//        ContractEntity centity = null;
//        String contractStatus;
//        String timeSheetFrequency;
//        LocalDate startDate;
//        LocalDate endDate;
//        try {
//            if (centity != null) {
//                contractStatus = centity.getStatus().toString();
//                timeSheetFrequency =centity.getFrequency().toString();
//                startDate= centity.getStartDate();
//                endDate= centity.getEndDate();
//                
//                if (!contractStatus.equalsIgnoreCase(ContractStatusEnum.STARTED.toString())) {
//                    throw new IllegalStateException("****Contract Status must be STARTED****");
//                }
//
//                TimesheetEntity tsEntity;
//                TimesheetEntryEntity entryEntity;
//                List<TimesheetEntryEntity> timeSheetEntrylist;
//                LocalDate tempDate;
//
//                if (timeSheetFrequency != null) {
//
//                    if (timeSheetFrequency.equalsIgnoreCase(TimesheetFrequencyEnum.MONTHLY.toString())) {
//                        diff = ChronoUnit.MONTHS.between(startDate, endDate);
//                        timeSheetStartDate = startDate;
//
//                        for (int i = 0; i < diff; i++) {
//                            //tsEntity = new TimesheetEntity();
//                            timeSheetEntrylist = new ArrayList<TimesheetEntryEntity>();
//                            tsEntity = timeSheetAccess.createEntity("TimeSheet");
//                            tsEntity.setStartDate(timeSheetStartDate);
//                            timeSheetEndDate = timeSheetStartDate.withDayOfMonth(timeSheetStartDate.lengthOfMonth());
//                            tsEntity.setEndDate(timeSheetEndDate);
//                            tsEntity.setStatus(TimesheetStatusEnum.IN_PROGRESS);
//                            tsEntity.setContract(centity);
//                            timeSheetStartDate = timeSheetStartDate.plusMonths(1);
//                            tempDate = tsEntity.getStartDate();
//
//                            while (!tempDate.isAfter(timeSheetEndDate)) {
//                                entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry");
//                                entryEntity.setEntryDate(tempDate);
//                                entryEntity.setTimesheet(tsEntity);
//                                tempDate = tempDate.plusDays(1);
//                                timeSheetEntrylist.add(entryEntity);
//                            }
//                            tsEntity.setEntries((Set) timeSheetEntrylist);
//                            //timeSheets.add(tsEntity);
//                        }
//
//                    } else {
//
//                        diff = ChronoUnit.DAYS.between(startDate, endDate);
//                        weeks = diff / 7;
//                        if (diff % 7 != 0) {
//                            weeks = weeks + 1;
//                        }
//                        timeSheetStartDate = startDate;
//                        for (int i = 1; i <= weeks; i++) {
//
//                            timeSheetEntrylist = new ArrayList<TimesheetEntryEntity>();
//                            tsEntity = timeSheetAccess.createEntity("TimeSheet");
//                            tsEntity.setStartDate(timeSheetStartDate);
//
//                            timeSheetStartDate = timeSheetStartDate.plusWeeks(1);
//                            tempDate = timeSheetStartDate;
//
//                            LocalDate edate = tempDate.minusDays(1);
//                            if (timeSheetStartDate.isAfter(endDate)) {
//                                edate = endDate.withDayOfMonth(endDate.lengthOfMonth());
//                            }
//                            tsEntity.setEndDate(edate);
//                            tsEntity.setContract(centity);
//                            tsEntity.setStatus(TimesheetStatusEnum.IN_PROGRESS);                        //System.out.println("End of week::" + edate);                        timeSheets.add(tsEntity);
//
//                            tempDate = tsEntity.getStartDate();
//                            while (!tempDate.isAfter(edate)) {
//                                entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry_for_timesheet_" + tsEntity.getId());
//                                entryEntity.setEntryDate(tempDate);
//                                tempDate = tempDate.plusDays(1);
//                                entryEntity.setTimesheet(tsEntity);
//                                timeSheetEntrylist.add(entryEntity);
//                            }
//                            tsEntity.setEntries(new HashSet(timeSheetEntrylist));
//
//                        }
//
//                    }
//                }
//            }
//        } catch (IllegalStateException e) {
//            System.err.print(e.getMessage());
//        }
//    }
//
//     
    
    /*
     * // check if the timesheet is in in_progress state and contract is
     * started then only allow to perform action

     *
     * @param TimeSheetEntry object containing the values to be saved/updated
     * @return String containing message.
     */
    @Override
    public String addUpdateTimeSheetEntry(final TimeSheetEntry obj) {
        String messageString = null;
        try {
            String uuid = obj.getUuid();
            if (uuid == null) {
                throw new IllegalStateException("parameter cannot be null!!");
            }
            TimesheetEntryEntity tsEntry = timeSheetEntryAccess.findByPrimaryKey(timeSheetEntryAccess.getByUuid(uuid).getId());
            tsEntry.setDescription(obj.getDescription());
            tsEntry.setEndTime(obj.getEndTime());
            tsEntry.setStartTime(obj.getStartTime());

            messageString = "Saved!!"; // need to do internationalization;

            final String timeSheetStatus = tsEntry.getTimesheet().getStatus().toString();
            final ContractEntity contract = contractAccess.findByPrimaryKey(tsEntry.getTimesheet().getContract().getId());

            if (timeSheetStatus.equalsIgnoreCase("IN_PROGRESS") && contract.getStatus().toString().equalsIgnoreCase("STARTED")) {

                tsEntry.setDescription(obj.getDescription());
                tsEntry.setEndTime(obj.getEndTime());
                tsEntry.setStartTime(obj.getStartTime());

                messageString = "Saved!!"; // need to do internationalization;

            } else {
                throw new IllegalStateException("Please check contract status or timesheet status");
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return messageString;
    }

    @Override
    public String deleteTimeSheet(final String contractUuid, Boolean isTerminateContract) {
        List<TimesheetEntity> removeList;
        try {
            if (contractUuid == null) {
                throw new IllegalStateException("Uuid cannot be null");
            }
            // get all timesheets for a contract 

            List<TimesheetEntity> timeSheets = timeSheetAccess.getTimeSheetsForContract(contractUuid);

            if (isTerminateContract) {
                removeList = new ArrayList<TimesheetEntity>();
                for (final TimesheetEntity e : timeSheets) {
                    if (TimesheetStatusEnum.IN_PROGRESS.toString().equalsIgnoreCase(e.getStatus().toString())) {
                        removeList.add(e);
                    }
                }
            }

        } catch (IllegalStateException e) {
                System.err.println(e.getMessage());
        }
        return "Success";
    }

    @Override
    public TimeSheet printTimeSheet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TimeSheet> viewTimeSheet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContractEntity getContractByUUID(String uuid) {
        return contractAccess.getContractEntity("fa80898f-bd9d-40bd-8203-c7bff5f82d79"); // hardcoding for testing purpose
    }

    /**
     *
     * @param uuid of the contract for which all Timesheets needs to be fetched
     * @return List<TimeSheet> objects containing the timesheet details
     */
    @Override
    public List<TimeSheet> getAllTimeSheetsForContract(String contractUUID) {

        //contract uuid=fa80898f-bd9d-40bd-8203-c7bff5f82d79
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
            if (contractUUID == null) {
                throw new IllegalStateException("****contract cannot be null****");
            }
            timeSheetList = timeSheetAccess.getTimeSheetsForContract(contractUUID);

            tsObjList = new ArrayList<TimeSheet>(timeSheetList.size());
            TimeSheet ts;
            TimeSheetEntry tsEntry;
            Boolean isHoliday;
            LocalDate tempDate;
            List<TimeSheetEntry> entryList;
            for (final TimesheetEntity entity : timeSheetList) {
                entryList = new ArrayList<TimeSheetEntry>();
                ts = new TimeSheet();
                ts.setEndDate(entity.getEndDate());
                ts.setId(entity.getId());
                ts.setStartDate(entity.getStartDate());
                ts.setStatus(entity.getStatus());
                ts.setUuid(entity.getUuid());

                ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());
                tsObjList.add(ts);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());

        }

        return tsObjList;
    }

    /**
     *
     * @exception illegalStateException if parameter uuid is null.
     * @param uuid of the timeSheet of contract
     * @return List<TimeSheetEntry> containing the TimeSheet entries for a given
     * timeSheet.
     */
    @Override
    public List<TimeSheetEntry> getEntriesForTimeSheet(String uuid) {

        List<TimeSheetEntry> entryList = null;
        Boolean isHoliday;
        try {
            if (uuid == null) {
                throw new IllegalStateException("Please select a timesheet");
            }

            entryList = new ArrayList<TimeSheetEntry>();

            List<TimesheetEntryEntity> objList = timeSheetEntryAccess.getEntriesForTimeSheet(timeSheetEntryAccess.getByUuid(uuid).getId());
            TimeSheetEntry entryObj;

            for (TimesheetEntryEntity e : objList) {
                entryObj = new TimeSheetEntry(e.getUuid(), e.getName());
                entryObj.setEntryDate(e.getEntryDate());
                entryObj.setDateString(e.getEntryDate().toString());
                entryObj.setDescription(e.getDescription());
                entryObj.setEndTime(e.getEndTime());
                entryObj.setStartTime(e.getStartTime());
                if (e.getEntryDate().getDayOfWeek().toString().equalsIgnoreCase("sunday") || e.getEntryDate().getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                    isHoliday = Boolean.TRUE;
                } else {
                    isHoliday = Boolean.FALSE;
                }
                entryObj.setIsHoliday(isHoliday);
                entryList.add(entryObj);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return entryList;
    }

    /**
     * // CALL this method when and emp/supervisor wants to sign the timesheet
     *
     * @param uuid of the timesheet
     * @param submittedByEmp set true if submitted by emp . Set false if submitted by supervisor. Set null if not called by emp or supervisor.
     * 
     * @return String message.
     */
    @Override
    public String submitTimeSheet(final String uuid, final Boolean submittedByEmp) {

        String messageString = null;
        try {
            if (uuid == null) {
                throw new IllegalStateException("uuid of timesheet cannot ne null!!");
            }

            TimesheetEntity tsEntity = timeSheetAccess.findByPrimaryKey(timeSheetAccess.getByUuid(uuid).getId());

            // signed by employee should come here
                if (submittedByEmp) {

                    tsEntity.setSignedByEmployee(LocalDate.now());
                    tsEntity.setStatus(TimesheetStatusEnum.SIGNED_BY_EMPLOYEE);
                } else {
                    // signed by supervisor should come here
                    tsEntity.setSignedBySupervisor(LocalDate.now());
                    tsEntity.setStatus(TimesheetStatusEnum.SIGNED_BY_SUPERVISOR);
                }
                messageString = "TimeSheet Submitted Successfully!";
                return messageString;

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return messageString;
    }

    @Override
    public String deleteTimeSheetEntry(String uuid) {
        String messageString = null;
        try {
            if (uuid == null) {
                throw new IllegalStateException("please a timeSheet entry");
            }
            TimesheetEntryEntity entry = timeSheetEntryAccess.findByPrimaryKey(timeSheetEntryAccess.getByUuid(uuid).getId());
            entry.setDescription(null);
            entry.setStartTime(null);
            entry.setEndTime(null);

            messageString = "Entry removed";

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return messageString;
    }
}
