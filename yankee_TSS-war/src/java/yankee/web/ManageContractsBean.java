package yankee.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.Employee;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import yankee.logic.to.Supervisor;

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
public class ManageContractsBean {
    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;

    @Inject
    private LoginBean loginBean;

    private List<Person> personsAssociatedToContract = new ArrayList<>();
    Map<String, String> person_to_contract = new HashMap<>();
    
//    private String person_id;
//
//    public String getPerson_id() {
//        Map<String, String> params = FacesContext.getCurrentInstance()
//                .getExternalContext().getRequestParameterMap();
//        person_id = params.get("id");
//        return person_id;
//    }
//
//    public void setPerson_id(String person_id) {
//        this.person_id = person_id;
//    }
   
    // TODO hello world
//    public List<Contract> getContracts() {
//        List<Supervisor> sup = supervisorBusinessLogic.getSupervisorByPerson(loginBean.getUser().getUuid());
//        
//        return
//    }
    public List<Person> getPersonsAssociatedToContract() {
//        //because supervisor table will store supervisor id with contractid and person
//        List<Supervisor> ls = supervisorBusinessLogic.getSupervisorByPerson(loginBean.getUser().getUuid());
//        
//        //List<Secretary> lsecr = secretaryBusinessLogic.getSecretariesByPerson(loginBean.getUser().getUuid());
//        
//        ls.forEach((s) -> {
//            Employee e = employeeBusinessLogic.getEmployeeByContract(s.getContract().getUuid());
//            if (e != null) {
//                persons_associatedto_contract.add(e.getPerson());  
//                // Just a mapping so that contract is associaated to person
//                person_to_contract.put(e.getPerson().getUuid(), s.getContract().getUuid());
//            }
//        });      
//        return persons_associatedto_contract;
        personsAssociatedToContract = supervisorBusinessLogic.getPersonsUnderSupervisor(loginBean.getUser().getUuid());
        return personsAssociatedToContract;
    }
    
    
    
    

    public void setPersonsAssociatedToContract(List<Person> persons_associatedto_contract) {
        this.personsAssociatedToContract = persons_associatedto_contract;
    }   
    
    public void onRowEdit(String contract_uuid) throws IOException {
        //String contract_id = person_to_contract.get(person_uuid);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/staff_logged_in/editcontract.xhtml?id=" + contract_uuid);
    }
    
    public void onRowDelete(String contract_uuid) {
        
    }   
    
    
    // Need to get all persons who the current logged in user is 
    // 1. supervisor.
    // 2. Assistant
    // 3. 
    
    
}