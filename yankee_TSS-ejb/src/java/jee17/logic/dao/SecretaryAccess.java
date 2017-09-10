/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.dao;

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
public class SecretaryAccess extends AbstractAccess<SecretaryEntity> {

    @Override
    public SecretaryEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        SecretaryEntity se = super.createEntity(name);

        try {
            se.setRollType(RoleTypeEnum.SECRETARY);
        }catch (Exception ex) {
            Logger.getLogger(SecretaryAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return se;
    }

    @Override
    protected Class<SecretaryEntity> getEntityClass() {
        return SecretaryEntity.class;
    }

    @Override
    protected SecretaryEntity newEntity() {
        return new SecretaryEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getSecretaryCount", Long.class
        ).getSingleResult();
    }
    
    @RolesAllowed("AUTHENTICATED")
    public SecretaryEntity getCreateSecretaryByName(String name) {
        name = name.trim().toLowerCase();

        try {
            // try to find secretary
            return em.createNamedQuery("getSecretaryByName", SecretaryEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create a SecretaryEntity for the name.
            return createEntity(name);
        }
    }
}
