package jee17.web;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import jee17.logic.AssistantBusinessLogic;
import jee17.logic.ContractBusinessLogic;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.EmployeeBusinessLogic;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.SupervisorBusinessLogic;
import jee17.logic.to.Person;
import jee17.logic.to.Role;
import org.primefaces.event.RowEditEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class CreateContractBean {

    @EJB
    private PersonBusinessLogic personBusinessLogic;
    
    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;
    
    @EJB
    private AssistantBusinessLogic assistantBusinessLogic;
    
    @EJB
    private SecretaryBusinessLogic secretaryBusinessLogic;
    
    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;
    
    
    @EJB
    private ContractBusinessLogic contractBusinessLogic;
    
    @Inject
    private LoginBean loginBean;

    private List<Person> persons;
    private RoleTypeEnum roleType;
    private Person contractTo;
    private List<RoleTypeEnum> selectedRoleTypes;

    public List<RoleTypeEnum> getSelectedRoleTypes() {
        return selectedRoleTypes;
    }

    public void setSelectedRoleTypes(List<RoleTypeEnum> selectedRoleTypes) {
        this.selectedRoleTypes = selectedRoleTypes;
    }

    public Person getContractTo() {
        return contractTo;
    }

    public void setContractTo(Person contractTo) {
        this.contractTo = contractTo;
    }

    
    public void setPersonBusinessLogic(PersonBusinessLogic personBusinessLogic) {
        this.personBusinessLogic = personBusinessLogic;
    }

    public RoleTypeEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }

    public List<Person> getPersons() {
        if (persons == null) {
            //personBusinessLogic.createPerson("sbhattarai@uni-koblenz.de");
            persons = personBusinessLogic.getPersonList();
            for (Person i : persons)
            {
                ArrayList<Role> roles = i.getRoles();
                System.out.println("email" + i.getEmailAddress());
                for (Role j : roles){
                    System.out.println("name " + i.getFirstName());
                    System.out.println("huh" + j.getRoleType());
                    
                }
            }            
        }
        return persons;
    }
    
    
    // Look this up on test.xhtml it makes use of it.
    public void updatePersonDetails(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Person) event.getObject()).getUuid());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        String selectedPerson_uuid = ((Person) event.getObject()).getUuid();
        personBusinessLogic.updatePersonDetails(selectedPerson_uuid, roleType);
        System.out.println("Am I here to update?" + roleType);               
    }
    
    public void newContractInit() {
       System.out.println("Called for contract create"); 
       Person loggedInUser = loginBean.getUser();
       // Make the current loggedin person as supervisor
       String supervisorUUID;// i.e. supervisor or 
       
       supervisorUUID = supervisorBusinessLogic.createSupervisor(loggedInUser.getName(), loggedInUser.getUuid()).getUuid();
       
       // switch case to make role associated person
       String assignedRoleUUID;
       RoleTypeEnum e = RoleTypeEnum.ASSISTANT;
       switch(roleType){
            case ASSISTANT:
                assignedRoleUUID = assistantBusinessLogic.createAssistant(loggedInUser.getName(), loggedInUser.getUuid()).getUuid();
                break;
            case EMPLOYEE :
                assignedRoleUUID = employeeBusinessLogic.createEmployee(loggedInUser.getName(), loggedInUser.getUuid()).getUuid();
                break;
            case SECRETARY: 
                assignedRoleUUID = secretaryBusinessLogic.createSecretary(loggedInUser.getName(), loggedInUser.getUuid()).getUuid();
                break;  
            default:
                assignedRoleUUID = null; break;
       }
       
       // Create new contract with all persons related to it i.e supervisor , assitant or employee or secretary
       contractBusinessLogic.createContract("contract", supervisorUUID , assignedRoleUUID , roleType);
       // Get the person whose contract is to be created. 
       // 
       // Go through the roles and create roles for that person
       System.out.println("Am I here to update?" + loggedInUser.getFirstName());   
       System.out.println("Am I here to update?" + roleType); 
    }  
    
}
