/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import yankee.entities.AdministrationEntity;
import yankee.logic.ENUM.GermanyStatesEnum;


@Stateless
@LocalBean
public class AdministrationtAccess extends AbstractAccess<AdministrationEntity> {

    @Override
    public AdministrationEntity createEntity(String name) {
        name = name.trim().toLowerCase();        
        AdministrationEntity se = super.createEntity(name);
        se.setGermanState(GermanyStatesEnum.RHINELANDPALATINATE);
        return se;
    }

    @Override
    protected Class<AdministrationEntity> getEntityClass() {
        return AdministrationEntity.class;
    }

    @Override
    protected AdministrationEntity newEntity() {
        return new AdministrationEntity(true);
    }

    public AdministrationEntity getAdminSetState() {
        String name = "state";       
        try {
            return em.createNamedQuery("getAdminSetStateByName", AdministrationEntity.class)
                .setParameter("name", name)
                .getSingleResult();
        } catch (NoResultException ex) {
            // Create entity if not available
            return this.createEntity(name);
        }
    }    

    @Override
    public long getEntityCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
