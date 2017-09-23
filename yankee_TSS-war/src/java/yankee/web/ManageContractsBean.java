package yankee.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.to.Person;


@RequestScoped
@Named
public class ManageContractsBean {
    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;
    
    @EJB
    private AssistantBusinessLogic assistantBusinessLogic;
    
    @EJB
    private SecretaryBusinessLogic secretaryBusinessLogic;
    
    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    @Inject
    private LoginBean loginBean;

    private List<Person> personsAssociatedToContractSupervisor = new ArrayList<>();
    private List<Person> personsAssociatedToContractAssistant = new ArrayList<>();    
    private List<Person> personsAssociatedToContractSecretary = new ArrayList<>();

    public List<Person> getPersonsAssociatedToContractAssistant() {
        personsAssociatedToContractAssistant = assistantBusinessLogic.getPersonsUnderAssistant(loginBean.getUser().getUuid());
        return personsAssociatedToContractAssistant;
    }

    public void setPersonsAssociatedToContractAssistant(List<Person> personsAssociatedToContractAssistant) {
        this.personsAssociatedToContractAssistant = personsAssociatedToContractAssistant;
    }

    public List<Person> getPersonsAssociatedToContractSecretary() {
        personsAssociatedToContractSecretary = secretaryBusinessLogic.getPersonsUnderSecretary(loginBean.getUser().getUuid());
        return personsAssociatedToContractSecretary;
    }

    public void setPersonsAssociatedToContractSecretary(List<Person> personsAssociatedToContractSecretary) {
        this.personsAssociatedToContractSecretary = personsAssociatedToContractSecretary;
    }

    // TODO hello world
//    public List<Contract> getContracts() {
//        List<Supervisor> sup = supervisorBusinessLogic.getSupervisorByPerson(loginBean.getUser().getUuid());
//        
//        return
//    }
    
    public List<Person> getPersonsAssociatedToContractSupervisor() {
        personsAssociatedToContractSupervisor = supervisorBusinessLogic.getPersonsUnderSupervisor(loginBean.getUser().getUuid());
        return personsAssociatedToContractSupervisor;
    }    

    public void setPersonsAssociatedToContractSupervisor(List<Person> persons_associatedto_contract) {
        this.personsAssociatedToContractSupervisor = persons_associatedto_contract;
    }   
    
    public void onRowEdit(String contract_uuid) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/staff_logged_in/editcontract.xhtml?id=" + contract_uuid);
    }
    
    public void onRowDelete(String contract_uuid) {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Contract Deleted");
        FacesContext.getCurrentInstance().addMessage(null, msg);
  
        contractBusinessLogic.deleteContract(contract_uuid);      
    }  
    
    public void onRowView(String contract_uuid) throws IOException {
        contractBusinessLogic.updateContractStatistics(contract_uuid);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/staff_logged_in/contractdetails.xhtml?id=" + contract_uuid);        
    } 
    
     public void onRowPrint(String contract_uuid) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/staff_logged_in/printpreviewcontract.xhtml?id=" + contract_uuid);        
    }
    
    public void onRowStart(String contract_uuid){
        contractBusinessLogic.startContract(contract_uuid);
        
        StringBuilder builder = new StringBuilder();
        
        FacesMessage msgs = new FacesMessage();
        msgs.setSeverity(FacesMessage.SEVERITY_INFO);
        msgs.setSummary("Contract Started");
        msgs.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msgs);
    }
    
    // Need to get all persons who the current logged in user is 
    // 1. supervisor.
    // 2. Assistant
    // 3. 
    
    
}