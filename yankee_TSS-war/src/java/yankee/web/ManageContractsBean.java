package yankee.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.entities.ContractEntity;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.PersonBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Secretary;
import yankee.logic.to.Supervisor;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;

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

    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;

    @EJB
    private PersonBusinessLogic personBusinessLogic;

    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;

    public boolean allTimeSheetsInProgressSignedBySuper(String contractUUID) {
        List<TimeSheet> lt = timeSheetBusinessLogic.getAllTimeSheetsForContract(contractUUID);
        // The following says if no entires obtained for archived or signed by employee
        return lt.stream().filter(p -> (p.getStatus() == TimesheetStatusEnum.ARCHIVED || p.getStatus() == TimesheetStatusEnum.SIGNED_BY_EMPLOYEE)).collect(Collectors.toList()).isEmpty();
    }

    public boolean contractWithTimesheetHasEntries(String contractUUID) {
        // In progress timesheets with entries
        List<TimeSheet> lt = timeSheetBusinessLogic.getAllTimeSheetsForContract(contractUUID);
        for (TimeSheet t : lt.stream().filter(p -> p.getStatus() == TimesheetStatusEnum.IN_PROGRESS).collect(Collectors.toList())) {
            List<TimeSheetEntry> ltse = timeSheetBusinessLogic.getEntriesForTimeSheet(t.getUuid());
            if (!ltse.stream().filter(e -> e.isIsFilled()).collect(Collectors.toList()).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Inject
    private LoginBean loginBean;

    private List<Person> personsAssociatedToContractSupervisor = new ArrayList<>();
    private List<Person> personsAssociatedToContractAssistant = new ArrayList<>();
    private List<Person> personsAssociatedToContractSecretary = new ArrayList<>();
    private final List<Person> terminatedContracts = new ArrayList<>();

    public List<Person> getTerminatedContracts() {
        if (terminatedContracts != null) {
            List<Contract> termContracts = contractBusinessLogic.getContractList().stream().filter(c -> (c.getStatus().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList());
            for (Contract c : termContracts) {
                Person p = personBusinessLogic.getPersonByName(c.getEmployee().getPerson().getName());
                p.setContractStatusForRole(c.getStatus());
                p.setContractUUIDForRole(c.getUuid());
                if (terminatedContracts.size() != termContracts.size()) {
                    terminatedContracts.add(p);
                }
            }
        }
        return terminatedContracts;
    }

    //@PostConstruct
    public void init() {
        List<Person> lpsupr = supervisorBusinessLogic.getPersonsUnderSupervisor(loginBean.getUser().getUuid());
        //terminatedContracts.addAll(lpsupr.stream().filter(p -> (p.getContractStatusForRole().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList()));
        personsAssociatedToContractSupervisor = lpsupr.stream().filter(p -> (!p.getContractStatusForRole().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList());

        List<Person> lpassi = assistantBusinessLogic.getPersonsUnderAssistant(loginBean.getUser().getUuid());
        personsAssociatedToContractAssistant = lpassi.stream().filter(p -> (!p.getContractStatusForRole().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList());
        //terminatedContracts.addAll(lpassi.stream().filter(p -> (p.getContractStatusForRole().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList()));

        List<Person> lpsec = secretaryBusinessLogic.getPersonsUnderSecretary(loginBean.getUser().getUuid());
        personsAssociatedToContractSecretary = lpsec.stream().filter(p -> (!p.getContractStatusForRole().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList());
        //terminatedContracts.addAll(lpsec.stream().filter(p -> (p.getContractStatusForRole().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList()));

    }

    public List<Person> getPersonsAssociatedToContractAssistant() {
        return personsAssociatedToContractAssistant;
    }

    public void setPersonsAssociatedToContractAssistant(List<Person> personsAssociatedToContractAssistant) {
        this.personsAssociatedToContractAssistant = personsAssociatedToContractAssistant;
    }

    public List<Person> getPersonsAssociatedToContractSecretary() {
        return personsAssociatedToContractSecretary;
    }

    public void setPersonsAssociatedToContractSecretary(List<Person> personsAssociatedToContractSecretary) {
        this.personsAssociatedToContractSecretary = personsAssociatedToContractSecretary;
    }

    public List<Person> getPersonsAssociatedToContractSupervisor() {
        // Because the postconstruct was causing problem for page refresh.
        init();
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

    public void onRowTerminate(String contract_uuid) {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Contract Terminated");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // Terminate task
        contractBusinessLogic.terminateContract(contract_uuid);
    }

    public void onRowView(String contract_uuid) throws IOException {
        //contractBusinessLogic.updateContractStatistics(contract_uuid);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/logged_in/contractdetails.xhtml?id=" + contract_uuid);
    }

    public void onRowPrint(String contract_uuid) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/staff_logged_in/printpreviewcontract.xhtml?id=" + contract_uuid);
    }

    public void onRowStart(String contract_uuid) {
        contractBusinessLogic.startContract(contract_uuid);

        StringBuilder builder = new StringBuilder();

        FacesMessage msgs = new FacesMessage();
        msgs.setSeverity(FacesMessage.SEVERITY_INFO);
        msgs.setSummary("Contract Started");
        msgs.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msgs);
    }

    // HELPER METHODS
    public Person supervisorPersonForContractUUID(String contractUUID) {
        return contractBusinessLogic.getContractByUUID(contractUUID).getSupervisor().getPerson();
    }

//    public boolean isSecretary(String contractUUID) {
//        List<Secretary> ls = secretaryBusinessLogic.getSecretariesByContract(contractUUID);
//        // For each secretary if one of the secretary matches the person logged in
//        return !ls.stream().filter(e -> e.getPerson().getUuid().equals(loggedinUser.getUuid())).collect(Collectors.toList()).isEmpty();
//    }
}
