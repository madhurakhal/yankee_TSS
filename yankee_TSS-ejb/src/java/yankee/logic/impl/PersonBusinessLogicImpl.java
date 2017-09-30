package yankee.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AssistantEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.entities.RoleEntity;
import yankee.entities.SecretaryEntity;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SecretaryAccess;
import yankee.logic.dao.SupervisorAccess;
import yankee.logic.dao.AssistantAccess;
import yankee.logic.to.Person;
import yankee.logic.to.Role;

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
        l.stream().map((pe) -> {
            Person p = new Person(pe.getUuid(), pe.getName());
            p.setFirstName(pe.getFirstName());
            p.setLastName(pe.getLastName());
            p.setDateOfBirth(pe.getDateOfBirth());
            p.setEmailAddress(pe.getEmailAddress());
            p.setUserRoleRealm(pe.getUserRoleRealm());
            p.setPreferredLanguage(pe.getPreferredLanguage());
            ArrayList<Role> resultRole = new ArrayList<>(pe.getRoles().size());
            pe.getRoles().stream().map((re) -> {
                Role r = new Role(re.getUuid(), re.getName());
                r.setRoleType(re.getRollType());
                return r;
            }).forEachOrdered((r) -> {
                resultRole.add(r);
            });
            ;
            p.setRoles(resultRole);
            return p;
        }).forEachOrdered((p) -> {
            result.add(p);
        });
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
        p.setPreferredLanguage(pe.getPreferredLanguage());
        p.setLastName(pe.getLastName());
        p.setEmailAddress(pe.getEmailAddress());
        p.setDateOfBirth(pe.getDateOfBirth());
        p.setUserRoleRealm(pe.getUserRoleRealm());
        p.setPhoto(pe.getPhoto());
        return p;
    }

    @Override
    public void updateUserRoleRealm(String uuid, String realmRole) {
        PersonEntity pe = personAccess.getByUuid(uuid);
        pe.setUserRoleRealm(realmRole);
        personAccess.updateEntity(pe);
    }

    @Override
    public void updateDetails(Person updatedperson) {
        PersonEntity p = personAccess.getByUuid(updatedperson.getUuid());
        if (!p.getFirstName().equals(updatedperson.getFirstName())) {
            p.setFirstName(updatedperson.getFirstName());
        }
        if (!p.getLastName().equals(updatedperson.getLastName())) {
            p.setLastName(updatedperson.getLastName());
        }
        if (updatedperson.getDateOfBirth()!= null){
            p.setDateOfBirth(updatedperson.getDateOfBirth());
        }
        p.setPreferredLanguage(updatedperson.getPreferredLanguage());        
    }
    
    @Override
    public void updatePhoto(Person personphotoupdated){
       PersonEntity pe = personAccess.getByUuid(personphotoupdated.getUuid());
       pe.setPhoto(personphotoupdated.getPhoto());
    }

    @Override
    public void updatePersonDetails(String uuid, RoleTypeEnum roleType) {
        PersonEntity pe = personAccess.getByUuid(uuid);
        switch (roleType) {
            case ASSISTANT:
                AssistantEntity ae = assistantAccess.createEntity("secretary");
                ae.setPerson(pe);
                break;
            case EMPLOYEE:
                EmployeeEntity se = employeeAccess.createEntity("secretary");
                se.setPerson(pe);
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
