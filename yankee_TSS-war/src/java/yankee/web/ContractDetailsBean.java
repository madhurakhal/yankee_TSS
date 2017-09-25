package yankee.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.entities.ContractEntity;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;

@ManagedBean
@ViewScoped
@Named
public class ContractDetailsBean {

    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;

    @EJB
    private SecretaryBusinessLogic secretaryBusinessLogic;

    @EJB
    private AssistantBusinessLogic assistantBusinessLogic;

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;
    
    @Inject
    private LoginBean loginBean;

    private String contract_id;
    private Person currentContractPerson;
    private Contract contractinfo;
    private Person loggedinUser;

    public Person getLoggedinUser() {
        loggedinUser = loginBean.getUser();
        return loggedinUser;
    }

    public void setLoggedinUser(Person loggedinUser) {
        this.loggedinUser = loggedinUser;
    }

    private Person supervisorForContract;
    private List<Person> secretariesForContract = new ArrayList<>();
    private List<Person> assistantsForContract = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
 
    public boolean isSupervisor(String timeSheetUUID){
       System.out.println("TIMEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + timeSheetUUID);
       ContractEntity ce = timeSheetBusinessLogic.getContractByTimesheetUUID(timeSheetUUID);
       return ce.getSupervisor().getPerson().getUuid().equals(loggedinUser.getUuid());
    }

    @PostConstruct
    public void init() {
        //This contract id will be sent to edit contract as parameter in url from managecontract edit is pressed
        // This will be used in getting current assistant, supervisor, secretary for this contract id below.
        System.out.println("called me init only once yes?");
        getContract_id();
        getPersons();
        getCurrentContractPerson();

        // First we will get all the assistant , supervisor , secretary for the given contract
        getSupervisorForContract();
        getAssistantsForContract();
        getSecretariesForContract();
    }

    // BEGINS GETTER AND SETTER for contract id then current assistant , supervisor, secretaries for given contract
    public Person getCurrentContractPerson() {
        if (currentContractPerson == null) {
            currentContractPerson = employeeBusinessLogic.getEmployeeByContract(contract_id).getPerson();
        }
        return currentContractPerson;
    }

    public List<Person> getPersons() {
        if (persons.isEmpty()) {
            persons = personBusinessLogic.getPersonList();
        }
        return persons;
    }

    public String getContract_id() {
        if (contract_id == null) {
            Map<String, String> params = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap();
            contract_id = params.get("id");
        }
        return contract_id;
    }

    public Contract getContractinfo() {
        contractinfo = contractBusinessLogic.getContractByUUID(contract_id);
        return contractinfo;
    }

    public Person getSupervisorForContract() {
        if (supervisorForContract == null) {
            supervisorForContract = supervisorBusinessLogic.getSupervisorByContract(contract_id).getPerson();
        }
        return supervisorForContract;
    }

    public List<Person> getSecretariesForContract() {
        if (secretariesForContract.isEmpty()) {
            List<Secretary> ls = secretaryBusinessLogic.getSecretariesByContract(contract_id);
            List<Person> result = new ArrayList<>();
            ls.forEach((s) -> {
                result.add(s.getPerson());
            });
            secretariesForContract = result;
        }
        return secretariesForContract;
    }

    public List<Person> getAssistantsForContract() {
        if (assistantsForContract.isEmpty()) {
            List<Assistant> ls = assistantBusinessLogic.getAssistantsByContract(contract_id);
            List<Person> result = new ArrayList<>();
            ls.forEach((s) -> {
                result.add(s.getPerson());
            });
            assistantsForContract = result;
        }
        return assistantsForContract;
    }

// RELATED TO STATISTICSSSSSSSSss For TimeSheet NEEEEED TO TALK TO HARSHIII
    private List<Integer> HoursDuePerTimeSheet;

    public List<Integer> getHoursDuePerTimeSheet() {
        return HoursDuePerTimeSheet;
    }

    public void setHoursDuePerTimeSheet(List<Integer> HoursDuePerTimeSheet) {
        this.HoursDuePerTimeSheet = HoursDuePerTimeSheet;
    }

    /**/
    /**/
     /**/
    /**/
    
    /* BEGIN:::  TimeSheettts DISPLAY BEGINSSSS*/
    
    // All For Timesheets BEGINSSSS
    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;
    
    private List<TimeSheet> timesheets;    
    private List<TimeSheetEntry> timeSheetEntries;
    private TimeSheet timeSheetFor;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    private String uuid;


    public List<TimeSheetEntry> getTimeSheetEntries() {
        return timeSheetEntries;
    }
    
    

    public TimeSheet getTimeSheetFor() {
        return timeSheetFor;
    }

    public void setTimeSheetFor(TimeSheet timeSheetFor) {
        this.timeSheetFor = timeSheetFor;
    }


    public void setTimesheets(List<TimeSheet> timesheets) {
        this.timesheets = timesheets;
    }

    public List<TimeSheet> getTimesheets() {
        timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(contract_id);
        return timesheets;
    }
    
    /* Following method called when action performed. Ajax calls */

    public void onSignByEmployeeRow(String timeSheet_uuid) {        
        timeSheetBusinessLogic.submitTimeSheet(timeSheet_uuid, Boolean.TRUE);
    }
    
    public void onSignBySupervisorRow(String timeSheet_uuid) {        
        timeSheetBusinessLogic.submitTimeSheet(timeSheet_uuid, Boolean.FALSE);
    }

    public void onRowView(String timeSheetUUId, String displayStrings) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/logged_in/view_timesheet_entries.xhtml?id=" + timeSheetUUId + "&contractID=" + contract_id);
    }

    public void addNewEntry(String timeSheetUUId, String displayString) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/logged_in/timesheet_entry.xhtml?id=" + timeSheetUUId + "&timeSheetDateRange=" + displayString + "&contractID=" + contract_id);
    }

}
