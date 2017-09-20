/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.to;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import yankee.logic.ENUM.TimesheetStatusEnum;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
public class TimeSheet {

    private Contract contract;
    private TimesheetStatusEnum status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate signedByEmployee;
    private LocalDate signedBySupervisor;
    private String displayString;
    private Long id;

    public TimesheetStatusEnum getStatus() {
        return status;
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

    private String uuid;
    
    private List<TimeSheetEntry> timeSheetEntries;

    public List<TimeSheetEntry> getTimeSheetEntries() {
        return timeSheetEntries;
    }

    public void setTimeSheetEntries(List<TimeSheetEntry> timeSheetEntries) {
        this.timeSheetEntries = timeSheetEntries;
    }
    

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
