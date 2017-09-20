/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author T540P
 */

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private final Map<String, String> availableLanguages;

    public Map<String, String> getAvailableLanguages() {
        return availableLanguages;
    }
    
    public LanguageBean() {
        availableLanguages = new HashMap<>();
        availableLanguages.put("en", "English");
        availableLanguages.put("de", "Deutsch");
    }
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
    }
}
