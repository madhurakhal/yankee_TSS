/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee17.entities.ContractEntity;
import jee17.entities.EmployeeEntity;
import jee17.entities.PersonEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.ENUM.RoleTypeEnum;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class EmployeeAccess extends AbstractAccess<EmployeeEntity> {

    @Override
    public EmployeeEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        EmployeeEntity se = super.createEntity(name);

        try {
            se.setRollType(RoleTypeEnum.EMPLOYEE);
        }catch (Exception ex) {
            Logger.getLogger(EmployeeAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return se;
    }

    @Override
    protected Class<EmployeeEntity> getEntityClass() {
        return EmployeeEntity.class;
    }

    @Override
    protected EmployeeEntity newEntity() {
        return new EmployeeEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getEmployeeCount", Long.class
        ).getSingleResult();
    }
    
    @RolesAllowed("AUTHENTICATED")
    public EmployeeEntity getCreateEmployeeByName(String name) {
        name = name.trim().toLowerCase();

        try {
            // try to find supervisor
            return em.createNamedQuery("getEmployeeByName", EmployeeEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create a SupervisorEntity for the name.
            return createEntity(name);
        }
    }
    
    @RolesAllowed("AUTHENTICATED")
    public EmployeeEntity getEmployeeByContract(ContractEntity contract) {
       try {
            return em.createNamedQuery("getEmployeeByContract", EmployeeEntity.class)
                    .setParameter("contract", contract)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } 
    }
}
