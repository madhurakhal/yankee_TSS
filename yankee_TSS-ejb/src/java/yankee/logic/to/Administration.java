package yankee.logic.to;

import yankee.logic.ENUM.GermanyStatesEnum;

public class Administration extends Named {
    private static final long serialVersionUID = 1L;
    
    private GermanyStatesEnum germanState;
    private boolean reminderServiceOn;
    private boolean guestLoggedIn;
    
    public Administration(String uuid, String name) {
        super(uuid, name);
    }

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
    
    public GermanyStatesEnum getGermanState() {
        return germanState;
    }

    public void setGermanState(GermanyStatesEnum germanState) {
        this.germanState = germanState;
    }
  

    
}
