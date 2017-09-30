package yankee.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import java.time.LocalDate;
import javax.ejb.Stateless;
import yankee.entities.TimesheetEntity;
import yankee.logic.ENUM.TimesheetStatusEnum;

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

    @Override
    public long getEntityCount() {
        return 0l; // change as required.
    }
    
  
    public List<TimesheetEntity> getTimeSheetsForContract(String contractUUID) {
        return em.createNamedQuery("getTimeSheetsForContract",TimesheetEntity.class).setParameter("contractUUID",contractUUID).getResultList();
    }
   
    public List<TimesheetEntity> getAllTimeSheetsByGivenDate(LocalDate givenDate) {
        return em.createNamedQuery("getAllRunningTimeSheet", TimesheetEntity.class).setParameter("givenDate", givenDate).getResultList();
    }
    
    public List<TimesheetEntity> getAllTimeSheetsSignedBySupervisor(LocalDate givenDate) {
        return em.createNamedQuery("getAllTimeSheetsSignedBySupervisor", TimesheetEntity.class).setParameter("givenDate", givenDate).getResultList();
    }
    
    public List<TimesheetEntity> getTimeSheetsForContractByID(Long contractId)
    {
        return em.createNamedQuery("getTimeSheetsForContractById",TimesheetEntity.class).setParameter("contractId",contractId).getResultList();
        
    }
    
    
    public List<TimesheetEntity> getOldTimeSheetSignedBySupervisor(LocalDate givenDate) {
        return em.createNamedQuery("getOldTimeSheetSignedBySupervisor", TimesheetEntity.class).setParameter("givenDate", givenDate).getResultList();
    }
    
    public TimesheetEntity findByPrimaryKey(final Long id)
    {
        return em.find(TimesheetEntity.class, id);
    }
    
    public void deleteTimeSheet(final List<TimesheetEntity>objList)
    {
        objList.forEach((e) -> {
            em.remove(e);
        });        
    }
 
       
}
