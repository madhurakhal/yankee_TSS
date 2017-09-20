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

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
public interface TimeSheetBusinessLogic extends Serializable {
 
    public List<TimesheetEntity> createTimeSheet(String uuid,LocalDate startDate,LocalDate endDate,String timeSheetFrequency,String contractStatus);
    public String addUpdateTimeSheetEntry(final TimeSheetEntry obj);
    public String deleteTimeSheet(final String uuid);
    public List<TimeSheet>viewTimeSheet();
    public TimeSheet printTimeSheet();
    public String submitTimeSheet(TimeSheet obj);
    public List<TimeSheet> getAllTimeSheetsForContract(String uuid);
    public ContractEntity getContractByUUID(String uuid);
    public List<TimeSheetEntry> getEntriesForTimeSheet(String uuid);
    
}
