/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.ENUM.PreferredLanguageENUM;

/**
 *
 * @author Sabs
 */
@NamedQueries({
    @NamedQuery(name = "getAdminSetStateByName", query = "SELECT p FROM AdministrationEntity p WHERE p.name = :name")
})

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
@Entity
@Table(name = "ADMINISTRATION")
public class AdministrationEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;
    
    private GermanyStatesEnum germanState; 

    
    public AdministrationEntity() {
    }
    
    public AdministrationEntity(boolean isNew){
        super(isNew);        
    }  
    
    public GermanyStatesEnum getGermanState() {
        return germanState;
    }

    public void setGermanState(GermanyStatesEnum germanState) {
        this.germanState = germanState;
    }
  

    
}
