/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "getAssistantCount", query = "SELECT COUNT(p) FROM AssistantEntity p"),
    @NamedQuery(name = "getAssistantList", query = "SELECT p FROM AssistantEntity p ORDER BY p.name, p.uuid"),
    @NamedQuery(name = "getAssistantByName", query = "SELECT p FROM AssistantEntity p WHERE p.name = :name"),
    @NamedQuery(name = "getAssistantsByContract", query = "SELECT p FROM AssistantEntity p WHERE p.contract = :contract"),    
    @NamedQuery(name = "getAssistantsByPerson", query = "SELECT p FROM AssistantEntity p WHERE p.person = :person")    
})

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
