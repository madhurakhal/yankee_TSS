/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import yankee.entities.ContractEntity;
import yankee.entities.TimesheetEntity;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;
import yankee.logic.to.TimesheetT;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
public interface TimeSheetBusinessLogic extends Serializable {
 
    //public void createTimeSheet(final String uuid);
    public String addUpdateTimeSheetEntry(final TimeSheetEntry obj);
    public String deleteTimeSheetEntry(final String uuid);
    public String deleteTimeSheet(final String contractUuid,final Boolean isTerminateContract);
    public List<TimeSheet>viewTimeSheet();
    public TimeSheet printTimeSheet();
    public String submitTimeSheet(final String uudi,final Boolean submittedByEmp);
    public List<TimeSheet> getAllTimeSheetsForContract(final String uuid);
    public ContractEntity getContractByUUID(final String uuid);
    public List<TimeSheetEntry> getEntriesForTimeSheet(final String uuid);
    
    // TO REVIEW
    public List<TimesheetT> createTimeSheet(final String contractUUID);
    
}
