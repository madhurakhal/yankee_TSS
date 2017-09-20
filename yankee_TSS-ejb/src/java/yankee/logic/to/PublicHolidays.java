/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic.to;

import java.time.LocalDate;
import yankee.logic.ENUM.DayOfWeekEnum;
import yankee.logic.ENUM.GermanyStatesEnum;

/**
 *
 * @author Sabs
 */
public class PublicHolidays extends Named {
    private static final long serialVersionUID = 1L;
    
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
    
    public PublicHolidays(String uuid, String name) {
        super(uuid, name);
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
    
    private GermanyStatesEnum stateGermany;

    public GermanyStatesEnum getState() {
        return stateGermany;
    }

    public void setState(GermanyStatesEnum state) {
        this.stateGermany = state;
    }    
}
