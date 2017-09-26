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
import yankee.entities.SupervisorEntity;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SupervisorAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;

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
    
    @EJB
    private EmployeeAccess employeeAccess;

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
        for (SupervisorEntity se : lse){
            Supervisor p = new Supervisor(se.getUuid(), se.getName());
            ContractEntity contract = se.getContract();
            Contract c = new Contract(contract.getUuid(), contract.getName());
            // Fill up all other contract info
            p.setContract(c);            
            PersonEntity pe = se.getPerson();
            p.setPerson(new Person(pe.getUuid(),pe.getName()));
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
        p.setEmailAddress(person.getEmailAddress());
        p.setPreferredLanguage(person.getPreferredLanguage());
        p.setUserRoleRealm(person.getUserRoleRealm());
        //p.setRoles(person.getRoles());
        // Fill up all other contract info
        s.setPerson(p);

        return s;
    }

    @Override
    public List<Contract> getContracts(String personUUID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public List<Person> getPersonsUnderSupervisor(String supervisorPersonUUID){
        List<SupervisorEntity> lse = supervisorAccess.getSupervisorByPerson(personAccess.getByUuid(supervisorPersonUUID));
        List<Person> result = new ArrayList<>();
        lse.forEach((se) -> {
            EmployeeEntity ee = employeeAccess.getEmployeeByContract(se.getContract());
            if (ee != null) {
                PersonEntity pe = ee.getPerson();
                Person p = new Person(pe.getUuid() ,pe.getName());
                p.setFirstName(pe.getFirstName());
                p.setLastName(pe.getLastName());
                p.setPreferredLanguage(pe.getPreferredLanguage());
                p.setDateOfBirth(pe.getDateOfBirth());
                p.setEmailAddress(pe.getEmailAddress());
                p.setUserRoleRealm(pe.getUserRoleRealm());
                p.setContractUUIDForRole(ee.getContract().getUuid());
                p.setContractStatusForRole(ee.getContract().getStatus());
                result.add(p);                
            }
        });    
        return result;
    }
}
