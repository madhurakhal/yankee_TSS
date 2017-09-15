package yankee.web;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.ENUM.RoleTypeEnum;
import yankee.logic.to.Person;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@RequestScoped
@Named
public class RoleTypeBean {    

    private RoleTypeEnum roleTypes;

    public void setRoleTypes(RoleTypeEnum roleTypes) {
        this.roleTypes = roleTypes;
    }

    public RoleTypeEnum[] getRoleTypes() {        
        return roleTypes.values();
    }
}
