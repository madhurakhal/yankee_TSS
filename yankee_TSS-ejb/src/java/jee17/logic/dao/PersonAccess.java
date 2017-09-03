/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import jee17.entities.PersonEntity;
import jee17.logic.to.Person;
import org.riediger.ldap.DirectoryLookup;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
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
            // try to find firstname and lastname in directory
            System.out.println("Here Before");
            org.riediger.ldap.Person p = directoryLookup.lookupPerson(name);
            System.out.println("Here After" + p);
            if (p != null) {
                pe.setFirstName(p.getFirstName());
                pe.setLastName(p.getLastName());
                // Have to get these details from somewhere. Since directory lookup only gives firstname and last name
                pe.setDateOfBirth(null);
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
        System.out.println("DO I COME TO CREATE A PERSON BY ITS NAME :- " + name);
        try {
            // try to find a person
            return em.createNamedQuery("getPersonByName", PersonEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            // Create a PersonEntity for the name.
            System.out.println("NOW is there a exception? when creating name? i.e creating entity");
            return createEntity(name);
        }
    }

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
