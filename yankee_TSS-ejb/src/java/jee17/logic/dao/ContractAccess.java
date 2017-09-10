/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee17.entities.ContractEntity;
import jee17.entities.SecretaryEntity;
import jee17.entities.SupervisorEntity;
import jee17.logic.ENUM.ContractStatusEnum;
import jee17.logic.ENUM.RoleTypeEnum;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Stateless
@LocalBean
public class ContractAccess extends AbstractAccess<ContractEntity> {

    @Override
    public ContractEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        ContractEntity se = super.createEntity(name);

        try {
            se.setStatus(ContractStatusEnum.PREPARED);
        }catch (Exception ex) {
            Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return se;
    }

    @Override
    protected Class<ContractEntity> getEntityClass() {
        return ContractEntity.class;
    }

    @Override
    protected ContractEntity newEntity() {
        return new ContractEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getContractCount", Long.class
        ).getSingleResult();
    }
}
