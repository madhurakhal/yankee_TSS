package jee17.logic.to;

import jee17.logic.ENUM.RoleTypeEnum;

public class Role extends Named {

    private static final long serialVersionUID = 2813983198416172587L;

    private RoleTypeEnum roleType;  
    
    private Person person;
    
    public Role(String uuid, String name) {
        super(uuid, name);
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public RoleTypeEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }   
    
}
