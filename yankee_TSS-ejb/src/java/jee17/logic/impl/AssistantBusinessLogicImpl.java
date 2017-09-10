/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee17.entities.AssistantEntity;
import jee17.entities.PersonEntity;
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.AssistantBusinessLogic;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.SupervisorBusinessLogic;
import jee17.logic.dao.AssistantAccess;
import jee17.logic.dao.PersonAccess;
import jee17.logic.dao.SecretaryAccess;
import jee17.logic.dao.SupervisorAccess;
import jee17.logic.to.Assistant;
import jee17.logic.to.Person;
import jee17.logic.to.Supervisor;

/**
 *
 * @author Sabs
 */
@Stateless
public class AssistantBusinessLogicImpl implements AssistantBusinessLogic {

    @EJB
    private AssistantAccess assistantAccess;
    
    @EJB
    private PersonAccess personAccess;

    @Override
    public List<Assistant> getAssistantList(){
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
    public Assistant createAssistant(String name , String personUUID) {
        AssistantEntity se = assistantAccess.getCreateAssistantByName(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        assistantAccess.updateEntity(se); // not sure if we have to update
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Assistant(se.getUuid(), se.getName());
    }
}
