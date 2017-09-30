package yankee.logic.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import yankee.entities.TimesheetEntryEntity;

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
    
    public java.util.List<TimesheetEntryEntity> getTimeSheetEntriesForTimeSheet(String timeSheetUUID)
    {
        return em.createNamedQuery("getTimeSheetEntriesForTimeSheet",TimesheetEntryEntity.class).setParameter("timeSheetUUID", timeSheetUUID).getResultList();
    }
    
    public TimesheetEntryEntity findByPrimaryKey(Long id)
    {
        return em.find(TimesheetEntryEntity.class, id);
    }
    
    
    
    
}
