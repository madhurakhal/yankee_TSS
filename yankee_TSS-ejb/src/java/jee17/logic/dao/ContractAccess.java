/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee17.entities.ContractEntity;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */

@Stateless
@LocalBean
public class ContractAccess extends AbstractAccess<ContractEntity>{

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ContractEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        ContractEntity se = super.createEntity(name);
        return se;
    }
    
    public ContractEntity getContractEntity(String uuid)
    {
        return em.createNamedQuery("getContractEntityByUuid", ContractEntity.class).setParameter("uuid",uuid).getSingleResult();
    }
    
}
