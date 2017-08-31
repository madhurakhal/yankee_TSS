/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends RoleEntity {
    private static final long serialVersionUID = 1L;
    
    @OneToOne
    private ContractEntity contract;

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }
    
    public EmployeeEntity() {
    }
    
    public EmployeeEntity(boolean isNew){
        super(isNew);
    }
    
    
    
}
