/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import yankee.entities.ContractEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SecretaryEntity;
import yankee.entities.SupervisorEntity;
import yankee.logic.ENUM.RoleTypeEnum;

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
    
    @RolesAllowed("AUTHENTICATED")
    public List<SecretaryEntity> getSecretariesByContract(ContractEntity contract) {
       try {
            em.flush();
            em.clear();
            return em.createNamedQuery("getSecretariesByContract", SecretaryEntity.class)
                    .setParameter("contract", contract)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }
    
     @RolesAllowed("AUTHENTICATED")
    // Person being himself
    public List<SecretaryEntity> getSecretariesByPerson(PersonEntity person) {
       try {
            return em.createNamedQuery("getSecretariesByPerson", SecretaryEntity.class)
                    .setParameter("person", person)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }
}
