/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;

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
     * @param contractName
     * @param supervisor
     * @param assistant
     * @param secretary
     * @param employee
     * @return 
     */
    public Contract createContract(String contractName, Person supervisor, Person assistant, Person secretary, Person employee);
    
    public Contract editContract(String contractUUID , Person supervisorPerson , List<Person> secretaries , boolean secretariesChanged, List<Person> assistants , boolean assistantsChanged, Date startDate, Date endDate );
    
    public Contract startContract(String contractUUID);
    
    public Contract getContractByUUID(String contractUUID);
    
}
