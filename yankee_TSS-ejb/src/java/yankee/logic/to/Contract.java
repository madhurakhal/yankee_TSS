package yankee.logic.to;

import java.time.LocalDate;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetFrequencyEnum;

public class Contract extends Named{
    
    private ContractStatusEnum status;
    private LocalDate startDate;
    private LocalDate endDate;
    private TimesheetFrequencyEnum frequency;
    private LocalDate terminationDate;
    private double hoursPerWeek;
    private int workingDaysPerWeek;
    private int vacationDaysPerYear;
    private double vacationHours;
    private double hoursDue;
    private int archiveDuration;
    private Employee employee;    
    private Supervisor supervisor;
    
    public Contract(String uuid, String name) {
        super(uuid, name);
    }
    
    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }    
    
    public int getArchiveDuration() {
        return archiveDuration;
    }

    public void setArchiveDuration(int archiveDuration) {
        this.archiveDuration = archiveDuration;
    }
    
    public double getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(double vationHours) {
        this.vacationHours = vationHours;
    }

    public double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(double hoursDue) {
        this.hoursDue = hoursDue;
    }

    public ContractStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ContractStatusEnum status) {
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

    public TimesheetFrequencyEnum getFrequency() {
        return frequency;
    }

    public void setFrequency(TimesheetFrequencyEnum frequency) {
        this.frequency = frequency;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public int getWorkingDaysPerWeek() {
        return workingDaysPerWeek;
    }

    public void setWorkingDaysPerWeek(int workingDaysPerWeek) {
        this.workingDaysPerWeek = workingDaysPerWeek;
    }

    public int getVacationDaysPerYear() {
        return vacationDaysPerYear;
    }

    public void setVacationDaysPerYear(int vacationDaysPerYear) {
        this.vacationDaysPerYear = vacationDaysPerYear;
    }

   
    
    
    
}
