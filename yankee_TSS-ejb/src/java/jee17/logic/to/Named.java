package jee17.logic.to;

import java.io.Serializable;
import java.util.Objects;

public abstract class Named implements Comparable<Named>, Serializable {

    private static final long serialVersionUID = -7385439782422256104L;

    private final String uuid;

    private final String name;

    public Named(String uuid, String name) {
        if (uuid == null) {
            throw new IllegalArgumentException("uuid must not be null");
        }
        this.uuid = uuid;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Named other = (Named) obj;
        return Objects.equals(this.uuid, other.uuid);
    }

    @Override
    public int compareTo(Named o) {
        int result = name.compareTo(o.name);
        if (result == 0) {
            result = uuid.compareTo(o.uuid);
        }
        return result;
    }
}
