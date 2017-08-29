package org.riediger.ldap;

import java.util.SortedSet;
import javax.ejb.Remote;
import javax.naming.NamingException;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@Remote
public interface DirectoryLookup {

    /**
     * Tries to find the user with the specified <code>username</code> in the
     * LDAP directory of the University. When a matching user was found,
     * firstname and lastname are copied from LDAP. Otherwise, this method
     * returns <code>null</code>.
     *
     * @param username the user name (e-mail address) to look for
     *
     * @return a {@link Person} with name, firstName, and lastName from LDAP, or
     * <code>null</code> when <code>username</code> was not found.
     *
     * @throws javax.naming.NamingException in case of trouble contacting the
     * LDAP server
     */
    public Person lookupPerson(String username) throws NamingException;

    /**
     * Tries to find up to <code>countLimit</code> persons whose username,
     * firstname or lastname contains the strings given in
     * <code>nameFragment</code>. The match is successful if all fragments
     * (separated by spaces) are found in the in the respective user entry on
     * the LDAP directory of the University.
     *
     * @param nameFragment space separated search strings
     * @param countLimit maximum number of results
     *
     * @return a set of matching {@link Person}s with username, firstName, and
     * lastName from LDAP, ordered by name, or an empty set when no matches were
     * found.
     *
     * @throws javax.naming.NamingException in case of trouble contacting the
     * LDAP server
     */
    public SortedSet<Person> lookupPersonsByName(String nameFragment, long countLimit)
            throws NamingException;

}
