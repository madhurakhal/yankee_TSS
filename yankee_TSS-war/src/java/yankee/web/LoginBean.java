/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.to.Person;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@SessionScoped
@Named
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -3525598371957295227L;

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    private String principalName;

    private Person user;

    public Person getUser() {
        Principal currentPrincipal = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        String currentPrincipalName = currentPrincipal == null
                ? null
                : currentPrincipal.getName().trim().toLowerCase();
        if (Objects.equals(currentPrincipalName, principalName)) {
            return user;
        }
        if (currentPrincipalName == null) {
            principalName = null;
            user = null;
        } else {
            principalName = currentPrincipalName;
            user = personBusinessLogic.getPersonByName(principalName);
            // Also update user group
            if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("STAFF")){
                personBusinessLogic.updateUserRoleRealm(user.getUuid() , "STAFF");
            }
        }
        return user;
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public void logout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.invalidateSession();
        principalName = null;
        user = null;
        try {
            ec.redirect(ec.getRequestContextPath());
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
}
