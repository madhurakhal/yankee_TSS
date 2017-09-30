package yankee.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

    @Inject
    private LoginBean loginbean;
    
    private static final long serialVersionUID = 1L;

    private Locale locale;
    private final Map<String, String> availableLanguages;

    private boolean flag;
    
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
        flag = true;
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();        
    }
    
    public Locale getLocale() {
        if(loginbean.isLoggedIn() && flag){            
            String language = loginbean.getUser().getPreferredLanguage().toString().toLowerCase();
            this.locale = new Locale(language);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);  
        } 
        return this.locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) {
        flag = false;
        this.locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
