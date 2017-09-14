/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee17.entities.PersonEntity;
import jee17.entities.SecretaryEntity;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.dao.PersonAccess;
import jee17.logic.dao.SecretaryAccess;
import jee17.logic.to.Secretary;

/**
 *
 * @author Sabs
 */
@Stateless
public class SecretaryBusinessLogicImpl implements SecretaryBusinessLogic {

    @EJB
    private SecretaryAccess secretaryAccess;
    
    @EJB
    private PersonAccess personAccess;

    @Override
    public List<Secretary> getSecretaryList() {
//        List<PersonEntity> l = personAccess.getPersonList();
//        List<Person> result = new ArrayList<>(l.size());
//        for (PersonEntity pe : l) {
//            Person p = new Person(pe.getUuid(), pe.getName());
//            p.setFirstName(pe.getFirstName());
//            p.setLastName(pe.getLastName());
//            p.setDateOfBirth(pe.getDateOfBirth());
//            result.add(p);
//        }
          return null;
    }
    
    @Override
    public Secretary createSecretary(String name , String personUUID) {
        // To Think Will I create a lot of Secretaries with different contract ID
        //SecretaryEntity se = secretaryAccess.getCreateSecretaryByName(name);
        SecretaryEntity se = secretaryAccess.createEntity(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        secretaryAccess.updateEntity(se); // not sure if we have to update
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Secretary(se.getUuid(), se.getName());
    }
}
