package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;


@Remote 
public interface AssistantBusinessLogic {
    
    public List<Assistant> getAssistantList();
    
    public Assistant createAssistant(String name , String personUUID);
    
    public List<Assistant> getAssistantsByContract(String contractUUID);
    
    public Assistant getAssistantByUUID(String UUID);
    
    public List<Person> getPersonsUnderAssistant(String assistantPersonUUID);
        
}
