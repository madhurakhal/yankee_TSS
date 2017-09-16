/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AssistantEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SecretaryEntity;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.dao.AssistantAccess;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.to.Assistant;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;

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
    
    @EJB
    private ContractAccess contractAccess;

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
        // To Think Will I create a lot of assistant with different contract ID
        //AssistantEntity se = assistantAccess.getCreateAssistantByName(name);
        AssistantEntity se = assistantAccess.createEntity(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        assistantAccess.updateEntity(se); // not sure if we have to update
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Assistant(se.getUuid(), se.getName());
    }
    
    @Override
    public List<Assistant> getAssistantsByContract(String contractUUID) {
        List<AssistantEntity> lse = assistantAccess.getAssistantsByContract(contractAccess.getByUuid(contractUUID));
        
        List<Assistant> result = new ArrayList<>();
        for (AssistantEntity se : lse) {
            Assistant s = new Assistant(se.getUuid(), se.getName());
            
            PersonEntity person = se.getPerson();
            
            Person p = new Person(person.getUuid(), person.getName());
            p.setFirstName(person.getFirstName());
            p.setLastName(person.getLastName());
            p.setDateOfBirth(person.getDateOfBirth());
            p.setPreferredLanguage(person.getPreferredLanguage());
            p.setUserRoleRealm(person.getUserRoleRealm());
            //p.setRoles(person.getRoles());
            // Fill up all other contract info
            s.setPerson(p);            
            result.add(s);
        }
        return result;
    }

    @Override
    public Assistant getAssistantByUUID(String UUID) {
        AssistantEntity se = assistantAccess.getByUuid(UUID);
        Assistant s = new Assistant(se.getUuid(), se.getName());
        s.setContract(new Contract(se.getContract().getUuid() , se.getContract().getName()));
        s.setPerson(new Person(se.getPerson().getUuid() , se.getPerson().getName()));
        s.setRoleType(se.getRollType());
        return s;
    }
}
