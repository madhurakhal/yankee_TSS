package yankee.logic.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AssistantEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.dao.AssistantAccess;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.to.Assistant;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;

@Stateless
public class AssistantBusinessLogicImpl implements AssistantBusinessLogic {

    @EJB
    private AssistantAccess assistantAccess;
    
    @EJB
    private PersonAccess personAccess;
    
    @EJB
    private ContractAccess contractAccess;
    
    @EJB
    private EmployeeAccess employeeAccess;
    
    // TODO implement if required
    @Override
    public List<Assistant> getAssistantList(){
          return null;
    }
    
    @Override
    public Assistant createAssistant(String name , String personUUID) {
        AssistantEntity se = assistantAccess.createEntity(name);
        PersonEntity pe = personAccess.getByUuid(personUUID);
        se.setPerson(pe);
        assistantAccess.updateEntity(se);
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
            p.setEmailAddress(person.getEmailAddress());
            p.setPreferredLanguage(person.getPreferredLanguage());
            p.setUserRoleRealm(person.getUserRoleRealm());
            
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
    
    
        
    @Override
    public List<Person> getPersonsUnderAssistant(String assistantPersonUUID){
        List<AssistantEntity> lse = assistantAccess.getAssistantsByPerson(personAccess.getByUuid(assistantPersonUUID));
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
                p.setContractUUIDForRole(ee.getContract().getUuid());
                p.setContractStatusForRole(ee.getContract().getStatus());
                result.add(p);                
            }
        });    
        return result;
    }
}
