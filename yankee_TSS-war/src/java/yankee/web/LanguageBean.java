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
import javax.annotation.PostConstruct;

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

    private Locale locale;
    private final Map<String, String> availableLanguages;

    public Map<String, String> getAvailableLanguages() {
        return availableLanguages;
    }
    
    public LanguageBean() {
        availableLanguages = new HashMap<>();
        availableLanguages.put("en", "English");
        availableLanguages.put("de", "Deutsch");
    }
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        System.out.println("ohoooo" + locale.getLanguage());
        return locale.getLanguage();
    }

    public void changeLanguage(String language) {
        System.out.println("New language is " + language);
        this.locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
