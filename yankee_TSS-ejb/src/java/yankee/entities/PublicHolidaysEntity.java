package yankee.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import yankee.logic.ENUM.DayOfWeekEnum;
import yankee.logic.ENUM.GermanyStatesEnum;


@NamedQueries({
    @NamedQuery(name = "getPublicHolidaysCount", query = "SELECT COUNT(p) FROM PublicHolidaysEntity p"),
    @NamedQuery(name = "getPublicHolidaysList", query = "SELECT p FROM PublicHolidaysEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getPublicHolidaysByState", query = "SELECT p FROM PublicHolidaysEntity p WHERE p.stateGermany = :state"),
    @NamedQuery(name = "getPublicHolidaysByStateAndDate", query = "SELECT p FROM PublicHolidaysEntity p WHERE p.stateGermany = :state AND p.holidayDate = :date")        
})

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
@Entity
@Table(name = "PUBLICHOLIDAYS")
public class PublicHolidaysEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;
    
    private GermanyStatesEnum stateGermany;

    public GermanyStatesEnum getStateGermany() {
        return stateGermany;
    }

    public void setStateGermany(GermanyStatesEnum stateGermany) {
        this.stateGermany = stateGermany;
    }
    
    private int dayH;    
    private int monthH;
    private int yearH;
    private LocalDate holidayDate;
    private int dayOfWeek;
    private DayOfWeekEnum dayOfWeekEnum;
    private String localName;
    private String englishName;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
     

    public PublicHolidaysEntity() {
    }
    
    public PublicHolidaysEntity(boolean isNew){
        super(isNew);        
    }
    
    public int getDayH() {
        return dayH;
    }

    public void setDayH(int dayH) {
        this.dayH = dayH;
    }

    public int getMonthH() {
        return monthH;
    }

    public void setMonthH(int monthH) {
        this.monthH = monthH;
    }

    public int getYearH() {
        return yearH;
    }

    public void setYearH(int yearH) {
        this.yearH = yearH;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeekEnum getDayOfWeekEnum() {
        return dayOfWeekEnum;
    }

    public void setDayOfWeekEnum(DayOfWeekEnum dayOfWeekEnum) {
        this.dayOfWeekEnum = dayOfWeekEnum;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }
    
}
