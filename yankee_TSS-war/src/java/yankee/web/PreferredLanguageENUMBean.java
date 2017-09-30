package yankee.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import yankee.logic.ENUM.PreferredLanguageENUM;

@RequestScoped
@Named
public class PreferredLanguageENUMBean {    

    private PreferredLanguageENUM preferredLanguageENUM;

    public PreferredLanguageENUM[] getPreferredLanguageENUM() {
        return PreferredLanguageENUM.values();
    }

    public void setPreferredLanguageENUM(PreferredLanguageENUM preferredLanguageENUM) {
        this.preferredLanguageENUM = preferredLanguageENUM;
    }    
}
