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
import jee17.entities.PersonEntity;
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.ENUM.RoleTypeEnum;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class SupervisorAccess extends AbstractAccess<SupervisorEntity> {

    @Override
    public SupervisorEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        SupervisorEntity se = super.createEntity(name);

        try {
            se.setRollType(RoleTypeEnum.SUPERVISOR);
        }catch (Exception ex) {
            Logger.getLogger(SupervisorAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return se;
    }

    @Override
    protected Class<SupervisorEntity> getEntityClass() {
        return SupervisorEntity.class;
    }

    @Override
    protected SupervisorEntity newEntity() {
        return new SupervisorEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPersonCount", Long.class
        ).getSingleResult();
    }
    
    @RolesAllowed("AUTHENTICATED")
    public SupervisorEntity getCreateSupervisorByName(String name) {
        name = name.trim().toLowerCase();

        try {
            // try to find supervisor
            return em.createNamedQuery("getSupervisorByName", SupervisorEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create a SupervisorEntity for the name.
            return createEntity(name);
        }
    }
    
    @RolesAllowed("AUTHENTICATED")
    public List<SupervisorEntity> getSupervisorByPerson(PersonEntity person) {
       try {
            return em.createNamedQuery("getSupervisorByPerson", SupervisorEntity.class)
                    .setParameter("person", person)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }
}
