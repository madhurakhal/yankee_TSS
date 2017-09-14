/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee17.entities.EmployeeEntity;
import jee17.entities.PersonEntity;
import jee17.logic.EmployeeBusinessLogic;
import jee17.logic.dao.ContractAccess;
import jee17.logic.dao.EmployeeAccess;
import jee17.logic.dao.PersonAccess;
import jee17.logic.to.Employee;
import jee17.logic.to.Person;

/**
 *
 * @author Sabs
 */
@Stateless
public class EmployeeBusinessLogicImpl implements EmployeeBusinessLogic {

    @EJB
    private EmployeeAccess employeeAccess;

    @EJB
    private PersonAccess personAccess;

    @EJB
    private ContractAccess contractAccess;

    @Override
    public List<Employee> getEmployeeList() {
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
    public Employee createEmployee(String name, String personUUID) {
        // To Think Will I create a lot of Employee with different contract ID
        //EmployeeEntity se = employeeAccess.getCreateEmployeeByName(name);
        EmployeeEntity ee = employeeAccess.createEntity(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        ee.setPerson(pe);
        employeeAccess.updateEntity(ee); // not sure if we have to update
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Employee(ee.getUuid(), ee.getName());
    }

    @Override
    public Employee getEmployeeByContract(String contractUUID) {
        EmployeeEntity ee = employeeAccess.getEmployeeByContract(contractAccess.getByUuid(contractUUID));
        // Need to create a SUpervisor list from transfer object
        if (ee == null){return null;}
        Employee e = new Employee(ee.getUuid(), ee.getName());
        PersonEntity person = ee.getPerson();
        Person p = new Person(person.getUuid() , person.getName());
        p.setFirstName(person.getFirstName());
        p.setLastName(person.getLastName());
        p.setEmailAddress(person.getEmailAddress());
        // Fill up all other contract info
        e.setPerson(p);
        return e;
    }
}
