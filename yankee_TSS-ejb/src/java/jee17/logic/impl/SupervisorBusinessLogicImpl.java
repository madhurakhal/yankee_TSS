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
import jee17.entities.ContractEntity;
import jee17.entities.PersonEntity;
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.SupervisorBusinessLogic;
import jee17.logic.dao.ContractAccess;
import jee17.logic.dao.PersonAccess;
import jee17.logic.dao.SecretaryAccess;
import jee17.logic.dao.SupervisorAccess;
import jee17.logic.to.Contract;
import jee17.logic.to.Person;
import jee17.logic.to.Supervisor;

/**
 *
 * @author Sabs
 */
@Stateless
public class SupervisorBusinessLogicImpl implements SupervisorBusinessLogic {

    @EJB
    private SupervisorAccess supervisorAccess;

    @EJB
    private PersonAccess personAccess;

    @EJB
    private ContractAccess contractAccess;

    @Override
    public List<Person> getSupervisorList() {
//        List<PersonEntity> l = personAccess.getPersonList();
//        List<Person> result = new ArrayList<>();
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
    public Supervisor createSupervisor(String name, String personUUID) {
        // To Think Will I create a lot of Supervisors with different contract ID
        //SupervisorEntity se = supervisorAccess.getCreateSupervisorByName(name);
        SupervisorEntity se = supervisorAccess.createEntity(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        supervisorAccess.updateEntity(se); // not sure if we have to update
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Supervisor(se.getUuid(), se.getName());
    }

    @Override
    public List<Supervisor> getSupervisorByPerson(String personUUID) {
        List<SupervisorEntity> lse = supervisorAccess.getSupervisorByPerson(personAccess.getByUuid(personUUID));
        // Need to create a SUpervisor list from transfer object
        List<Supervisor> result = new ArrayList<>();
        for (SupervisorEntity se : lse) {
            Supervisor p = new Supervisor(se.getUuid(), se.getName());
            
            ContractEntity contract = se.getContract();
            Contract c = new Contract(contract.getUuid(), contract.getName());
            // Fill up all other contract info
            p.setContract(c);
            //Should we also update person?
            //p.setPerson(personAccess.getByUuid(personUUID));
            result.add(p);
        }
        return result;
    }

    @Override
    public Supervisor getSupervisorByContract(String contractUUID) {
        SupervisorEntity se = supervisorAccess.getSupervisorByContract(contractAccess.getByUuid(contractUUID));
       
        Supervisor s = new Supervisor(se.getUuid(), se.getName());
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

        return s;
    }
}
