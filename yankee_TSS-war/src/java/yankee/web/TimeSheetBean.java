/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
    
    
    
    public void ajaxListener(AjaxBehaviorEvent e)
    {
        //String uuid=e.getNewValue().toString();
        System.out.println("yankee.web.TimeSheetBean.valueChangeEvent()"+uuid);
        
        TimeSheet selectedTimeSheetObj=null;
        for(TimeSheet ts:timesheets)
        {
            if(ts.getUuid().equalsIgnoreCase(uuid))
            {
                selectedTimeSheetObj=ts;
                break;
            }
        }
        
        if(selectedTimeSheetObj!=null){
        LocalDate startDate=selectedTimeSheetObj.getStartDate();
        LocalDate endDate=selectedTimeSheetObj.getEndDate();
        TimeSheetEntry tsObj;
        List<TimeSheetEntry> objList=new ArrayList<TimeSheetEntry>();
        while(!startDate.isAfter(endDate))
        {
            tsObj=new TimeSheetEntry();
            tsObj.setEntryDate(startDate);
            tsObj.setDateString(startDate.toString());
            startDate=startDate.plusDays(1);
            objList.add(tsObj);
        }
        timeSheetEntries=objList;
        }
    }

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

    /*public List<TimeSheet> getTimesheets() {
        return timesheets;
    }*/

    public void setTimesheets(List<TimeSheet> timesheets) {
        this.timesheets = timesheets;
    }

    public List<TimeSheet> getTimesheets() {
        return timesheets;
    }

    
    
    @PostConstruct
    public  void init()    {
        if (timeSheetService != null) { 
            final ContractEntity centity = timeSheetService.getContractByUUID("fa80898f-bd9d-40bd-8203-c7bff5f82d79");
            timesheets=timeSheetService.getAllTimeSheetsForContract(centity.getUuid());
        }
        //return timesheets;
    }
  

}
