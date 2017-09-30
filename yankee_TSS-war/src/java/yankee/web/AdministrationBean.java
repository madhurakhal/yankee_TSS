package yankee.web;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.to.Administration;


@RequestScoped
@Named
public class AdministrationBean {   
    
    @EJB 
    private AdministrationBusinessLogic administrationBusinessLogic;
    
    private GermanyStatesEnum selectedState;
    private Administration adminSettingsInfo;    
    private GermanyStatesEnum germanyState;
    
    @PostConstruct
    public void init(){
        adminSettingsInfo = administrationBusinessLogic.getAdminSettingsInfo();
    }

    public Administration getAdminSettingsInfo() {
        return adminSettingsInfo;
    }

    public void setAdminSettingsInfo(Administration adminSettingsInfo) {
        this.adminSettingsInfo = adminSettingsInfo;
    }
    
    public GermanyStatesEnum getSelectedState() {
        selectedState = administrationBusinessLogic.getAdminSetState().getGermanState();
        return selectedState;
    }

    public void setSelectedState(GermanyStatesEnum selectedState) {
        this.selectedState = selectedState;
    }
    
    public GermanyStatesEnum[] getGermanyState() {
        return germanyState.values();
    }

    public void setGermanyState(GermanyStatesEnum germanyState) {
        this.germanyState = germanyState;
    }
    
    public void updateAdminInfo(){ 
        FacesMessage msg = new FacesMessage("Settings Updated!!" , "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        administrationBusinessLogic.updateAdminSettingsInfo(adminSettingsInfo);
    }

    public void setState(){
        administrationBusinessLogic.updateState(selectedState);
        FacesMessage msg = new FacesMessage("New State has been set!!" , "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void enableGuestLogin() throws IOException{
        administrationBusinessLogic.enableGuestLogin();
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        extContext.redirect(extContext.getRequestContextPath() + "/index.xhtml");
    }
    
    public void disableGuestLogin(){
        administrationBusinessLogic.disableGuestLogin();        
    }
}
