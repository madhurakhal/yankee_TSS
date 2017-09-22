package yankee.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import yankee.entities.PersonEntity;
import yankee.logic.to.Person;
import org.riediger.ldap.DirectoryLookup;


@Stateless
@LocalBean
public class PersonAccess extends AbstractAccess<PersonEntity> {

    // NOTE WE have to deploy DirectoryLookupp ear given by professor in glassfish. Refer dropbox.
    @EJB
    private DirectoryLookup directoryLookup;

    @Override
    public PersonEntity createEntity(String name) {
        name = name.trim().toLowerCase();
        PersonEntity pe = super.createEntity(name);

        try {
            org.riediger.ldap.Person p = directoryLookup.lookupPerson(name);
            if (p != null) {
                pe.setFirstName(p.getFirstName());
                pe.setLastName(p.getLastName()); 
                pe.setEmailAddress(name);
            }
        } catch (NamingException ex) {
            Logger.getLogger(PersonAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pe;
    }

    @Override
    protected Class<PersonEntity> getEntityClass() {
        return PersonEntity.class;
    }

    @Override
    protected PersonEntity newEntity() {
        return new PersonEntity(true);
    }

    @Override
    public long getEntityCount() {
        return em.createNamedQuery("getPersonCount", Long.class
        ).getSingleResult();
    }

    public List<PersonEntity> getPersonList() {
        return em.createNamedQuery("getPersonList", PersonEntity.class
        ).getResultList();
    }

    @RolesAllowed("AUTHENTICATED")
    public PersonEntity getPersonByName(String name) {
        name = name.trim().toLowerCase();
        try {
            return em.createNamedQuery("getPersonByName", PersonEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create entity if not available
            return this.createEntity(name);
        }
    }

    // NEEED TO REVIEW...
    @RolesAllowed("AUTHENTICATED")
    public void storePersonDetails(Person person) {
        System.err.println("storePersonDetails " + person.getUuid());
        PersonEntity pe = getByUuid(person.getUuid());
        pe.setFirstName(person.getFirstName());
        pe.setLastName(person.getLastName());
        // Again have to find a way to get email and dateofbirth as diretory lookup only gives first and last name
        //pe.setDateOfBirth(person.getDateOfBirth());
        //pe.setEmailAddress(null);
    }
}
