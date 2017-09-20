/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
                centity = getContractByUUID("20f7aa43-b6cd-47ac-903b-e3f4ce43b536"); // hardcoding for testing purpose
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
                        tsEntity.setEndDate(edate);
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

    @Override
    public String addTimeSheetEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editTimeSheetEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String removeTimeSheetEntry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteTimeSheet() {
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
        return contractAccess.getContractEntity("20f7aa43-b6cd-47ac-903b-e3f4ce43b536"); // hardcoding for testing purpose
    }

    @Override
    public List<TimeSheet> getAllTimeSheetsForContract(Long contractId) {

        //contract uuid=20f7aa43-b6cd-47ac-903b-e3f4ce43b536
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
            if (contractId == null) {
                throw new IllegalStateException("****ContractId cannot be null****");
            }
            timeSheetList = timeSheetAccess.getTimeSheetsForContract(contractId);

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
                tempDate = entity.getStartDate();
                while (!tempDate.isAfter(entity.getEndDate())) {

                    tsEntry = new TimeSheetEntry();
                    tsEntry.setEntryDate(tempDate);
                    if (tempDate.getDayOfWeek().toString().equalsIgnoreCase("sunday") || tempDate.getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                        isHoliday = Boolean.TRUE;
                    } else {
                        isHoliday = Boolean.FALSE;
                    }
                    tsEntry.setIsHoliday(isHoliday);
                    tsEntry.setTimeSheetId(entity.getId());
                    entryList.add(tsEntry);
                    tempDate = tempDate.plusDays(1);
                }
                ts.setTimeSheetEntries(entryList);

                ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());
                tsObjList.add(ts);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return tsObjList;
    }

}
