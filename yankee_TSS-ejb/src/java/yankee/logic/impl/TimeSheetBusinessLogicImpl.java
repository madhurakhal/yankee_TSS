/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.ContractEntity;
import yankee.entities.TimesheetEntity;
import yankee.entities.TimesheetEntryEntity;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.to.Contract;
import yankee.logic.dao.TimeSheetEntryAccess;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;



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

    /**
     * Call this method when the Assistant starts the contract
     *
     * @param uuid specifying the unique identifier for the contract
     * @param startDate specifying the start date of the contract.
     * @param endDate specifying the end date of the contract.
     * @param timeSheetFrequency specifying the frequency of the TimeSheet.
     * @param contractStatus specifying the status of the contract.
     *
     * @return String message containing success or failure.
     */
    @Override
    public List<TimesheetEntity> createTimeSheet(final String uuid, final LocalDate startDate, final LocalDate endDate, final String timeSheetFrequency, final String contractStatus) {

        long diff;
        LocalDate timeSheetStartDate;
        LocalDate timeSheetEndDate;
        float weeks;
        List<TimesheetEntity> timeSheets = null;
        ContractEntity centity = null;
        try {

            if (uuid != null) {
                centity = getContractByUUID("e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941"); // hardcoding for testing purpose
            }

            if (!contractStatus.equalsIgnoreCase(ContractStatusEnum.STARTED.toString())) {
                throw new IllegalStateException("****Contract Status must be STARTED****");
            }

            timeSheets = new ArrayList<TimesheetEntity>();
            TimesheetEntity tsEntity;
            TimesheetEntryEntity entryEntity;
            List<TimesheetEntryEntity> timeSheetEntrylist;
            LocalDate tempDate;
            
            
            if (timeSheetFrequency != null) {
                
                if (timeSheetFrequency.equalsIgnoreCase(TimesheetFrequencyEnum.MONTHLY.toString())) {
                    diff = ChronoUnit.MONTHS.between(startDate, endDate);
                    timeSheetStartDate = startDate;

                    for (int i = 0; i < diff; i++) {
                        //tsEntity = new TimesheetEntity();
                        timeSheetEntrylist = new ArrayList<TimesheetEntryEntity>();
                        tsEntity = timeSheetAccess.createEntity("TimeSheet");
                        tsEntity.setStartDate(timeSheetStartDate);
                        timeSheetEndDate = timeSheetStartDate.withDayOfMonth(timeSheetStartDate.lengthOfMonth());
                        tsEntity.setEndDate(timeSheetEndDate);
                        tsEntity.setStatus(TimesheetStatusEnum.IN_PROGRESS);
                        tsEntity.setContract(centity);
                        timeSheetStartDate = timeSheetStartDate.plusMonths(1);
                        tempDate = tsEntity.getStartDate();
                        
                        while (!tempDate.isAfter(timeSheetEndDate)) {
                            entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry");
                            entryEntity.setEntryDate(tempDate);
                            entryEntity.setTimesheet(tsEntity);
                            tempDate = tempDate.plusDays(1);
                            timeSheetEntrylist.add(entryEntity);
                        }
                        tsEntity.setEntries((Set) timeSheetEntrylist);
                        //timeSheets.add(tsEntity);
                    }

                } 
                else {

                    diff = ChronoUnit.DAYS.between(startDate, endDate);
                    weeks = diff / 7;
                    if (diff % 7 != 0) {
                        weeks = weeks + 1;
                    }
                    timeSheetStartDate = startDate;
                    for (int i = 1; i <= weeks; i++) {
                        timeSheetEntrylist = new ArrayList<TimesheetEntryEntity>();
                        tsEntity = timeSheetAccess.createEntity("TimeSheet");
                        tsEntity.setStartDate(timeSheetStartDate);

                        timeSheetStartDate = timeSheetStartDate.plusWeeks(1);
                        tempDate = timeSheetStartDate;

                        LocalDate edate = tempDate.minusDays(1);
                        if (timeSheetStartDate.isAfter(endDate)) {
                            edate = endDate.withDayOfMonth(endDate.lengthOfMonth());
                        }
                        //tsEntity.setEndDate(edate);
                        tsEntity.setContract(centity);
                        tsEntity.setStatus(TimesheetStatusEnum.IN_PROGRESS);                        //System.out.println("End of week::" + edate);                        timeSheets.add(tsEntity);
                        
                        tempDate = tsEntity.getStartDate();
                        while (!tempDate.isAfter(edate)) {
                            entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry_for_timesheet_"+tsEntity.getId());
                            entryEntity.setEntryDate(tempDate);
                            tempDate = tempDate.plusDays(1);
                            entryEntity.setTimesheet(tsEntity);
                            timeSheetEntrylist.add(entryEntity);
                        }
                        tsEntity.setEntries(new HashSet(timeSheetEntrylist));

                    }

                }

            }
        } catch (IllegalStateException e) {
            System.err.print(e.getMessage());
            e.printStackTrace();
        }
        return timeSheets;

    }

    /**
     * 
     * @param TimeSheetEntry object containing the values to be saved/updated
     * @return String containing message.
     */
    
    
    @Override
    public String addUpdateTimeSheetEntry(final TimeSheetEntry obj) {
        String messageString=null;
        try
        {
            String uuid=obj.getUudi();
            if(uuid==null)
            {
                throw new IllegalStateException("parameter cannot be null!!");
            }
            TimesheetEntryEntity tsEntry=timeSheetEntryAccess.findByPrimaryKey(timeSheetEntryAccess.getByUuid(uuid).getId());
            
            tsEntry.setDescription(obj.getDescription());
            tsEntry.setEndTime(obj.getEndTime());
            tsEntry.setStartTime(obj.getStartTime());
            
            messageString="Saved!!"; // need to do internationalization;
        
        }
        catch(IllegalStateException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return messageString;
    }

    
    @Override
    public String deleteTimeSheet(final String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return contractAccess.getByUuid("e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941");
    }

    @Override
    public List<TimeSheet> getAllTimeSheetsForContract(String uuid) {

        //contract uuid=fa80898f-bd9d-40bd-8203-c7bff5f82d79
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
         if (uuid == null) {
                throw new IllegalStateException("****contract cannot be null****");
            }
            timeSheetList = timeSheetAccess.getTimeSheetsForContract(timeSheetAccess.getByUuid(uuid).getUuid());

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
            e.printStackTrace();
        }

        return tsObjList;
    }

    
    
    /**
     * 
     * @exception illegalStateException if parameter uuid is null.
     * @param uuid of the timeSheet of contract 
     * @return List<TimeSheetEntry> containing the TimeSheet entries for a given timeSheet.
     */
    
    @Override
    public List<TimeSheetEntry> getEntriesForTimeSheet(String uuid) {
        
        List<TimeSheetEntry>entryList = null;
        Boolean isHoliday;
        try
        {
            if(uuid==null)
            {
                throw new IllegalStateException("Please select a timesheet");
            }
     
            entryList=new ArrayList<TimeSheetEntry>();
            
            List<TimesheetEntryEntity> objList = timeSheetEntryAccess.getEntriesForTimeSheet(timeSheetEntryAccess.getByUuid(uuid).getId());
            TimeSheetEntry entryObj;
            
            for(TimesheetEntryEntity e:objList)
            {
                entryObj=new TimeSheetEntry();
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
            e.printStackTrace();
        }
        return entryList;
    }

    @Override
    public List<TimeSheet> getAllTimeSheetsByGivenDate(LocalDate givenDate) {
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
            timeSheetList = timeSheetAccess.getAllTimeSheetsByGivenDate(givenDate);
            tsObjList = new ArrayList<>();
            TimeSheet ts;
            for (final TimesheetEntity entity : timeSheetList) {
                ts = new TimeSheet();
                ts.setEndDate(entity.getEndDate());
                ts.setId(entity.getId());
                ts.setStartDate(entity.getStartDate());
                ts.setStatus(entity.getStatus());
                ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());

//                ContractEntity contract = entity.getContract();
//                // to get the contract id
//                Contract c = new Contract(contract.getUuid(), contract.getName());
//                // to do fill up contract transfer object
//                ts.setContract(c);

                tsObjList.add(ts);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return tsObjList;
    }
    
    
    /**
     * 
     * @param obj TimeSheet object containing the details.
     * @return String message.
     */
    
    @Override
    public String submitTimeSheet(final TimeSheet obj) {
      
        String messageString=null;
      try
      { 
          String uuid=obj.getUuid();
          if(uuid==null)
          {
              throw new IllegalStateException("uuid of timesheet cannot ne null!!");
          } 
          
          TimesheetEntity tsEntity=timeSheetAccess.findByPrimaryKey(timeSheetAccess.getByUuid(uuid).getId());
          
          if(tsEntity.getSignedByEmployee()!=null && tsEntity.getSignedBySupervisor()!=null)
          {
              // do stuff
              tsEntity.setSignedByEmployee(obj.getSignedByEmployee());
              tsEntity.setSignedBySupervisor(obj.getSignedBySupervisor());
              tsEntity.setStatus(TimesheetStatusEnum.SIGNED_BY_EMPLOYEE);
              
              messageString="TimeSheet Submitted Successfully!";
          }
          else
          {
              messageString="TimeSheet already submitted!";
              return messageString;
          }
          
          
          
          
      }
      catch(IllegalStateException e)
      {
          e.printStackTrace();
      }
      
      return messageString;
    }
    

}
