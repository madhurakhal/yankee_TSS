package yankee.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;

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
