package yankee.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@ManagedBean
@RequestScoped
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

    @Inject
    private LoginBean loginBean;

    private List<Person> persons = new ArrayList<>();

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
    private List<Person> secretariesForContract;
    private List<Person> assistantsForContract;

    private List<Person> availableSecretaryList = new ArrayList<>();
    private List<Person> availableAssistantList = new ArrayList<>();
    private List<Person> availableSupervisorList = new ArrayList<>();

    private DualListModel<Person> secretaryPickupList;
    private DualListModel<Person> assistantPickupList;

    @PostConstruct
    public void init() {
        //This contract id will be sent to edit contract as parameter in url from managecontract edit is pressed
        // This will be used in getting current assistant, supervisor, secretary for this contract id below.

        getContract_id();
        getPersons();

        // First we will get all the assistant , supervisor , secretary for the given contract
        getAssistantsForContract();
        getSupervisorForContract();
        getSecretariesForContract();

        // After current assistant, supervisor and secretary are recieved. we set out the available for adding list of 
        // assistant, supervisor and secretary
        getAvailableSecretaryList();
        getAvailableSupervisorList();
        getAvailableAssistantList();

        // Note the supervisor is just one for a given contract. So supervisorForContract can be used.
        secretaryPickupList = new DualListModel<>(new ArrayList<>(availableSecretaryList), secretariesForContract);
        assistantPickupList = new DualListModel<>(new ArrayList<>(availableAssistantList), assistantsForContract);
        System.out.println("la haiiiiiiiiiiiiii" + availableSecretaryList);
        System.out.println(assistantPickupList);

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
        supervisorForContract = supervisorBusinessLogic.getSupervisorByContract(contract_id).getPerson();
        return supervisorForContract;
    }

    public void setSupervisorForContract(Person supervisorForContract) {
        this.supervisorForContract = supervisorForContract;
    }

    public List<Person> getSecretariesForContract() {
        if (secretariesForContract == null) {
            List<Secretary> ls = secretaryBusinessLogic.getSecretariesByContract(contract_id);
            List<Person> result = new ArrayList<>();
            for (Secretary s : ls) {
                result.add(s.getPerson());
            }
            secretariesForContract = result;
        }
        return secretariesForContract;
    }

    public void setSecretariesForContract(List<Person> secretariesForContract) {
        this.secretariesForContract = secretariesForContract;
    }

    public List<Person> getAssistantsForContract() {
        if (assistantsForContract == null) {
            List<Assistant> ls = assistantBusinessLogic.getAssistantsByContract(contract_id);
            List<Person> result = new ArrayList<>();
            for (Assistant s : ls) {
                result.add(s.getPerson());
            }
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
        if (availableSecretaryList == null) {
            for (Person p : persons) {
                System.out.println("People available for secretary" + p.getFirstName());
                if (secretariesForContract.contains(p)) {
                } else {
                    availableSecretaryList.add(p);
                }
            }
        }
        return availableSecretaryList;
    }

    public void setAvailableSecretaryList(List<Person> availableSecretaryList) {
        this.availableSecretaryList = availableSecretaryList;
    }

    public List<Person> getAvailableAssistantList() {
        for (Person p : persons) {
            if (assistantsForContract.contains(p)) {
            } else {
                availableAssistantList.add(p);
            }
        }
        return availableAssistantList;
    }

    public void setAvailableAssistantList(List<Person> availableAssistantList) {
        this.availableAssistantList = availableAssistantList;
    }

    public List<Person> getAvailableSupervisorList() {
        if (availableSupervisorList == null) {
            for (Person p : persons) {
                if (supervisorForContract.equals(p)) {
                } else {
                    availableSupervisorList.add(p);
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

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for(Object item : event.getItems()) {
            builder.append(((Person) item).getName()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
