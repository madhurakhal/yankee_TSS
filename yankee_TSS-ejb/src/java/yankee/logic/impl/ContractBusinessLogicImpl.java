/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AssistantEntity;
import yankee.entities.ContractEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.SecretaryEntity;
import yankee.entities.SupervisorEntity;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.dao.AssistantAccess;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.EmployeeAccess;
import yankee.logic.dao.PersonAccess;
import yankee.logic.dao.SecretaryAccess;
import yankee.logic.dao.SupervisorAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;

/**
 *
 * @author Sabs
 */
@Stateless
public class ContractBusinessLogicImpl implements ContractBusinessLogic {

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private PersonAccess personAccess;

    @EJB
    private AssistantAccess assistantAccess;

    @EJB
    private EmployeeAccess employeeAccess;

    @EJB
    private SecretaryAccess secretaryAccess;

    @EJB
    private SupervisorAccess supervisorAccess;

    @Override
    public List<Person> getContractList() {
//        List<PersonEntity> l = personAccess.getPersonList();
//        List<Person> result = new ArrayList<>(l.size());
//        for (PersonEntity pe : l) {
//            Person p = new Person(pe.getUuid(), pe.getName());
//            p.setFirstName(pe.getFirstName());
//            p.setLastName(pe.getLastName());
//            p.setDateOfBirth(pe.getDateOfBirth());
//            result.add(p);
//        }
        return null;
    }

    // This roleTypeUUID is either supervisor, secretary, assistant, employee uuid
    @Override
    public Contract createContract(String contractName, Person supervisor, Person assistant, Person secretary, Person employee) {

        // When creating contract.
        // 1. First create contract and get contract id.
        // 2. now create employee with person associated to it.
        // 3. now create supervisor with person associated to it
        // 4. now create secretary as of yet in this createContract.
        // 5. now create assistant as of yet in this createContract.
        //1.
        ContractEntity ce = contractAccess.createEntity(contractName);
        System.out.println("Contract ko naam k ho ta" + ce.getName() + ce.getId());
        
        try {
            //2.
            EmployeeEntity ee = employeeAccess.createEntity(employee.getName());
            System.out.println("Employee ko naam k ho ta" + ee.getName() + ee.getId());
            ee.setPerson(personAccess.getByUuid(employee.getUuid()));
            ee.setContract(ce);

            //3.
            SupervisorEntity sve = supervisorAccess.createEntity(supervisor.getName());
            sve.setPerson(personAccess.getByUuid(supervisor.getUuid()));
            sve.setContract(ce);

            //4. Note not necessary that you will be provided with person secretary. So can be null
            // Usually if when creating contract assistant and secretary trying to create contract for supervisor.
            if (secretary != null) {
                SecretaryEntity se = secretaryAccess.createEntity(secretary.getName());
                se.setPerson(personAccess.getByUuid(secretary.getUuid()));
                se.setContract(ce);
            }
            
            //5. Note not necessary that you will be provided with person assistant. So can be null
            // Usually if when creating contract assistant and secretary trying to create contract for supervisor.
            if (assistant != null) {
                AssistantEntity ae = assistantAccess.createEntity(assistant.getName());
                ae.setPerson(personAccess.getByUuid(assistant.getUuid()));
                ae.setContract(ce);
                ae.setRollType(RoleTypeEnum.ASSISTANT);
            }            

        } catch (Exception ex) {
            Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODOOOOOOOOOOOOOO have to think what to return
        // Note that all contract values like timesheets and start end date and so on gets added when updating contract.
        // initially the above is correct. 
        return new Contract(ce.getUuid(), ce.getName());
    }
    
    @Override
    public Contract editContract(String contractUUID , Person supervisorPerson , List<Person> secretaries , boolean secretariesChanged, List<Person> assistants , boolean assistantsChanged, Date startDate, Date endDate ){
        // When updating contract 
        // We will receive 
        // 1. new supervisorFor contract .. check for null if no change. Delete and create if not null and associate this contract id.
        // 2. list of secretaries updated. BE SMART or easy is delete existing secretaries and create new ones.
        //    2.1  Note maybe check for null. because no change = null is also good.
        // 3. Similar as 2. This time assistant
        // 4. Set start and end date.

        ContractEntity ce = contractAccess.getByUuid(contractUUID);
        
        // 1. 
        SupervisorEntity se = supervisorAccess.getSupervisorByContract(contractAccess.getByUuid(contractUUID));
         // This means the current selected supervisor is different from one before
        if(!supervisorPerson.equals(se.getPerson())){
            // delete old supervisor for this contract
            // delete also the contract id for that supervisor.
            
            // create new supervisor with this contract id
            SupervisorEntity sve = supervisorAccess.createEntity(supervisorPerson.getName());
            sve.setPerson(personAccess.getByUuid(supervisorPerson.getUuid()));
            sve.setContract(ce);    
        } 
        
        // 2.
        
        // delete all secretaries for that contract
        if(secretariesChanged){
        List <SecretaryEntity> toDeleteSecretaries = secretaryAccess.getSecretariesByContract(ce);
        // For loop to delete
        
        // Now we create the secretaries given in the list
        
        
        }
        
        return new Contract(ce.getUuid() , ce.getName());
    }
}
