/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import yankee.entities.TimesheetEntryEntity;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
@Stateless
@LocalBean
public class TimeSheetEntryAccess extends AbstractAccess<TimesheetEntryEntity>{

    @Override
    protected Class<TimesheetEntryEntity> getEntityClass() {
        return TimesheetEntryEntity.class;
    }

    @Override
    protected TimesheetEntryEntity newEntity() {
        return new TimesheetEntryEntity(true);
    }

    @Override
    public long getEntityCount() {
        return 0;
     }
    
    public java.util.List<TimesheetEntryEntity> getTimeSheetEntriesForTimeSheet(Long id)
    {
        return em.createNamedQuery("getEntryForTimeSheet",TimesheetEntryEntity.class).setParameter("timesheetId", id).getResultList();
    }
    
    public TimesheetEntryEntity findByPrimaryKey(Long id)
    {
        return em.find(TimesheetEntryEntity.class, id);
    }
    
    
    
    
}
