/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Person;
import yankee.logic.to.TimeSheetEntry;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
@ManagedBean
@Named(value = "timeSheetEntryBean")
@ViewScoped
public class TimeSheetEntryBean implements Serializable {

    /**
     * Creates a new instance of TimeSheetEntryBean
     */
    @EJB
    private TimeSheetBusinessLogic timeSheetService;

    private List<TimeSheetEntry> entries;
    private String description;
    private double hours;
    private String timeSheetUuid;
    private String dateString;
    private String timeSheet_id;
    private String displayString;
    private TimeSheetEntry selectedEntry;
    private Date startDate;
    private String contractUUID;

    private Person loggedinUser;
    @Inject
    private LoginBean loginBean;

    public Person getLoggedinUser() {
        loggedinUser = loginBean.getUser();
        return loggedinUser;
    }

    public void setLoggedinUser(Person loggedinUser) {
        this.loggedinUser = loggedinUser;
    }

    public String getContractUUID() {
        return contractUUID;
    }

    public void setContractUUID(String contractUUID) {
        this.contractUUID = contractUUID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
    private Date endDateTime;

    public TimeSheetEntry getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(TimeSheetEntry selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public List<TimeSheetEntry> getEntries() {
        boolean flag = entries == null;
        if (flag) {
            entries = timeSheetService.getEntriesForTimeSheet(timeSheet_id);
        }
        return entries;
    }

    public void setEntries(List<TimeSheetEntry> entries) {
        this.entries = entries;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getTimeSheetUuid() {
        return timeSheetUuid;
    }

    public void setTimeSheetUuid(String timeSheetUuid) {
        this.timeSheetUuid = timeSheetUuid;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeSheet_id() {
        if (timeSheet_id == null) {
            Map<String, String> params = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap();
            timeSheet_id = params.get("id");
        }
        return timeSheet_id;
    }

    public String getDisplayString() {
        if (displayString == null) {
            Map<String, String> params = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap();
            displayString = params.get("timeSheetDateRange");
        }
        return displayString;
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        contractUUID = params.get("contractID");
        getTimeSheet_id();
        getDisplayString();
        getEntries();

    }

    public void saveEntry() {
        if (selectedEntry.getStartDateTime() == null || selectedEntry.getEndDateTime() == null || selectedEntry.getEndDateTime().getTime() < selectedEntry.getStartDateTime().getTime()) {
            StringBuilder builder = new StringBuilder();
            FacesMessage msgs = new FacesMessage();
            msgs.setSeverity(FacesMessage.SEVERITY_INFO);
            msgs.setSummary("Invalid!! Entry not valid. ReEnter");
            msgs.setDetail(builder.toString());
            FacesContext.getCurrentInstance().addMessage(null, msgs);
        } else {
            if (timeSheetService != null) {
                timeSheetService.addUpdateTimeSheetEntry(selectedEntry);
                StringBuilder builder = new StringBuilder();
                FacesMessage msgs = new FacesMessage();
                msgs.setSeverity(FacesMessage.SEVERITY_INFO);
                msgs.setSummary("Entry Updated");
                msgs.setDetail(builder.toString());
                FacesContext.getCurrentInstance().addMessage(null, msgs);
            }
        }

    }

    public void resetEntry() {
        timeSheetService.addUpdateTimeSheetEntry(selectedEntry);
        StringBuilder builder = new StringBuilder();
        FacesMessage msgs = new FacesMessage();
        msgs.setSeverity(FacesMessage.SEVERITY_INFO);
        msgs.setSummary("Entry Removed");
        msgs.setDetail(builder.toString());
        FacesContext.getCurrentInstance().addMessage(null, msgs);

    }

}
