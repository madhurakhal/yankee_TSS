package yankee.logic;

import java.util.List;
import javax.ejb.Remote;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.to.Person;

@Remote 
public interface PersonBusinessLogic {
   
    public List<Person> getPersonList();
    
    public Person createPerson(String name);
    
    public void updatePersonDetails(String uuid, RoleTypeEnum roleType);
    
    public void updateUserRoleRealm(String uuid , String realmRole);
            
    public Person getPersonByName(String name);
    
    public void updateDetails(Person updatedperson);    
  
    public void updatePhoto(Person photoupdated);

    public long getPersonCount();
    
        
}
