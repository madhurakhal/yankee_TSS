/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.to.Person;

/**
 *
 * @author madhurakhalmagar
 */
@SessionScoped
@Named
public class AccountBean implements Serializable {

    @EJB
    private  PersonBusinessLogic personBusinessLogic;
    
    @Inject
    private LoginBean logInBean;
    
    private Person person;

    public Person getPerson() {
        person = personBusinessLogic.getPersonByName(logInBean.getUser().getName());
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
