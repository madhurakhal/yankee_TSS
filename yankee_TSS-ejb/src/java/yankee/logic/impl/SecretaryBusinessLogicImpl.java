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
import yankee.entities.ContractEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SecretaryEntity;
import yankee.entities.SupervisorEntity;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SecretaryAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import yankee.logic.to.Supervisor;

/**
 *
 * @author Sabs
 */
@Stateless
public class SecretaryBusinessLogicImpl implements SecretaryBusinessLogic {

    @EJB
    private SecretaryAccess secretaryAccess;
    
    @EJB
    private ContractAccess contractAccess;
    
    
    @EJB
    private PersonAccess personAccess;
    
    @EJB
    private EmployeeAccess employeeAccess;

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
    
    @Override
    public List<Secretary> getSecretariesByContract(String contractUUID) {
        List<SecretaryEntity> lse = secretaryAccess.getSecretariesByContract(contractAccess.getByUuid(contractUUID));
        // Need to create a SUpervisor list from transfer object
        List<Secretary> result = new ArrayList<>();
        for (SecretaryEntity se : lse) {
            Secretary s = new Secretary(se.getUuid(), se.getName());
            
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
    public Secretary getSecretaryByUUID(String UUID) {
        SecretaryEntity se = secretaryAccess.getByUuid(UUID);
        Secretary s = new Secretary(se.getUuid(), se.getName());
        s.setContract(new Contract(se.getContract().getUuid() , se.getContract().getName()));
        s.setPerson(new Person(se.getPerson().getUuid() , se.getPerson().getName()));
        s.setRoleType(se.getRollType());
        return s;
    }
    
    @Override
    public List<Person> getPersonsUnderSecretary(String secretaryPersonUUID){
        List<SecretaryEntity> lse = secretaryAccess.getSecretariesByPerson(personAccess.getByUuid(secretaryPersonUUID));
        List<Person> result = new ArrayList<>();
        lse.forEach((se) -> {
            EmployeeEntity ee = employeeAccess.getEmployeeByContract(se.getContract());
            if (ee != null) {
                PersonEntity pe = ee.getPerson();
                Person p = new Person(pe.getUuid() ,pe.getName()) ;
                p.setFirstName(pe.getFirstName());
                p.setLastName(pe.getLastName());
                p.setPreferredLanguage(pe.getPreferredLanguage());
                p.setDateOfBirth(pe.getDateOfBirth());
                p.setEmailAddress(pe.getEmailAddress());
                p.setUserRoleRealm(pe.getUserRoleRealm());
                result.add(p);                
            }
        });    
        return result;
    }
}
