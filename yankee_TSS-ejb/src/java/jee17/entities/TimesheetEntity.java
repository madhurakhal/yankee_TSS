/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jee17.logic.TimesheetStatusEnum;

/**
 *
 * TODO: REMOVE relation getter setter as per our requirement. delete set if in
 * collection.
 */
@Entity
@Table(name = "TIMESHEET")
public class TimesheetEntity extends NamedEntity {

    private static final long serialVersionUID = 1L;
     
    @Enumerated(EnumType.STRING)
    private TimesheetStatusEnum status;
    
    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate signedByEmployee;

    private LocalDate signedBySupervisor;

    @ManyToOne
    private ContractEntity contract;

    @OneToMany(mappedBy = "timesheet")
    private Set<TimesheetEntryEntity> entries;

    public TimesheetEntity(boolean isNew) {
        super(isNew);
    }

    public TimesheetEntity() {
    }

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

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    public Set<TimesheetEntryEntity> getEntries() {
        return entries;
    }

    public void setEntries(Set<TimesheetEntryEntity> entries) {
        this.entries = entries;
    }

}
