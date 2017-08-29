package org.riediger.ldap;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@XmlRootElement
public class Person implements Serializable, Comparable<Person> {

    private static final long serialVersionUID = -2560156749431283486L;

    private String name;
    private String firstName;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int compareTo(Person o) {
        if (o == this) {
            return 0;
        }
        return name.compareTo(o.name);
    }
}
