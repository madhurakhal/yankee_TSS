/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import yankee.entities.TimesheetEntity;
import yankee.logic.ENUM.TimesheetStatusEnum;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */


@Stateless
@LocalBean
public class TimeSheetAccess extends AbstractAccess<TimesheetEntity>{

    @Override
    protected Class<TimesheetEntity> getEntityClass() {
            return TimesheetEntity.class;
        }

    @Override
    protected TimesheetEntity newEntity() {
        return new TimesheetEntity(true);
    }
    
    // ADDED For Review Sabin
    @Override
    public TimesheetEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        TimesheetEntity tse = super.createEntity(name);
        try {
            tse.setStatus(TimesheetStatusEnum.IN_PROGRESS);
        }catch (Exception ex) {
            Logger.getLogger(SupervisorAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tse;
    }
    //

    @Override
    public long getEntityCount() {
        return 0l; // change as required.
    }
    
  
    public List<TimesheetEntity> getTimeSheetsForContract(String contractUUID) {
        return em.createNamedQuery("getTimeSheetsForContract",TimesheetEntity.class).setParameter("contractUUID",contractUUID).getResultList();
    }
    
    
    public TimesheetEntity findByPrimaryKey(final Long id)
    {
        return em.find(TimesheetEntity.class, id);
    }
    
    public void deleteTimeSheet(final List<TimesheetEntity>objList)
    {
        for(TimesheetEntity e:objList)
        {
            em.remove(e);
        }
        
    }
    
}
