package yankee.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import yankee.logic.AssistantBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SecretaryBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.Supervisor;
import yankee.logic.to.TimeSheet;

@RequestScoped
@Named
public class EmployeeContractsBean {

    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;

    @Inject
    private LoginBean loginBean;

    @PostConstruct
    public void init() {
        getContractsAssociatedToEmployee();
    }
    private List<Contract> contractsAssociatedToEmployee;
    private List<Contract> contractsToSignAssociatedToEmployee = new ArrayList<>();
    private List<Contract> contractsPreparedAssociatedToEmployee = new ArrayList<>();
    private List<Contract> contractsStartedAssociatedToEmployee = new ArrayList<>();

    public List<Contract> getContractsToSignAssociatedToEmployee() {
        LocalDate today = LocalDate.now();
        for (Contract c : contractsAssociatedToEmployee) {
            List<TimeSheet> lt = timeSheetBusinessLogic.getAllTimeSheetsByGivenDate(today);
            if (!lt.stream().filter(p -> p.getStatus() == TimesheetStatusEnum.IN_PROGRESS).collect(Collectors.toList()).isEmpty()) {
                contractsToSignAssociatedToEmployee.add(c);
            }            
        }
        return contractsToSignAssociatedToEmployee;
    }

    public void setContractsToSignAssociatedToEmployee(List<Contract> contractsToSignAssociatedToEmployee) {
        this.contractsToSignAssociatedToEmployee = contractsToSignAssociatedToEmployee;
    }

    public List<Contract> getContractsPreparedAssociatedToEmployee() {
        if (contractsAssociatedToEmployee != null) {
            if (contractsPreparedAssociatedToEmployee.isEmpty()) {
                for (Contract c : contractsAssociatedToEmployee) {
                    if (c.getStatus() == ContractStatusEnum.PREPARED) {
                        contractsPreparedAssociatedToEmployee.add(c);
                    }
                }
            }
        }
        return contractsPreparedAssociatedToEmployee;
    }

    public void setContractsPreparedAssociatedToEmployee(List<Contract> contractsPreparedAssociatedToEmployee) {
        this.contractsPreparedAssociatedToEmployee = contractsPreparedAssociatedToEmployee;
    }

    public List<Contract> getContractsStartedAssociatedToEmployee() {
        if (contractsAssociatedToEmployee != null) {
            if (contractsStartedAssociatedToEmployee.isEmpty()) {
                for (Contract c : contractsAssociatedToEmployee) {
                    if (c.getStatus() == ContractStatusEnum.STARTED) {
                        contractsStartedAssociatedToEmployee.add(c);
                    }
                }
            }
        }
        return contractsStartedAssociatedToEmployee;
    }

    public void setContractsStartedAssociatedToEmployee(List<Contract> contractsStartedAssociatedToEmployee) {
        this.contractsStartedAssociatedToEmployee = contractsStartedAssociatedToEmployee;
    }

    public List<Contract> getContractsAssociatedToEmployee() {
        contractsAssociatedToEmployee = contractBusinessLogic.getContractsByPerson(loginBean.getUser().getUuid());
        return contractsAssociatedToEmployee;
    }

    public void setContractsAssociatedToEmployee(List<Contract> contractsAssociatedToEmployee) {
        this.contractsAssociatedToEmployee = contractsAssociatedToEmployee;
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

    public void onRowView() throws IOException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String contract_uuid = params.get("contract_uuid");
        contractBusinessLogic.updateContractStatistics(contract_uuid);
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
}
