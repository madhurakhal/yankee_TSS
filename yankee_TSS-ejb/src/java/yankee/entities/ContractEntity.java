/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import java.time.LocalDate;
import java.util.Set;
import yankee.logic.ENUM.ContractStatusEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import yankee.logic.ENUM.TimesheetFrequencyEnum;



@NamedQueries({
    @NamedQuery(name = "getContractCount", query = "SELECT COUNT(p) FROM ContractEntity p"),
    @NamedQuery(name = "getContractList", query = "SELECT p FROM ContractEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getContractByName", query = "SELECT p FROM ContractEntity p WHERE p.name = :name")
})
/**
 *
 * TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
 */
@Entity
@Table(name = "CONTRACT")
public class ContractEntity extends NamedEntity {

    private static final long serialVersionUID = 1L;
    
    @Enumerated(EnumType.STRING)
    private ContractStatusEnum status;
    
    private LocalDate startDate;
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    private TimesheetFrequencyEnum frequency;
    
    private LocalDate terminationDate;
    private double hoursPerWeek;
    private double vacationHours;
    private double hoursDue;
    private int workingDaysPerWeek;
    private int vacationDaysPerYear;

    @OneToOne
    private SupervisorEntity supervisor;

    @OneToMany(mappedBy = "contract")
    private Set<AssistantEntity> assistants;
    
    @OneToMany(mappedBy = "contract")
    private Set<TimesheetEntity> timesheets;
    
    @OneToOne
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "contract")
    private Set<SecretaryEntity> secretaries;

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

    public Set<TimesheetEntity> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<TimesheetEntity> timesheets) {
        this.timesheets = timesheets;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public Set<SecretaryEntity> getSecretaries() {
        return secretaries;
    }

    public void setSecretaries(Set<SecretaryEntity> secretaries) {
        this.secretaries = secretaries;
    }

    public SupervisorEntity getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(SupervisorEntity supervisor) {
        this.supervisor = supervisor;
    }

    public Set<AssistantEntity> getAssistants() {
        return assistants;
    }

    public void setAssistants(Set<AssistantEntity> assistants) {
        this.assistants = assistants;
    }
    
    
    public double getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(double vacationHours) {
        this.vacationHours = vacationHours;
    }

    public double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(double hoursDue) {
        this.hoursDue = hoursDue;
    }

    public ContractEntity() {
    }

    public ContractEntity(boolean isNew) {
        super(isNew);
    }

}
