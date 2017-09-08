package jee17.web;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.to.Person;
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
public class PersonListBean {

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    private List<Person> persons;
    private RoleTypeEnum roleType;

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
            
        }
        return persons;
    }
    
    public void updatePersonDetails(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Person) event.getObject()).getUuid());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        String selectedPerson_uuid = ((Person) event.getObject()).getUuid();
        personBusinessLogic.updatePersonDetails(selectedPerson_uuid, roleType);
        System.out.println("Am I here to update?" + roleType);
               
    }
}
