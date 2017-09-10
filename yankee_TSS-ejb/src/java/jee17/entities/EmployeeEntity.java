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
    @NamedQuery(name = "getEmployeeCount", query = "SELECT COUNT(p) FROM EmployeeEntity p"),
    @NamedQuery(name = "getEmployeeList", query = "SELECT p FROM EmployeeEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getEmployeeByName", query = "SELECT p FROM EmployeeEntity p WHERE p.name = :name")
})
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
