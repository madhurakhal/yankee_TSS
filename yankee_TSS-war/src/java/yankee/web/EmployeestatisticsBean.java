package yankee.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.chart.PieChartModel;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;
import yankee.utilities.UTILNumericSupport;

@ManagedBean
@ViewScoped
public class EmployeestatisticsBean implements Serializable {

    @Inject
    LoginBean loginBean;
    
    @EJB
    private TimeSheetBusinessLogic timeSheetBusinessLogic;
    
    @EJB
    private SupervisorBusinessLogic supervisorBusinessLogic;    
    
    @EJB
    private EmployeeBusinessLogic employeeBusinessLogic;

    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    private Integer totalNumberOfContracts; // count contracts associated to Employee.
    public Integer getTotalNumberOfContracts() {
        totalNumberOfContracts = contractsAssociatedToEmployee.size();
        return totalNumberOfContracts;
    }
    
    private double totalHoursDueOverAllContracts; // go through all contracts and getHoursDue and sum

    public double getTotalHoursDueOverAllContracts() {
        //timesheets.stream().filter(e -> (e.getStartDate().isBefore(currentDate))).collect(Collectors.toList());   
        totalHoursDueOverAllContracts = contractsAssociatedToEmployee.stream().mapToDouble(c->c.getHoursDue()).sum();
        return totalHoursDueOverAllContracts;
    }
    
    private Integer totalUnsignedTimesheetsAsOfToday; // previous timesheets with in progress status.
    public Integer getTotalUnsignedTimesheetsAsOfToday() {
        // Get the current date today.
        //LocalDate currentDate = LocalDate.now();
        LocalDate currentDate = LocalDate.of(2017, 10, 15);
        totalUnsignedTimesheetsAsOfToday = 0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            totalUnsignedTimesheetsAsOfToday = totalUnsignedTimesheetsAsOfToday + timesheets.stream().filter(e -> (e.getStartDate().isBefore(currentDate) && e.getStatus().equals(TimesheetStatusEnum.IN_PROGRESS))).collect(Collectors.toList()).size();              
        }
        return totalUnsignedTimesheetsAsOfToday;
    }
    
    private Integer totalTimesheets;// All timesheets with both merged weekly and monthly frequency
    public Integer getTotalTimesheets() {
        totalTimesheets = 0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            totalTimesheets = totalTimesheets + timesheets.size();
        }
        return totalTimesheets;
    }
    
    private double totalHoursWorkedOverall; // Have to go through all contracts and through all timesheets and through entries and sum hours
    public double getTotalHoursWorkedOverall() {
        totalHoursWorkedOverall = 0.0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            timesheets.forEach((t) -> {
                totalHoursWorkedOverall = totalHoursWorkedOverall + timeSheetBusinessLogic.getEntriesForTimeSheet(t.getUuid()).stream().mapToDouble(entry->entry.getHours()).sum();
            });
        }
        return totalHoursWorkedOverall;
    }
    
    
    // FOR PIE CHART Contracts
    private Integer totalPreparedContracts;
    private Integer totalStartedContracts;
    private Integer totalArchivedContracts;
    private Integer totalTerminatedContracts;
    
    // For Pie CHART Timesheets
    private Integer totalSignedTimesheets;
    private Integer totalInProgressTimesheets;
    private Integer totalArchivedTimesheets;
    
    
    // Test
    private PieChartModel pieModel1;

    public PieChartModel getPieModel1() {
        pieModel1 = new PieChartModel();
         
        pieModel1.set("Brand 1", 540);
        pieModel1.set("Brand 2", 325);
        pieModel1.set("Brand 3", 702);
        pieModel1.set("Brand 4", 421);
         
        pieModel1.setTitle("Simple Pie");
        pieModel1.setLegendPosition("w");
        return pieModel1;
    }
    
    
    
    // Main methods 
    @PostConstruct
    public void init() {
      getContractsAssociatedToEmployee();  
    }
    
    private Person employee;    
    private List<Contract> contractsAssociatedToEmployee;

    public Person getEmployee() {
        employee = loginBean.getUser();
        return employee;
    }
    
    public List<Contract> getContractsAssociatedToEmployee() {
        contractsAssociatedToEmployee = contractBusinessLogic.getContractsByPerson(loginBean.getUser().getUuid());
        return contractsAssociatedToEmployee;
    }
    
    
    
    
}
