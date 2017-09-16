/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jee17.entities.ContractEntity;
import jee17.entities.TimesheetEntity;
import jee17.logic.to.TimeSheet;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
public interface TimeSheetBusinessLogic extends Serializable {
 
    public List<TimesheetEntity> createTimeSheet(String uuid,LocalDate startDate,LocalDate endDate,String timeSheetFrequency,String contractStatus);
    public String addTimeSheetEntry();
    public String editTimeSheetEntry();
    public String removeTimeSheetEntry();
    public String deleteTimeSheet();
    public List<TimeSheet>viewTimeSheet();
    public TimeSheet printTimeSheet();
    public List<TimeSheet> getAllTimeSheetsForContract(Long contractId);
    public ContractEntity getContractByUUID(String uuid);
}
