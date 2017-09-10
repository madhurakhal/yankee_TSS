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
import jee17.entities.AssistantEntity;
import jee17.entities.PersonEntity;
import jee17.entities.RoleEntity;
import jee17.entities.SecretaryEntity;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.dao.AssistantAccess;
import jee17.logic.dao.EmployeeAccess;
import jee17.logic.dao.PersonAccess;
import jee17.logic.dao.SecretaryAccess;
import jee17.logic.dao.SupervisorAccess;
import jee17.logic.to.Person;
import jee17.logic.to.Role;

/**
 *
 * @author Sabs
 */
@Stateless
public class PersonBusinessLogicImpl implements PersonBusinessLogic {

    @EJB
    private PersonAccess personAccess;

    @EJB
    private SecretaryAccess secretaryAccess;

    @EJB
    private EmployeeAccess employeeAccess;

    @EJB
    private SupervisorAccess supervisorAccess;

    @EJB
    private AssistantAccess assistantAccess;

    @Override
    public List<Person> getPersonList() {
        List<PersonEntity> l = personAccess.getPersonList();
        List<Person> result = new ArrayList<>(l.size());
        for (PersonEntity pe : l) {
            Person p = new Person(pe.getUuid(), pe.getName());
            p.setFirstName(pe.getFirstName());
            p.setLastName(pe.getLastName());
            p.setDateOfBirth(pe.getDateOfBirth());
            p.setEmailAddress(pe.getEmailAddress());

            ArrayList<Role> resultRole = new ArrayList<>(pe.getRoles().size());
            for (RoleEntity re : pe.getRoles()) {
                System.out.println("AT LEATST " + re.getRollType());
                Role r = new Role(re.getUuid(), re.getName());
                r.setRoleType(re.getRollType());
                resultRole.add(r);
            };
            p.setRoles(resultRole);
            result.add(p);
        }
        return result;
    }

    @Override
    public Person createPerson(String name) {
        PersonEntity p = personAccess.createEntity(name);
        return new Person(p.getUuid(), p.getName());
    }

    @Override
    public Person getPersonByName(String name) {
        PersonEntity pe = personAccess.getPersonByName(name);
        Person p = new Person(pe.getUuid(), pe.getName());
        p.setFirstName(pe.getFirstName());
        p.setLastName(pe.getLastName());
        p.setDateOfBirth(pe.getDateOfBirth());
        ArrayList<Role> resultRole = new ArrayList<>(pe.getRoles().size());
        for (RoleEntity re : pe.getRoles()) {
            Role r = new Role(re.getUuid(), re.getName());
            r.setRoleType(re.getRollType());
            resultRole.add(r);
        };
        p.setRoles(resultRole);
        return p;
    }

    @Override
    public void updatePersonDetails(String uuid, RoleTypeEnum roleType) {
        PersonEntity pe = personAccess.getByUuid(uuid);
        switch (roleType) {
            case ASSISTANT:
                AssistantEntity ae = assistantAccess.createEntity("secretary");
                ae.setPerson(pe);
                //ae.setRollType(roleType);
                break;
            case EMPLOYEE:
                SecretaryEntity se = secretaryAccess.createEntity("secretary");
                se.setPerson(pe);
                //se.setRollType(roleType);
                break;
            case SECRETARY:
                SecretaryEntity se1 = secretaryAccess.createEntity("secretary");
                se1.setPerson(pe);
                break;
            case SUPERVISOR:
                secretaryAccess.createEntity("secretary");
                break;
            default: ;
        }
    }

}
