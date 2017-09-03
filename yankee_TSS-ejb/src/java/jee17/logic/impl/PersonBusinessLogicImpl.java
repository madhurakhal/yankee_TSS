/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee17.entities.PersonEntity;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.dao.PersonAccess;
import jee17.logic.to.Person;

/**
 *
 * @author Sabs
 */
@Stateless
public class PersonBusinessLogicImpl implements PersonBusinessLogic {

    @EJB
    private PersonAccess personAccess;

    @Override
    public List<Person> getPersonList() {
        List<PersonEntity> l = personAccess.getPersonList();
        List<Person> result = new ArrayList<>(l.size());
        for (PersonEntity pe : l) {
            Person p = new Person(pe.getUuid(), pe.getName());
            p.setFirstName(pe.getFirstName());
            p.setLastName(pe.getLastName());
            p.setDateOfBirth(pe.getDateOfBirth());
            result.add(p);
        }
        return result;
    }
    
    @Override
    public Person createPerson(String name) {
        PersonEntity p = personAccess.createEntity(name);
        return new Person(p.getUuid(), p.getName());
    }
}
