package jee17.web;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.to.Person;

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

    public List<Person> getPersons() {
        if (persons == null) {
            personBusinessLogic.createPerson("sbhattarai@uni-koblenz.de");
            persons = personBusinessLogic.getPersonList();
        }
        return persons;
    }
}
