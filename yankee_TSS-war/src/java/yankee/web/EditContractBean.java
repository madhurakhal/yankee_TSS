package yankee.web;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.TimesheetFrequencyEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.Employee;
import yankee.logic.to.Supervisor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@ManagedBean
@ViewScoped
@Named
public class EditContractBean {

    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;

    @EJB
    private SecretaryBusinessLogic secretaryBusinessLogic;

    @EJB
    private AssistantBusinessLogic assistantBusinessLogic;

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;

    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    @Inject
    private LoginBean loginBean;

    private Date startDate;
    private Date endDate;
    private TimesheetFrequencyEnum timesheetFrequency;    
    private double hoursPerWeek;
    private Integer workingDaysPerWeek;    
    private Integer vacationDaysPerYear;    
    private Person currentContractPerson;    
    private Contract contractInfo;

    public Contract getContractInfo() {
        contractInfo = contractBusinessLogic.getContractByUUID(contract_id);
        return contractInfo;
    }

    public void setContractInfo(Contract contractInfo) {
        this.contractInfo = contractInfo;
    }
    private List<Person> persons = new ArrayList<>();

    public double getHoursPerWeek() {
        hoursPerWeek = contractInfo.getHoursPerWeek();
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }
    
    public Integer getWorkingDaysPerWeek() {
        workingDaysPerWeek = contractInfo.getWorkingDaysPerWeek();
        return workingDaysPerWeek;
    }

    public void setWorkingDaysPerWeek(Integer workingDaysPerWeek) {
        this.workingDaysPerWeek = workingDaysPerWeek;
    }

    public Integer getVacationDaysPerYear() {
        vacationDaysPerYear =  contractInfo.getVacationDaysPerYear();
        return vacationDaysPerYear;
    }

    public void setVacationDaysPerYear(Integer vacationDaysPerYear) {
        this.vacationDaysPerYear = vacationDaysPerYear;
    }

    public Person getCurrentContractPerson() {
        if (currentContractPerson == null) {
            currentContractPerson = employeeBusinessLogic.getEmployeeByContract(contract_id).getPerson();
        }
        return currentContractPerson;
    }

    public void setCurrentContractPerson(Person currentContractPerson) {
        this.currentContractPerson = currentContractPerson;
    }

    public TimesheetFrequencyEnum getTimesheetFrequency() {
        timesheetFrequency = contractInfo.getFrequency();
        return timesheetFrequency;
    }

    public void setTimesheetFrequency(TimesheetFrequencyEnum timesheetFrequency) {
        this.timesheetFrequency = timesheetFrequency;
    }

    public Date getStartDate() {
        LocalDate localDate = contractBusinessLogic.getContractByUUID(contract_id).getStartDate();
        if(localDate != null){
        startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        LocalDate localDate = contractBusinessLogic.getContractByUUID(contract_id).getEndDate();
        if(localDate != null){
        endDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());  }      
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public List<Person> getPersons() {
        if (persons.isEmpty()) {
            persons = personBusinessLogic.getPersonList();
            // removes the person who is logged in
            //persons.remove(personBusinessLogic.getPersonByName(loginBean.getUser().getName()));
        }
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    Map<String, String> person_to_contract = new HashMap<>();
    private String contract_id;
    private Person supervisorForContract;
    private List<Person> secretariesForContract = new ArrayList<>();
    private List<Person> assistantsForContract = new ArrayList<>();
    private List<Person> _secretariesForContract = new ArrayList<>();
    private List<Person> _assistantsForContract = new ArrayList<>();

    private List<Person> availableSecretaryList = new ArrayList<>();
    private List<Person> availableAssistantList = new ArrayList<>();
    private List<Person> availableSupervisorList = new ArrayList<>();

    private DualListModel<Person> secretaryPickupList;
    private DualListModel<Person> assistantPickupList;

    @PostConstruct
    public void init() {
        //This contract id will be sent to edit contract as parameter in url from managecontract edit is pressed
        // This will be used in getting current assistant, supervisor, secretary for this contract id below.
        System.out.println("called me init only once yes?");
        getContract_id();
        getPersons();
        getCurrentContractPerson();        
        getContractInfo();

        // First we will get all the assistant , supervisor , secretary for the given contract
        getSupervisorForContract();
        getPreviousSelectedSupervisor();
        _assistantsForContract = new ArrayList<>(getAssistantsForContract());
        _secretariesForContract = new ArrayList<>(getSecretariesForContract());

        // After current assistant, supervisor and secretary are recieved. we set out the available for adding list of 
        // assistant, supervisor and secretary
        getAvailableSecretaryList();
        getAvailableSupervisorList();
        getAvailableAssistantList();

        // Note the supervisor is just one for a given contract. So supervisorForContract can be used.
        secretaryPickupList = new DualListModel<>(new ArrayList<>(availableSecretaryList), secretariesForContract);
        assistantPickupList = new DualListModel<>(new ArrayList<>(availableAssistantList), assistantsForContract);

    }

    // BEGINS GETTER AND SETTER for contract id then current assistant , supervisor, secretaries for given contract
    public String getContract_id() {
        if (contract_id == null) {
            Map<String, String> params = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap();
            contract_id = params.get("id");
        }
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public Person getSupervisorForContract() {
        if (supervisorForContract == null) {
            supervisorForContract = supervisorBusinessLogic.getSupervisorByContract(contract_id).getPerson();
 _helperCurrentSupervisor = supervisorForContract;
        }
        return supervisorForContract;
    }

    public void setSupervisorForContract(Person supervisorForContract) {
        this.supervisorForContract = supervisorForContract;
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

    public void setSecretariesForContract(List<Person> secretariesForContract) {
        this.secretariesForContract = secretariesForContract;
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

    public void setAssistantsForContract(List<Person> assistantsForContract) {
        this.assistantsForContract = assistantsForContract;
    }

    // BEGINS getter and setter for available secretary , assistant, supervisor list that can be made secretaries assistant, supervisor respec.
    // Note for this secretariesforcontract, supervisorforcontract, assistantforcontract has to be loaded. done in init().
    public List<Person> getAvailableSecretaryList() {
        if (availableSecretaryList.isEmpty()) {
            for (Person p : persons) {
                System.out.println("People available for secretary" + p.getFirstName());
                if (p.getUserRoleRealm() != null && !supervisorForContract.getUuid().equals(p.getUuid())&& !currentContractPerson.getUuid().equals(p.getUuid())) {
                    if (secretariesForContract.contains(p) || assistantsForContract.contains(p)) {
                    } else {
                        availableSecretaryList.add(p);
                    }
                }
            }
        }
        return availableSecretaryList;
    }

    public void setAvailableSecretaryList(List<Person> availableSecretaryList) {
        this.availableSecretaryList = availableSecretaryList;
    }

    public List<Person> getAvailableAssistantList() {
        if (availableAssistantList.isEmpty()) {
            for (Person p : persons) {
                if (p.getUserRoleRealm() != null && !supervisorForContract.getUuid().equals(p.getUuid()) && !currentContractPerson.getUuid().equals(p.getUuid())) {
                    if (assistantsForContract.contains(p) || secretariesForContract.contains(p)) {
                    } else {
                        availableAssistantList.add(p);
                    }
                }
            }
        }
        return availableAssistantList;
    }

    public void setAvailableAssistantList(List<Person> availableAssistantList) {
        this.availableAssistantList = availableAssistantList;
    }

    public List<Person> getAvailableSupervisorList() {
        if (availableSupervisorList.isEmpty()) {

            // fetch all contracts for pradip and get supervisor.
            // delete these supervisors from all persons.
            availableSupervisorList.add(supervisorForContract);
            for (Person p : persons) {
                boolean hashimAsSupervisor = false;
                if (p.getUserRoleRealm() != null && !currentContractPerson.getUuid().equals(p.getUuid())) {
                    List<Supervisor> ls = supervisorBusinessLogic.getSupervisorByPerson(p.getUuid());

                    for (Supervisor s : ls) {
                        Employee e = employeeBusinessLogic.getEmployeeByContract(s.getContract().getUuid());
                        if (e != null) {
                            if (e.getPerson().getUuid().equals(currentContractPerson.getUuid())) {
                                hashimAsSupervisor = true;
                                System.out.println("SUPERVISOR forssssssssss " + currentContractPerson.getFirstName() + e.getPerson().getFirstName());
                            }
                        }
                    };
                    // TODO Also need to check if this person is a supervisor of this current contract person
                    // CHECKKKK with professor

                    if (!hashimAsSupervisor) {
                        availableSupervisorList.add(p);
                    }
                }
            }
        }
        return availableSupervisorList;
    }

    public void setAvailableSupervisorList(List<Person> availableSupervisorList) {
        this.availableSupervisorList = availableSupervisorList;
    }

    // BEGINS DUAL LIST GETTER AND SETTER
    public DualListModel<Person> getSecretaryPickupList() {
        return secretaryPickupList;
    }

    public void setSecretaryPickupList(DualListModel<Person> secretaryPickupList) {
        this.secretaryPickupList = secretaryPickupList;
    }

    public DualListModel<Person> getAssistantPickupList() {
        return assistantPickupList;
    }

    public void setAssistantPickupList(DualListModel<Person> assistantPickupList) {
        this.assistantPickupList = assistantPickupList;
    }

    public void onTransferSecretary(TransferEvent event) {
        System.out.println("Called ontransfer");
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Person) item).getFirstName()).append("<br />");
            if (event.isAdd()) {
                assistantPickupList.getSource().remove((Person) item);
            }
            if (event.isRemove()) {
                assistantPickupList.getSource().add((Person) item);
            }
        }
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onTransferAssistant(TransferEvent event) {
        System.out.println("Called ontransfer");
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Person) item).getFirstName()).append("<br />");
            if (event.isAdd()) {
                secretaryPickupList.getSource().remove((Person) item);
            }
            if (event.isRemove()) {
                secretaryPickupList.getSource().add((Person) item);
            }
        }
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    
    private Person previousSelectedSupervisor;
    private Person _helperCurrentSupervisor;

    public Person getPreviousSelectedSupervisor() {
        previousSelectedSupervisor = supervisorForContract;
        return previousSelectedSupervisor;
    }

    public void setPreviousSelectedSupervisor(Person previousSelectedSupervisor) {
        this.previousSelectedSupervisor = previousSelectedSupervisor;
    }
    
    public void onChangeSupervisor(AjaxBehaviorEvent event){
        System.out.println("PREVIOUSSSSSSSSSSSSSSSSSSSSS" + previousSelectedSupervisor );
        System.out.println("SELECTTTTTTTTTTTTTTTTTTTTTTTTTTT" + supervisorForContract.getFirstName());
        secretaryPickupList.getSource().remove(supervisorForContract);
        assistantPickupList.getSource().remove(supervisorForContract);
        assistantPickupList.getTarget().remove(supervisorForContract);
        
        if (!previousSelectedSupervisor.getUuid().equals(_helperCurrentSupervisor.getUuid())){
            secretaryPickupList.getSource().add(previousSelectedSupervisor);
            assistantPickupList.getSource().add(previousSelectedSupervisor);
        }
        else {            
            assistantPickupList.getTarget().add(previousSelectedSupervisor);
        }
        previousSelectedSupervisor = supervisorForContract;
    }
    

    public void edit() {
        System.out.println("Edit in progress");
        // Make changes for all contract details.
        System.out.println("Supervisor" + supervisorForContract);
        System.out.println("New secretaries" + secretaryPickupList.getTarget());
        System.out.println("New assistant" + assistantPickupList.getTarget());
        System.out.println("Start date" + startDate);
        System.out.println("End date" + endDate);
        System.out.println("Contract id" + contract_id);

        // TODO 
        // 1. update supervisor for the contract if old supervisor is different
        // 2. create all the secretaries i.e. secretaryPickupList.getTarget - secretariesForContract
        // 3. create all the assistants i.e. assistantPickupList.getTarget - assistantsForContract
        // 4. depending on start and end date and timesheets should be created. All timesheet should have contract associated to it.
        final Set<Person> newSecretaries = new HashSet<>(secretaryPickupList.getTarget());
        final Set<Person> prevSecretaries = new HashSet<>(_secretariesForContract);
        boolean secretariesChanged = !(newSecretaries.equals(prevSecretaries));
        System.out.println("Secretaries Changed " + secretariesChanged);

        
        final Set<Person> newAssistants = new HashSet<>(assistantPickupList.getTarget());
        final Set<Person> prevAssistants = new HashSet<>(_assistantsForContract);
        boolean assistantsChanged = !(newAssistants.equals(prevAssistants));
        System.out.println("Previous Assistants " + _assistantsForContract);
        System.out.println("Assistants Changed " + assistantsChanged);
        

        contractBusinessLogic.editContract(contract_id, supervisorForContract, secretaryPickupList.getTarget(), secretariesChanged, assistantPickupList.getTarget(), assistantsChanged, startDate, endDate , timesheetFrequency , workingDaysPerWeek , vacationDaysPerYear , hoursPerWeek);
        FacesMessage msg = new FacesMessage("Contract Has Been Updated");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }    
    
}
