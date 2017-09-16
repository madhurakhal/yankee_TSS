/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.ContractEntity;
import yankee.entities.TimesheetEntity;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.to.TimeSheet;

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
    
    /**
     * Call this method when the Assistant starts the contract
     *
     * @param startDate specifying the start date of the contract.
     * @param endDate specifying the end date of the contract.
     * @param timeSheetFrequency specifying the frequency of the TimeSheet.
     * @param contractStatus specifying the status of the contract.
     *
     * @return String message containing success or failure.
     */
    
    
    
    @Override
    public List<TimesheetEntity> createTimeSheet(final String uuid,final LocalDate startDate, final LocalDate endDate, final String timeSheetFrequency, final String contractStatus) {

        long diff;
        LocalDate timeSheetStartDate;
        float weeks;
        List<TimesheetEntity> timeSheets = null;
        ContractEntity centity = null;
        try {
            
            if(uuid!=null)
            {
                     centity=getContractByUUID("e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941"); // hardcoding for testing purpose
            }
            
            if (!contractStatus.equalsIgnoreCase(ContractStatusEnum.STARTED.toString())) {
                throw new IllegalStateException("****Contract Status must be STARTED****");
            }

            timeSheets = new ArrayList<TimesheetEntity>();
            TimesheetEntity tsEntity;

            if (timeSheetFrequency != null) {
                if (timeSheetFrequency.equalsIgnoreCase(TimesheetFrequencyEnum.MONTHLY.toString())) {
                    diff = ChronoUnit.MONTHS.between(startDate, endDate);
                    timeSheetStartDate = startDate;
                    for (int i = 0; i < diff; i++) {
                        //tsEntity = new TimesheetEntity();
                        tsEntity= timeSheetAccess.createEntity("TimeSheet");
                         tsEntity.setStartDate(timeSheetStartDate);
                        tsEntity.setEndDate(timeSheetStartDate.withDayOfMonth(timeSheetStartDate.lengthOfMonth()));
                        tsEntity.setStatus(TimesheetStatusEnum.IN_PROGRESS);
                        tsEntity.setContract(centity);
                        timeSheetStartDate = timeSheetStartDate.plusMonths(1);
                        timeSheets.add(tsEntity);
                    }

                } else {

                    diff = ChronoUnit.DAYS.between(startDate, endDate);
                    weeks = diff / 7;
                    if (diff % 7 != 0) {
                        weeks = weeks + 1;
                    }
                    LocalDate tempDate;
                    timeSheetStartDate = startDate;
                    for (int i = 1; i <= weeks; i++) {
                        tsEntity= timeSheetAccess.createEntity("TimeSheet");
                        //tsEntity = new TimesheetEntity();
                        tsEntity.setStartDate(timeSheetStartDate);

                        //System.out.println("Start of week" + timeSheetStartDate);
                        timeSheetStartDate = timeSheetStartDate.plusWeeks(1);
                        tempDate = timeSheetStartDate;

                        LocalDate edate = tempDate.minusDays(1);
                        if (timeSheetStartDate.isAfter(endDate)) {
                            edate = endDate.withDayOfMonth(endDate.lengthOfMonth());
                        }
                        tsEntity.setEndDate(edate);
                        tsEntity.setContract(centity);
                        tsEntity.setStatus(TimesheetStatusEnum.IN_PROGRESS);                        //System.out.println("End of week::" + edate);                        timeSheets.add(tsEntity);
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
    public ContractEntity getContractByUUID(String uuid)
    {
           return contractAccess.getByUuid("e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941");
    }
    
    
    @Override
    public List<TimeSheet> getAllTimeSheetsForContract(Long contractId) {

        //contract uuid=e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try
        {
            if(contractId==null)
            {
                throw new IllegalStateException("****ContractId cannot be null****");
            }
            timeSheetList=timeSheetAccess.getTimeSheetsForContract(contractId);
            
            tsObjList=new ArrayList<TimeSheet>(timeSheetList.size());
            TimeSheet ts;
            for (final TimesheetEntity entity: timeSheetList)
            {
                ts=new TimeSheet();
                ts.setEndDate(entity.getEndDate());
                ts.setId(entity.getId());
                ts.setStartDate(entity.getStartDate());
                ts.setStatus(entity.getStatus());
                ts.setDisplayString(entity.getStartDate().toString()+" - "+ entity.getEndDate().toString());
                tsObjList.add(ts);
            }
        }
        catch(IllegalStateException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return tsObjList;
    }

}
