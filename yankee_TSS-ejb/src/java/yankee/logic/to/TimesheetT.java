package yankee.logic.to;

import java.time.LocalDate;
import java.util.List;
import yankee.logic.ENUM.TimesheetStatusEnum;

public class TimesheetT extends Named{
    
    private TimesheetStatusEnum status;
    
    private LocalDate startDate;

    private LocalDate endDate;
    
    private double hoursDue;

    private LocalDate signedByEmployee;

    private LocalDate signedBySupervisor;
    
    private String displayString;
    
    private List<TimeSheetEntry> timeSheetEntries;
    
    private Contract contract;
    
    public TimesheetT(String uuid, String name) {
        super(uuid, name);
    }

    public TimesheetStatusEnum getStatus() {
        return status;
    }
    
        public double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(double hoursDue) {
        this.hoursDue = hoursDue;
    }


    public void setStatus(TimesheetStatusEnum status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getSignedByEmployee() {
        return signedByEmployee;
    }

    public void setSignedByEmployee(LocalDate signedByEmployee) {
        this.signedByEmployee = signedByEmployee;
    }

    public LocalDate getSignedBySupervisor() {
        return signedBySupervisor;
    }

    public void setSignedBySupervisor(LocalDate signedBySupervisor) {
        this.signedBySupervisor = signedBySupervisor;
    }    

    public List<TimeSheetEntry> getTimeSheetEntries() {
        return timeSheetEntries;
    }

    public void setTimeSheetEntries(List<TimeSheetEntry> timeSheetEntries) {
        this.timeSheetEntries = timeSheetEntries;
    }

    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    
    public void setContract(Contract contract) {
        this.contract = contract;
    }
     public Contract getContract() {
        return contract;
    }
    
}
