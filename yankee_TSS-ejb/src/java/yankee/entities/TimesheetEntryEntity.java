/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import java.sql.Time;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * TODO: REMOVE relation getter setter as per our requirement. delete set if in
 * collection.
 */
@NamedQueries({
    @NamedQuery(name = "getEntryForTimeSheet", query = "SELECT p FROM TimesheetEntryEntity p WHERE p.timesheet.id = :timesheetId")
})
@Entity
@Table(name = "TIMESHEETENTRY")
public class TimesheetEntryEntity extends NamedEntity {

    private static final long serialVersionUID = 1L;
    private String description;
    private Time startTime;

    private Time endTime;

    private Double hours;
    private LocalDate entryDate;

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    @ManyToOne
    private TimesheetEntity timesheet;

    public TimesheetEntryEntity(boolean isNew) {
        super(isNew);
    }

    public TimesheetEntryEntity() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public TimesheetEntity getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(TimesheetEntity timesheet) {
        this.timesheet = timesheet;
    }

}
