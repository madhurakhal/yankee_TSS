package yankee.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import yankee.logic.ENUM.RoleTypeEnum;


@Entity
@Table(name = "ROLE")
public abstract class RoleEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;
    @Enumerated(EnumType.STRING)
    private RoleTypeEnum rollType;
    
    @ManyToOne
    private PersonEntity person;
    
    public RoleTypeEnum getRollType() {
        return rollType;
    }

    public void setRollType(RoleTypeEnum rollType) {
        this.rollType = rollType;
    }
    
    public RoleEntity() {
    }

    public RoleEntity(boolean isNew){
        super(isNew);
    }    
    
    

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    } 
}
