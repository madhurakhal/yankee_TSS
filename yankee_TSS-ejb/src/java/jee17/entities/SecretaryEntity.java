/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQueries({
    @NamedQuery(name = "getSecretaryCount", query = "SELECT COUNT(p) FROM SecretaryEntity p"),
    @NamedQuery(name = "getSecretaryList", query = "SELECT p FROM SecretaryEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getSecretaryByName", query = "SELECT p FROM SecretaryEntity p WHERE p.name = :name"),
    @NamedQuery(name = "getSecretariesByContract", query = "SELECT p FROM SecretaryEntity p WHERE p.contract = :contract")
        
})

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.

@Entity
@Table(name = "SECRETARY")
public class SecretaryEntity extends RoleEntity {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private ContractEntity contract;

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }
    
    public SecretaryEntity() {
    }
    
    public SecretaryEntity(boolean isNew){
        super(isNew);
    }
    
}
