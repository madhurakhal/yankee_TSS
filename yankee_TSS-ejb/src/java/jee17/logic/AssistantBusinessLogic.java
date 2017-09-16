/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic;

import java.util.List;
import javax.ejb.Remote;
import jee17.logic.to.Assistant;
import jee17.logic.to.Person;
import jee17.logic.to.Supervisor;

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
        
}
