package yankee.logic.dao;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import yankee.entities.PublicHolidaysEntity;
import yankee.logic.ENUM.GermanyStatesEnum;

@Stateless
@LocalBean
public class PublicHolidaysAccess extends AbstractAccess<PublicHolidaysEntity> {

    @Override
    public PublicHolidaysEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        return super.createEntity(name);
    }

    @Override
    protected Class<PublicHolidaysEntity> getEntityClass() {
        return PublicHolidaysEntity.class;
    }

    @Override
    protected PublicHolidaysEntity newEntity() {
        return new PublicHolidaysEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPublicHolidaysCount", Long.class
        ).getSingleResult();
    }

    public List<PublicHolidaysEntity> getPublicHolidaysList() {
        return em.createNamedQuery("getPublicHolidaysList", PublicHolidaysEntity.class
        ).getResultList();
    }

    @RolesAllowed("AUTHENTICATED")
    public List<PublicHolidaysEntity> getPublicHolidaysByState(GermanyStatesEnum state) {
        try {
            return em.createNamedQuery("getPublicHolidaysByState", PublicHolidaysEntity.class)
                    .setParameter("state", state)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public PublicHolidaysEntity getPublicHolidaysByStateAndDate(GermanyStatesEnum state, LocalDate date) {
        try {
            return em.createNamedQuery("getPublicHolidaysByStateAndDate", PublicHolidaysEntity.class)
                    .setParameter("state", state)
                    .setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
