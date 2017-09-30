package yankee.logic;

import javax.ejb.Remote;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.to.Administration;


@Remote 
public interface AdministrationBusinessLogic {
    public Administration getAdminSetState();
    public void updateState(GermanyStatesEnum ge);
    public void updateAdminSettingsInfo(Administration adminInfo);
    public Administration getAdminSettingsInfo();
    public void enableGuestLogin();
    public void disableGuestLogin();
}
