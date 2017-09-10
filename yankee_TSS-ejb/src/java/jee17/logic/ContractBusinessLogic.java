/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic;

import java.util.List;
import javax.ejb.Remote;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.to.Contract;
import jee17.logic.to.Person;

/**
 *
 * @author Sabs
 */
@Remote 
public interface ContractBusinessLogic {
    /**
     * Returns a list of all {@link Person}s.
     *
     * @return the person list, sorted by name
     */
    public List<Person> getContractList();
    
    /**
     *
     * @param name
     * @param creatorUUID
     * @param assignedRoleUUID
     * @param assignedType
     * @return
     */
    public Contract createContract(String name, String creatorUUID, String assignedRoleUUID , RoleTypeEnum assignedType);
        
}
