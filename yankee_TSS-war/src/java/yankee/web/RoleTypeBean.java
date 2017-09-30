package yankee.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import yankee.logic.ENUM.RoleTypeEnum;

@RequestScoped
@Named
public class RoleTypeBean {    

    private RoleTypeEnum roleTypes;

    public void setRoleTypes(RoleTypeEnum roleTypes) {
        this.roleTypes = roleTypes;
    }

    public RoleTypeEnum[] getRoleTypes() {        
        return RoleTypeEnum.values();
    }
}
