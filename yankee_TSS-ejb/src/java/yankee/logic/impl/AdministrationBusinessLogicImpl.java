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
        AdministrationEntity ae = administrationtAccess.getAdminSetState();
        if(ae == null){
            return null;
        }
        Administration a = new Administration(ae.getUuid(), ae.getName());
        a.setGermanState(ae.getGermanState());
        return a;
    }

    @Override
    public void updateState(GermanyStatesEnum ge) {
       AdministrationEntity ae = administrationtAccess.getAdminSetState();
       ae.setGermanState(ge);
    }

    
}
