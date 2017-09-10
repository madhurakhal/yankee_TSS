/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "getSupervisorCount", query = "SELECT COUNT(p) FROM SupervisorEntity p"),
    @NamedQuery(name = "getSupervisorList", query = "SELECT p FROM SupervisorEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getSupervisorByName", query = "SELECT p FROM SupervisorEntity p WHERE p.name = :name")
})

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
@Entity
@Table(name = "SUPERVISOR")
public class SupervisorEntity extends RoleEntity {
    private static final long serialVersionUID = 1L;
    
    @OneToOne
    private ContractEntity contract;

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }
    
    public SupervisorEntity() {
    }
    
    public SupervisorEntity(boolean isNew){
        super(isNew);
    }
    
    
    
}
