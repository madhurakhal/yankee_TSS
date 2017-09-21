/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import yankee.entities.ContractEntity;
import yankee.logic.ENUM.ContractStatusEnum;

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
    
    public ContractEntity getContractEntity(String uuid)
    {
        return em.createNamedQuery("getContractEntityByUuid", ContractEntity.class).setParameter("uuid",uuid).getSingleResult();
    }

    public ContractEntity findByPrimaryKey(final Long id)
    {
        return em.find(ContractEntity.class, id);
    }
    

}
