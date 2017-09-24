/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import yankee.entities.ContractEntity;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
@Named(value = "timeSheetBean")
@RequestScoped
public class TimeSheetBean {

    /**
     * Creates a new instance of TimeSheetBean
     */
    public TimeSheetBean() {
    }

    private List<TimeSheet> timesheets;

    private TimeSheet timeSheetFor;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    private String uuid;

    private List<TimeSheetEntry> timeSheetEntries;

    public List<TimeSheetEntry> getTimeSheetEntries() {
        return timeSheetEntries;
    }

    @EJB
    TimeSheetAccess timeSheetAccess;
   
    public TimeSheet getTimeSheetFor() {
        return timeSheetFor;
    }

    public void setTimeSheetFor(TimeSheet timeSheetFor) {
        this.timeSheetFor = timeSheetFor;
    }
    
    @EJB
    private TimeSheetBusinessLogic timeSheetService;

    public TimeSheetBusinessLogic getTimeSheetService() {
        return timeSheetService;
    }

    public void setTimeSheetService(TimeSheetBusinessLogic timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    public void setTimesheets(List<TimeSheet> timesheets) {
        this.timesheets = timesheets;
    }

    public List<TimeSheet> getTimesheets() {
        return timesheets;
    }
  
    
    @PostConstruct
    public  void init()    {
        if (timeSheetService != null) { 

            final ContractEntity centity = timeSheetService.getContractByUUID("8901039d-bbbe-4e3d-93b9-9bda4a014d1a");
            timesheets=timeSheetService.getAllTimeSheetsForContract(centity.getUuid());
        }
    }
  
    public void onSubmitRow(String timeSheet_uuid)
    {   
        timeSheetService.submitTimeSheet(timeSheet_uuid, Boolean.TRUE);
        
     } 
    
    public void onRowView(String timeSheetUUId,String displayStrings)throws IOException {
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/public/view_timesheet_entries.xhtml?id=" + timeSheetUUId);
        
    }
    
       
    public void addNewEntry(String timeSheetUUId,String displayString)throws IOException
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/public/timesheet_entry.xhtml?id=" + timeSheetUUId+"&timeSheetDateRange="+displayString);
    }
  

}
