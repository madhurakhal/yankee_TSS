/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.logic;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Remote;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.to.Person;
import yankee.logic.to.PublicHolidays;

/**
 *
 * @author Sabs
 */
@Remote 
public interface PublicHolidaysBusinessLogic {
    /**
     * Returns a list of all {@link Person}s.
     *
     * @param state
     * @return the person list, sorted by name
     */
    public List<PublicHolidays> getPublicHolidaysByState(GermanyStatesEnum state);   
    
    public boolean databaseEmpty();
    public void createPublicHolidays(String name, GermanyStatesEnum stateName ,int day , int month, int year , int dayOfWeek, String localName , String englishName);
        
}
