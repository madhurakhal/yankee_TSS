/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import static javax.persistence.TemporalType.DATE;
import yankee.entities.TimesheetEntity;

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

    @Override
    public long getEntityCount() {
        return 0l; // change as required.
    }
    
    public List<TimesheetEntity> getTimeSheetsForContract(String contractId) {
        return em.createNamedQuery("getTimeSheetsForContract",TimesheetEntity.class).setParameter("contractId",contractId).getResultList();
    }
    

    public List<TimesheetEntity> getAllTimeSheetsByGivenDate(LocalDate givenDate) {
        return em.createNamedQuery("getAllRunningTimeSheet", TimesheetEntity.class).setParameter("givenDate", givenDate).getResultList();
    }
    
    public TimesheetEntity findByPrimaryKey(final Long id)
    {
        return em.find(TimesheetEntity.class, id);
    }    
}
