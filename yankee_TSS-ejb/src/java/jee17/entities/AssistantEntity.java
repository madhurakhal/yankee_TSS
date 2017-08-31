/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// TODO: REMOVE relation getter setter as per our requirement. delete set if in collection.
@Entity
@Table(name = "ASSISTANT")
public class AssistantEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private ContractEntity contract;

    public AssistantEntity() {
    }

    public AssistantEntity(boolean isNew) {
        super(isNew);
    }

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

}
