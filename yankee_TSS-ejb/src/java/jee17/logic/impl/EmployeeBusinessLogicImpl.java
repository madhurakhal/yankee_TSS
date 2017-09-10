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
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.EmployeeBusinessLogic;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.SupervisorBusinessLogic;
import jee17.logic.dao.EmployeeAccess;
import jee17.logic.dao.PersonAccess;
import jee17.logic.dao.SecretaryAccess;
import jee17.logic.dao.SupervisorAccess;
import jee17.logic.to.Employee;
import jee17.logic.to.Person;
import jee17.logic.to.Supervisor;

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
    public Employee createEmployee(String name , String personUUID) {
        EmployeeEntity se = employeeAccess.getCreateEmployeeByName(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        employeeAccess.updateEntity(se); // not sure if we have to update
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Employee(se.getUuid(), se.getName());
    }
}
