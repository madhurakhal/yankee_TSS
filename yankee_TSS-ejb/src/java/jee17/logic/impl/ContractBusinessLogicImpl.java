/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee17.entities.AssistantEntity;
import jee17.entities.ContractEntity;
import jee17.entities.EmployeeEntity;
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.ContractBusinessLogic;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.dao.AssistantAccess;
import jee17.logic.dao.ContractAccess;
import jee17.logic.dao.EmployeeAccess;
import jee17.logic.dao.SecretaryAccess;
import jee17.logic.dao.SupervisorAccess;
import jee17.logic.to.Contract;
import jee17.logic.to.Person;

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
