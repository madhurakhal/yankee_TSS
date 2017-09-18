/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Assistant;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;

/**
 *
 * @author Sabs
 */
@Remote 
public interface AssistantBusinessLogic {
    /**
     * Returns a list of all {@link Person}s.
     *
     * @return the person list, sorted by name
     */
    public List<Assistant> getAssistantList();
    
    public Assistant createAssistant(String name , String personUUID);
    
    public List<Assistant> getAssistantsByContract(String contractUUID);
    
    public Assistant getAssistantByUUID(String UUID);
    
    public List<Person> getPersonsUnderAssistant(String assistantPersonUUID);
        
}
