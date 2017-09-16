/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.impl;

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
import yankee.logic.dao.SecretaryAccess;
import yankee.logic.dao.SupervisorAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;

/**
 *
 * @author Sabs
 */
@Stateless
public class ContractBusinessLogicImpl implements ContractBusinessLogic {

    @EJB
    private ContractAccess contractAccess;
    
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
    public Contract createContract(String name, String creatorUUID, String assignedRoleUUID , RoleTypeEnum assignedType) {
        ContractEntity ce = contractAccess.createEntity(name);
        try {
           // get the supervisor details
           // TODOOOOOOOOOO neeed to fill up what to put in contract
           //Also a switch case for assigned Type . So if employee one to one.
           // So if assistant one to 0 many
           // So if Secretary one to many
           
           // Since always supervisor  // May change for assistant also
           SupervisorEntity sve = supervisorAccess.getByUuid(creatorUUID);
           sve.setContract(ce);
           supervisorAccess.updateEntity(sve);          
           
           switch (assignedType) {
            case ASSISTANT:
                AssistantEntity ae = assistantAccess.getByUuid(assignedRoleUUID);
                ae.setContract(ce);
                assistantAccess.updateEntity(ae);
                break;
            case EMPLOYEE:
                EmployeeEntity ee = employeeAccess.getByUuid(assignedRoleUUID);
                ee.setContract(ce);
                employeeAccess.updateEntity(ee);
                break;
            case SECRETARY:
                SecretaryEntity se = secretaryAccess.getByUuid(assignedRoleUUID);
                se.setContract(ce);
                secretaryAccess.updateEntity(se);
                break;
            default: 
                Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null , "NO Roll Type associated");
                break;
        }
           
         
        }catch (Exception ex) {
            Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Contract(ce.getUuid() , ce.getName());
    }
}
