package yankee.logic;

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
    
    public List<PublicHolidays> getPublicHolidaysByState(GermanyStatesEnum state);   
    
    public boolean databaseEmpty();
    public void createPublicHolidays(String name, GermanyStatesEnum stateName ,int day , int month, int year , int dayOfWeek, String localName , String englishName);
    public boolean isPublicHoliday(int day , int month , int year , GermanyStatesEnum state);   
}
