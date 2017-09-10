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
import jee17.entities.ContractEntity;
import jee17.entities.PersonEntity;
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.ContractBusinessLogic;
import jee17.logic.ENUM.ContractStatusEnum;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.SupervisorBusinessLogic;
import jee17.logic.dao.ContractAccess;
import jee17.logic.dao.PersonAccess;
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
    private PersonAccess personAccess;

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
        ContractEntity se = contractAccess.createEntity(name);
        try {
           // get the supervisor details
           // TODOOOOOOOOOO neeed to fill up what to put in contract
         
        }catch (Exception ex) {
            Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TODOOOOOOOOOOOOOO have to think what to return
        return new Contract(se.getUuid() , se.getName());
    }
}
