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
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import jee17.entities.AssistantEntity;
import jee17.entities.PersonEntity;
import jee17.entities.SecretaryEntity;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.to.Person;
import org.riediger.ldap.DirectoryLookup;

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
        return em.createNamedQuery("getPersonCount", Long.class
        ).getSingleResult();
    }
}
