package yankee.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
import yankee.logic.to.Contract;

@ManagedBean
@ViewScoped
@Named
public class viewContractDetailsBean {

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
    private Person currentContractPerson;
    private Contract contractinfo;
    private Person supervisorForContract;
    private List<Person> secretariesForContract = new ArrayList<>();
    private List<Person> assistantsForContract = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();

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
}
