package jee17.logic.to;

import jee17.logic.ENUM.RoleTypeEnum;

public class Role extends Named {

    private static final long serialVersionUID = 2813983198416172587L;

    private RoleTypeEnum roleType;   

    public RoleTypeEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }
    
    
    public Role(String uuid, String name) {
        super(uuid, name);
    }

}
