package jee17.web;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.ENUM.RoleTypeEnum;
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
       System.out.println("Am I here to update?" + roleType);   
       System.out.println("Am I here to update?" + selectedRoleTypes); 
    }
    
    
}
