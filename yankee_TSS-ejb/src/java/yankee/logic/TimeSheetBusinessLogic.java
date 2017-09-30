package yankee.logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import yankee.entities.ContractEntity;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
public interface TimeSheetBusinessLogic extends Serializable {
 
    public String addUpdateTimeSheetEntry(final TimeSheetEntry obj);
    public String deleteTimeSheetEntry(final String uuid);
    public String deleteTimeSheet(final String contractUuid,final Boolean isTerminateContract);
    public String submitTimeSheet(final String uudi,final Boolean submittedByEmp);
    public List<TimeSheet> getAllTimeSheetsForContract(final String uuid);
    public ContractEntity getContractByTimesheetUUID(final String uuid);
    public List<TimeSheetEntry> getEntriesForTimeSheet(final String uuid);
    public void archiveTimeSheet(String TimeSheetUUID);
    public void revokeSignature(String timeSheetUUID);
    public TimeSheet getByUUID(String timeSheetUUID) ;
    
    
    public List<TimeSheet> createTimeSheet(final String contractUUID); 
    public List<TimeSheet> getAllTimeSheetsByGivenDate (LocalDate givenDate);    
    public List<TimeSheet> getAllTimeSheetsSignedBySupervisor (LocalDate givenDate);
    
    public void deleteOldTimeSheetSignedBySupervisor(LocalDate oldDate);
    
}
