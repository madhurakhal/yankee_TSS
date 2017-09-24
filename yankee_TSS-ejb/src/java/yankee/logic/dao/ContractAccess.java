package yankee.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import yankee.entities.ContractEntity;
import yankee.entities.EmployeeEntity;
import yankee.entities.PersonEntity;
import yankee.entities.SupervisorEntity;
import yankee.logic.ENUM.ContractStatusEnum;

@Stateless
@LocalBean
public class ContractAccess extends AbstractAccess<ContractEntity> {

    @Override
    public ContractEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        ContractEntity se = super.createEntity(name);
        try {
            se.setStatus(ContractStatusEnum.PREPARED);
        } catch (Exception ex) {
            Logger.getLogger(ContractAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return se;
    }

    @Override
    protected Class<ContractEntity> getEntityClass() {
        return ContractEntity.class;
    }

    @Override
    protected ContractEntity newEntity() {
        return new ContractEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getContractCount", Long.class
        ).getSingleResult();
    }

    public List<ContractEntity> getContractList() {
        return em.createNamedQuery("getContractList", ContractEntity.class
        ).getResultList();
    }
    
    public List<ContractEntity> getContractByEmployee(EmployeeEntity employee){
        try {
            return em.createNamedQuery("getContractByEmployee", ContractEntity.class)
                    .setParameter("employee", employee)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }
    
    @RolesAllowed("AUTHENTICATED")
    // Person being himself
    public List<ContractEntity> getContractsByPerson(PersonEntity person) {
       try {
            return em.createNamedQuery("getContractByPerson", ContractEntity.class)
                    .setParameter("person", person)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } 
    }


    ///////////////////////////////////////////////////////////////////////////
    // Should be able to DELETE 
    public ContractEntity getContractEntity(String uuid) {
        return em.createNamedQuery("getContractEntityByUuid", ContractEntity.class).setParameter("uuid", uuid).getSingleResult();
    }

    // TO REVIEW???? 
    public ContractEntity findByPrimaryKey(final Long id) {
        return em.find(ContractEntity.class, id);
    }

}
