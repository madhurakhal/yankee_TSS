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
        return null;
    }

    @Override
    public Supervisor createSupervisor(String name, String personUUID) {
        SupervisorEntity se = supervisorAccess.createEntity(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        supervisorAccess.updateEntity(se);
        return new Supervisor(se.getUuid(), se.getName());
    }

    @Override
    public List<Supervisor> getSupervisorByPerson(String personUUID) {
        List<SupervisorEntity> lse = supervisorAccess.getSupervisorByPerson(personAccess.getByUuid(personUUID));
        
       
        List<Supervisor> result = new ArrayList<>();
        lse.stream().map((se) -> {
            Supervisor p = new Supervisor(se.getUuid(), se.getName());
            ContractEntity contract = se.getContract();
            Contract c = new Contract(contract.getUuid(), contract.getName());
            // Fill up all other contract info
            p.setContract(c);            
            PersonEntity pe = se.getPerson();
            p.setPerson(new Person(pe.getUuid(),pe.getName()));
            return p;
        }).forEachOrdered((p) -> {
            result.add(p);
        });
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
        s.setPerson(p);

        return s;
    }

    @Override
    public List<Contract> getContracts(String personUUID) {
        throw new UnsupportedOperationException("Not supported yet."); 
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
