package jee17.web;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.ENUM.RoleTypeEnum;
import jee17.logic.SecretaryBusinessLogic;
import jee17.logic.to.Person;

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
public class SecretaryBean {

    @EJB
    private SecretaryBusinessLogic secretaryBusinessLogic;

    private Person person;
    private RoleTypeEnum roleType;

    public void setPersonBusinessLogic(PersonBusinessLogic personBusinessLogic) {
        this.secretaryBusinessLogic = secretaryBusinessLogic;
    }

    public RoleTypeEnum getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }

    public Person setSecretary() {
        if (person == null) {
            //personBusinessLogic.createPerson("sbhattarai@uni-koblenz.de");
            person = secretaryBusinessLogic.createSecretary("secretary");
        }
        return person;
    }
}
