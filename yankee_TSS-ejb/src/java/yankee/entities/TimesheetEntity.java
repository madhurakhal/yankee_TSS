/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import yankee.logic.ENUM.TimesheetStatusEnum;



@NamedQueries({
    @NamedQuery(name = "getTimeSheetsForContract", query = "SELECT p FROM TimesheetEntity p WHERE p.contract.uuid = :contractUUID"),
    @NamedQuery(name = "getTimeSheetEntityByUuid", query = "SELECT e FROM TimesheetEntity e WHERE e.uuid = :uuid"),
    @NamedQuery(name = "getTimeSheetsForContractById", query = "SELECT p FROM TimesheetEntity p WHERE p.contract.id = :contractId"),
    @NamedQuery(name = "getAllRunningTimeSheet", query = "SELECT p FROM TimesheetEntity p WHERE p.endDate <= :givenDate AND p.status <> yankee.logic.ENUM.TimesheetStatusEnum.ARCHIVED"),
    @NamedQuery(name = "getAllTimeSheetsSignedBySupervisor", query = "SELECT p FROM TimesheetEntity p WHERE p.signedBySupervisor = :givenDate"),
    @NamedQuery(name = "deleteOldTimeSheetSignedBySupervisor", query = "DELETE FROM TimesheetEntity p WHERE p.signedBySupervisor <= :oldDate")
})


@Entity
@Table(name = "TIMESHEET")
public class TimesheetEntity extends NamedEntity {

    private static final long serialVersionUID = 1L;
     
    @Enumerated(EnumType.STRING)
    private TimesheetStatusEnum status;
    
    //@Column
   // @Convert(converter=LocalDateConverter.class)
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private double hoursDue;   

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
    
    public double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(double hoursDue) {
        this.hoursDue = hoursDue;
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
