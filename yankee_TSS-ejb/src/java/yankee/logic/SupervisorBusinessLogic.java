
package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;


@Remote 
public interface SupervisorBusinessLogic {
    
    public List<Person> getSupervisorList();
    
    public Supervisor createSupervisor(String name , String personUUID);
    
    public List<Supervisor> getSupervisorByPerson(String personUUID);
    
    public Supervisor getSupervisorByContract(String contractUUID);
    
    public List<Contract> getContracts(String personUUID);
    
    public List<Person> getPersonsUnderSupervisor(String supervisorPersonUUID);
}
