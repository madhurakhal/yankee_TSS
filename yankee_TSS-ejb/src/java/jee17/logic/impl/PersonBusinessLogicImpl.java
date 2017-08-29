/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee17.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee17.logic.PersonBusinessLogic;
import jee17.logic.dao.PersonAccess;
import jee17.logic.to.Person;

/**
 *
 * @author Sabs
 */
@Stateless
public class PersonBusinessLogicImpl implements PersonBusinessLogic{
    @EJB
    private PersonAccess personAccess;

    @Override
    public List<Person> getPersonDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
