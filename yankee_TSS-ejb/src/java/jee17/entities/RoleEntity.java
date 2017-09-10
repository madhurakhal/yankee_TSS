/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import jee17.logic.ENUM.RoleTypeEnum;

/**
 *
 * TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
 */
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
