/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@NamedQuery(name = "getNamedEntityByUuid",
        query = "SELECT e FROM NamedEntity e WHERE e.uuid = :uuid")

public abstract class NamedEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 36)
    private String uuid;

   
    
    
    private String name;

    public NamedEntity() {
    }

    public NamedEntity(boolean isNew) {
        if (isNew) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @Override
    public int hashCode() {
        if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
                if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final NamedEntity other = (NamedEntity) object;
        if (other.uuid == null) {
            throw new IllegalStateException("other.uuid not set");
        }
        if (Objects.equals(this.uuid, other.uuid)) {
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jee17.entities.NamedEntity[ id=" + id + " ]";
    }
    
}
