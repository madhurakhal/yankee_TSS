package yankee.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import yankee.logic.ENUM.GermanyStatesEnum;

/**
 *
 * @author Sabs
 */
@NamedQueries({
    @NamedQuery(name = "getAdminSetStateByName", query = "SELECT p FROM AdministrationEntity p WHERE p.name = :name")
})

@Entity
@Table(name = "ADMINISTRATION")
public class AdministrationEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;
    
    private GermanyStatesEnum germanState; 
    private boolean reminderServiceOn;
    private boolean guestLoggedIn;

    public boolean isReminderServiceOn() {
        return reminderServiceOn;
    }

    public void setReminderServiceOn(boolean reminderServiceOn) {
        this.reminderServiceOn = reminderServiceOn;
    }

    public boolean isGuestLoggedIn() {
        return guestLoggedIn;
    }

    public void setGuestLoggedIn(boolean guestLoggedIn) {
        this.guestLoggedIn = guestLoggedIn;
    }   
    
    public AdministrationEntity() {
    }
    
    public AdministrationEntity(boolean isNew){
        super(isNew);        
    }  
    
    public GermanyStatesEnum getGermanState() {
        return germanState;
    }

    public void setGermanState(GermanyStatesEnum germanState) {
        this.germanState = germanState;
    }
  

    
}
