package yankee.logic.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.PublicHolidaysEntity;
import yankee.logic.ENUM.DayOfWeekEnum;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.PublicHolidaysBusinessLogic;
import yankee.logic.dao.PublicHolidaysAccess;
import yankee.logic.to.PublicHolidays;

@Stateless
public class PublicHolidaysBusinessLogicImpl implements PublicHolidaysBusinessLogic {

    @EJB
    private PublicHolidaysAccess publicHolidaysAccess;

    @Override
    public List<PublicHolidays> getPublicHolidaysByState(GermanyStatesEnum state) {
        List<PublicHolidaysEntity> lphe = publicHolidaysAccess.getPublicHolidaysByState(state);

        List<PublicHolidays> result = new ArrayList<>();
        for (PublicHolidaysEntity phe : lphe) {
            PublicHolidays ph = new PublicHolidays(phe.getUuid(), phe.getName());
            ph.setDayH(phe.getDayH());
            ph.setDayOfWeek(phe.getDayOfWeek());
            ph.setDayOfWeekEnum(phe.getDayOfWeekEnum());
            ph.setEnglishName(phe.getEnglishName());
            ph.setHolidayDate(phe.getHolidayDate());
            ph.setLocalName(phe.getLocalName());
            ph.setMonthH(phe.getMonthH());
            ph.setState(phe.getStateGermany());
            ph.setYearH(phe.getYearH());

            result.add(ph);
        }
        return result;
    }

    @Override
    public void createPublicHolidays(String name, GermanyStatesEnum stateName, int day, int month, int year, int dayOfWeek, String localName, String englishName) {

        PublicHolidaysEntity phe = publicHolidaysAccess.createEntity(name);

        phe.setStateGermany(stateName);
        phe.setDayH(day);
        phe.setMonthH(month);
        phe.setYearH(year);

        phe.setHolidayDate(LocalDate.of(year, month, day));
        phe.setDayOfWeek(dayOfWeek);

        DayOfWeekEnum _dayOfWeekEnum;
        switch (dayOfWeek) {
            case (1):
                _dayOfWeekEnum = DayOfWeekEnum.MONDAY;
                break;
            case (2):
                _dayOfWeekEnum = DayOfWeekEnum.TUESDAY;
                break;
            case (3):
                _dayOfWeekEnum = DayOfWeekEnum.WEDNESDAY;
                break;
            case (4):
                _dayOfWeekEnum = DayOfWeekEnum.THURSDAY;
                break;
            case (5):
                _dayOfWeekEnum = DayOfWeekEnum.FRIDAY;
                break;
            case (6):
                _dayOfWeekEnum = DayOfWeekEnum.SATURDAY;
                break;
            case (7):
                _dayOfWeekEnum = DayOfWeekEnum.SUNDAY;
                break;
            default:
                _dayOfWeekEnum = null;
                break;
        }
        phe.setDayOfWeekEnum(_dayOfWeekEnum);
        phe.setLocalName(localName);
        phe.setEnglishName(englishName);
    }

    @Override
    public boolean databaseEmpty() {
        Long countEntity = publicHolidaysAccess.getEntityCount();
        return (countEntity.intValue() == 0);
    }

    @Override
    public boolean isPublicHoliday(int day, int month, int year, GermanyStatesEnum state) {
        LocalDate date = LocalDate.of(year, month, day);
        PublicHolidaysEntity phe = publicHolidaysAccess.getPublicHolidaysByStateAndDate(state , date);
        return (phe != null);
    }
}
