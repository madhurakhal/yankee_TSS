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
import yankee.entities.AssistantEntity;
import yankee.entities.ContractEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SecretaryEntity;
import yankee.logic.ENUM.RoleTypeEnum;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class AssistantAccess extends AbstractAccess<AssistantEntity> {

    @Override
    public AssistantEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        AssistantEntity se = super.createEntity(name);

        try {
            se.setRollType(RoleTypeEnum.ASSISTANT);
        }catch (Exception ex) {
            Logger.getLogger(AssistantAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return se;
    }

    @Override
    protected Class<AssistantEntity> getEntityClass() {
        return AssistantEntity.class;
    }

    @Override
    protected AssistantEntity newEntity() {
        return new AssistantEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getAssistantCount", Long.class
        ).getSingleResult();
    }
    
    @RolesAllowed("AUTHENTICATED")
    public AssistantEntity getCreateAssistantByName(String name) {
        name = name.trim().toLowerCase();

        try {
            em.flush();
            em.clear();
            // try to find Assistant
            return em.createNamedQuery("getAssistantByName", AssistantEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create a SupervisorEntity for the name.
            return createEntity(name);
        }
    }
    
    
    @RolesAllowed("AUTHENTICATED")
    public List<AssistantEntity> getAssistantsByContract(ContractEntity contract) {
       try {
            return em.createNamedQuery("getAssistantsByContract", AssistantEntity.class)
                    .setParameter("contract", contract)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }
    
    @RolesAllowed("AUTHENTICATED")
    // Person being himself
    public List<AssistantEntity> getAssistantsByPerson(PersonEntity person) {
       try {
            return em.createNamedQuery("getAssistantsByPerson", AssistantEntity.class)
                    .setParameter("person", person)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }
}
