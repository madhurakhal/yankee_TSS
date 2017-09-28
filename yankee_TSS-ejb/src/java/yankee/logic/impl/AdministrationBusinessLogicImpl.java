package yankee.logic.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.AdministrationEntity;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.dao.AdministrationtAccess;
import yankee.logic.to.Administration;

@Stateless
public class AdministrationBusinessLogicImpl implements AdministrationBusinessLogic {

    @EJB
    private AdministrationtAccess administrationtAccess;

    @Override
    public Administration getAdminSetState() {
        AdministrationEntity ae = administrationtAccess.getAdminEntity();
        if(ae == null){
            return null;
        }
        Administration a = new Administration(ae.getUuid(), ae.getName());
        a.setGermanState(ae.getGermanState());
        a.setGuestLoggedIn(ae.isGuestLoggedIn());
        a.setReminderServiceOn(ae.isReminderServiceOn());
        return a;
    }
    
    @Override
    public Administration getAdminSettingsInfo() {
        AdministrationEntity ae = administrationtAccess.getAdminEntity();
        if(ae == null){
            return null;
        }
        Administration a = new Administration(ae.getUuid(), ae.getName());
        a.setGermanState(ae.getGermanState());
        a.setGuestLoggedIn(ae.isGuestLoggedIn());
        a.setReminderServiceOn(ae.isReminderServiceOn());
        return a;
    }

    @Override
    public void updateState(GermanyStatesEnum ge) {
       AdministrationEntity ae = administrationtAccess.getAdminEntity();
       ae.setGermanState(ge);
    }

    @Override
    public void updateAdminSettingsInfo(Administration adminInfo) {
       AdministrationEntity ae = administrationtAccess.getAdminEntity();
       ae.setGermanState(adminInfo.getGermanState());
       ae.setReminderServiceOn(adminInfo.isReminderServiceOn());
    }

    @Override
    public void enableGuestLogin() {
        AdministrationEntity ae = administrationtAccess.getAdminEntity();
        ae.setGuestLoggedIn(true);        
    }

    @Override
    public void disableGuestLogin() {
        AdministrationEntity ae = administrationtAccess.getAdminEntity();
        ae.setGuestLoggedIn(false);
    }

    
}
