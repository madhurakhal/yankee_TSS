package yankee.entities;

import java.sql.Time;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQueries({
    @NamedQuery(name = "getTimeSheetEntriesForTimeSheet", query = "SELECT p FROM TimesheetEntryEntity p WHERE p.timesheet.uuid = :timeSheetUUID")
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
    private boolean filled;

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    

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
