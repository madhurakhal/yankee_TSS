/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.to;


import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
public class TimeSheetEntry extends Named{
    
    private String description;
    private LocalDate entryDate;
    private Long timeSheetId;
    private String dateString;

    private Double hours;  // To be calculated by ourself. 
    private Date startDateTime;    

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
    private Date endDateTime;  

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
    
    public TimeSheetEntry(String uuid, String name) {
        super(uuid, name);
    }

   
    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }
    
    private Boolean isHoliday;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Long getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Long timeSheetId) {
        this.timeSheetId = timeSheetId;
    }
    
    
    
    
}
