package yankee.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.entities.ContractEntity;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;
import yankee.utilities.UTILNumericSupport;

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
        init();
        return loggedinUser;
    }

    public void setLoggedinUser(Person loggedinUser) {
        this.loggedinUser = loggedinUser;
    }

    private Person supervisorForContract;
    private List<Person> secretariesForContract = new ArrayList<>();
    private List<Person> assistantsForContract = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();

    
    public boolean isSupervisor(String timeSheetUUID) {
        ContractEntity ce = timeSheetBusinessLogic.getContractByTimesheetUUID(timeSheetUUID);
        return ce.getSupervisor().getPerson().getUuid().equals(loggedinUser.getUuid());
    }
    
     public boolean isSupervisorContract() {
        Contract ce = contractBusinessLogic.getContractByUUID(contract_id);
        return ce.getSupervisor().getPerson().getUuid().equals(loggedinUser.getUuid());
    }
    

    public boolean isEmployee(String timeSheetUUID) {
        ContractEntity ce = timeSheetBusinessLogic.getContractByTimesheetUUID(timeSheetUUID);
        return ce.getEmployee().getPerson().getUuid().equals(loggedinUser.getUuid());
    }

    public boolean isEmployeeContract() {
        Contract ce = contractBusinessLogic.getContractByUUID(contract_id);
        return ce.getEmployee().getPerson().getUuid().equals(loggedinUser.getUuid());
    }
    
    public boolean isSecretary(String timeSheetUUID) {
        ContractEntity ce = timeSheetBusinessLogic.getContractByTimesheetUUID(timeSheetUUID);
        List<Secretary> ls = secretaryBusinessLogic.getSecretariesByContract(ce.getUuid());
        // For each secretary if one of the secretary matches the person logged in
        return !ls.stream().filter(e -> e.getPerson().getUuid().equals(loggedinUser.getUuid())).collect(Collectors.toList()).isEmpty();
    }
    
    public boolean isSecretaryContract() {
        Contract ce = contractBusinessLogic.getContractByUUID(contract_id);
        List<Secretary> ls = secretaryBusinessLogic.getSecretariesByContract(ce.getUuid());
        // For each secretary if one of the secretary matches the person logged in
        return !ls.stream().filter(e -> e.getPerson().getUuid().equals(loggedinUser.getUuid())).collect(Collectors.toList()).isEmpty();
    }
    

    //@PostConstruct
    public void init() {
        //This contract id will be sent to edit contract as parameter in url from managecontract edit is pressed
        // This will be used in getting current assistant, supervisor, secretary for this contract id below.
        getContract_id();
        getPersons();
        getCurrentContractPerson();
        loggedinUser = loginBean.getUser();

        // First we will get all the assistant , supervisor , secretary for the given contract
        getSupervisorForContract();
        getAssistantsForContract();
        getSecretariesForContract();

        // For timesheet
        this.timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(contract_id);
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

// RELATED TO STATISTICSSSSSSSSss For TimeSheet 
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
    // TIME SHEET TIME SHEEET
    /* BEGIN:::  TimeSheettts DISPLAY BEGINSSSS*/
    // All For Timesheets BEGINSSSS
    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;

    private List<TimeSheet> timesheets;
    private List<TimeSheetEntry> timeSheetEntries;
    private TimeSheet timeSheetFor;
    private String uuid;

    // Begins For tabs in contract details 
    TimeSheet currentTimeSheet;
    List<TimeSheet> previousTimeSheet;
    List<TimeSheet> signedByEmployeeTimeSheets;
    List<TimeSheet> signedBySupervisorTimeSheets;
    List<TimeSheet> inProgressTimeSheets;
    List<TimeSheet> inArchivedTimeSheets;
    List<TimeSheet> allTimeSheets;
    
    
    
    public List<TimeSheet> getSignedBySupervisorTimeSheets() {
        signedBySupervisorTimeSheets = timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.SIGNED_BY_SUPERVISOR))).collect(Collectors.toList());
        return signedBySupervisorTimeSheets;
    }

    public void setSignedBySupervisorTimeSheets(List<TimeSheet> signedBySupervisorTimeSheets) {
        this.signedBySupervisorTimeSheets = signedBySupervisorTimeSheets;
    }
    
    public List<TimeSheet> getPreviousTimeSheet() {
        // Get the current date today.
        LocalDate currentDate = LocalDate.now();
        //LocalDate currentDate = LocalDate.of(2017, 10, 15); // TRIAL TRIAL TRIAL
        previousTimeSheet = timesheets.stream().filter(e -> (e.getStartDate().isBefore(currentDate))).collect(Collectors.toList());   
        return previousTimeSheet;
    }

    public void setPreviousTimeSheet(List<TimeSheet> previousTimeSheet) {
        this.previousTimeSheet = previousTimeSheet;
    }  
    

    public TimeSheet getCurrentTimeSheet() {
        // Get the current date today.
        LocalDate currentDate = LocalDate.now();
        //LocalDate currentDate = LocalDate.of(2017, 10, 15);  // TRIAL TRIAL TRIAL
        List<TimeSheet> p = timesheets.stream().filter(e -> ((e.getStartDate().isBefore(currentDate) && e.getEndDate().isAfter(currentDate))
                || (e.getEndDate().isEqual(currentDate) || e.getStartDate().isEqual(currentDate))))
                .collect(Collectors.toList());
        if (p.isEmpty()) {
            this.currentTimeSheet = null;
        } else {
            this.currentTimeSheet = p.get(0);
        }
        return currentTimeSheet;
    }

    public void setCurrentTimeSheet(TimeSheet currentTimeSheet) {
        this.currentTimeSheet = currentTimeSheet;

    }

    public List<TimeSheet> getSignedByEmployeeTimeSheets() {
        signedByEmployeeTimeSheets = timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.SIGNED_BY_EMPLOYEE))).collect(Collectors.toList());
        return signedByEmployeeTimeSheets;
    }

    public void setSignedByEmployeeTimeSheets(List<TimeSheet> signedByEmployeeTimeSheets) {        
        this.signedByEmployeeTimeSheets = signedByEmployeeTimeSheets;
    }

    public List<TimeSheet> getInProgressTimeSheets() {
        inProgressTimeSheets = timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.IN_PROGRESS))).collect(Collectors.toList());
        return inProgressTimeSheets;
    }

    public void setInProgressTimeSheets(List<TimeSheet> inProgressTimeSheets) {
        this.inProgressTimeSheets = inProgressTimeSheets;
    }

    public List<TimeSheet> getInArchivedTimeSheets() {
        inArchivedTimeSheets = timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.ARCHIVED))).collect(Collectors.toList());
        return inArchivedTimeSheets;
    }

    public void setInArchivedTimeSheets(List<TimeSheet> inArchivedTimeSheets) {
        this.inArchivedTimeSheets = inArchivedTimeSheets;
    }

    public List<TimeSheet> getAllTimeSheets() {
        allTimeSheets = timesheets;
        return allTimeSheets;
    }

    public void setAllTimeSheets(List<TimeSheet> allTimeSheets) {
        this.allTimeSheets = allTimeSheets;
    }
    
    public double calculateHoursEntered(String timeSheetUUID){
        System.out.println("TIME SHEEEEEET ID for hours entere" + timeSheetUUID);
        List<TimeSheetEntry> lse = timeSheetBusinessLogic.getEntriesForTimeSheet(timeSheetUUID);
        return UTILNumericSupport.round(lse.stream().mapToDouble(o -> o.getHours()).sum(),2);
    }
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
        return timesheets;
    }

    
    public void onSignByEmployeeRow(String timeSheet_uuid) {
        timeSheetBusinessLogic.submitTimeSheet(timeSheet_uuid, Boolean.TRUE);        
    }

    public void onSignBySupervisorRow(String timeSheet_uuid) {
        timeSheetBusinessLogic.submitTimeSheet(timeSheet_uuid, Boolean.FALSE);
        TimeSheet t = timeSheetBusinessLogic.getByUUID(timeSheet_uuid);
        double hoursEntered = calculateHoursEntered(timeSheet_uuid);
        contractBusinessLogic.updateTotalHoursDue(t.getContract().getUuid(),hoursEntered);
    }

    public void onRowView(String timeSheetUUId, String displayStrings) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/logged_in/view_timesheet_entries.xhtml?id=" + timeSheetUUId + "&contractID=" + contract_id);
    }

    public void addNewEntry(String timeSheetUUId, String displayString) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/logged_in/timesheet_entry.xhtml?id=" + timeSheetUUId + "&timeSheetDateRange=" + displayString + "&contractID=" + contract_id);
    }

    public void printTimeSheet(String timeSheetUUId) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/staff_logged_in/printpreviewtimesheet.xhtml?id=" + timeSheetUUId + "&contractID=" + contract_id);

    }
    
     public void archiveTimeSheet(String timeSheetUUID) throws IOException {
        FacesMessage msg = new FacesMessage("TimeSheet has now been Archived" , "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        timeSheetBusinessLogic.archiveTimeSheet(timeSheetUUID);        
    }
     
    public void onRevokeSignatureBySupervisor(String timeSheetUUID){
        timeSheetBusinessLogic.revokeSignature(timeSheetUUID);
    }
    

}
