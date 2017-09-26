package yankee.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.to.Contract;

@ManagedBean
@ViewScoped
public class PrintpreviewtimesheetBean {

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

    private String contract_id;
    private String timesheetID;

    public String getTimesheetID() {
        if (timesheetID == null) {
            Map<String, String> params = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap();
            timesheetID = params.get("id");
        }
        return timesheetID;
    }

    public void setTimesheetID(String timesheetID) {
        this.timesheetID = timesheetID;
    }
    private Person currentContractPerson;
    private Contract contractinfo;
    
    private Person supervisorForContract;
    private List<Person> secretariesForContract = new ArrayList<>();
    private List<Person> assistantsForContract = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();


    @PostConstruct
    public void init() {
        //This contract id will be sent to edit contract as parameter in url from managecontract edit is pressed
        // This will be used in getting current assistant, supervisor, secretary for this contract id below.
        System.out.println("SHIRHARSH WAS HEREEEE");
        getContract_id();
        getTimesheetID();
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
            contract_id = params.get("contractID");
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
    
    
        
    
    
    
    
}
