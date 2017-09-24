package yankee.web;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.ENUM.RoleTypeEnum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class AdministrationBean {   
    
    @EJB 
    private AdministrationBusinessLogic administrationBusinessLogic;

    private GermanyStatesEnum selectedState;

    public GermanyStatesEnum getSelectedState() {
        selectedState = administrationBusinessLogic.getAdminSetState().getGermanState();
        return selectedState;
    }

    public void setSelectedState(GermanyStatesEnum selectedState) {
        this.selectedState = selectedState;
    }
    
    private GermanyStatesEnum germanyState;

    public GermanyStatesEnum[] getGermanyState() {
        return germanyState.values();
    }

    public void setGermanyState(GermanyStatesEnum germanyState) {
        this.germanyState = germanyState;
    }

    public void setState(){
        administrationBusinessLogic.updateState(selectedState);
        FacesMessage msg = new FacesMessage("New State Set. Used for public Holidays");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
