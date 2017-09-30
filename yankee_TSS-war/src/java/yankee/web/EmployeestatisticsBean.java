package yankee.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.PieChartModel;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.EmployeeBusinessLogic;
import yankee.logic.SupervisorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.Contract;
import yankee.logic.to.Person;
import yankee.logic.to.TimeSheet;

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
        LocalDate currentDate = LocalDate.of(2017, 10, 29); // TRIAL TRIAL TRIAL
        totalUnsignedTimesheetsAsOfToday = 0;
        contractsAssociatedToEmployee.stream().map((c) -> timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid())).forEachOrdered((timesheets) -> {
            totalUnsignedTimesheetsAsOfToday = totalUnsignedTimesheetsAsOfToday + timesheets.stream().filter(e -> (e.getStartDate().isBefore(currentDate) && e.getStatus().equals(TimesheetStatusEnum.IN_PROGRESS))).collect(Collectors.toList()).size();
        });
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
    
    
    /////////////////////////////////////
    
    // Begins For Pie CHART Contracts //
    
    /////////////////////////////////////    
    private PieChartModel contractsPie;
    
    private Integer totalPreparedContracts;
    public Integer getTotalPreparedContracts() {
        totalPreparedContracts = contractsAssociatedToEmployee.stream().filter(e -> (e.getStatus().equals(ContractStatusEnum.PREPARED))).collect(Collectors.toList()).size();              
        return totalPreparedContracts;
    }
    
    private Integer totalStartedContracts;
    public Integer getTotalStartedContracts() {
        totalStartedContracts = contractsAssociatedToEmployee.stream().filter(e -> (e.getStatus().equals(ContractStatusEnum.STARTED))).collect(Collectors.toList()).size();              
        return totalStartedContracts;
    }
    
    private Integer totalArchivedContracts;
    public Integer getTotalArchivedContracts() {
        totalArchivedContracts = contractsAssociatedToEmployee.stream().filter(e -> (e.getStatus().equals(ContractStatusEnum.ARCHIVED))).collect(Collectors.toList()).size();               
        return totalArchivedContracts;
    }
    
    private Integer totalTerminatedContracts;
    public Integer getTotalTerminatedContracts() {
        totalTerminatedContracts = contractsAssociatedToEmployee.stream().filter(e -> (e.getStatus().equals(ContractStatusEnum.TERMINATED))).collect(Collectors.toList()).size();              
        return totalTerminatedContracts;
    }
    
    public PieChartModel getContractsPie() {
        contractsPie = new PieChartModel();
         
        contractsPie.set("Total Prepared Contracts", getTotalPreparedContracts());
        contractsPie.set("Total Started Contracts", getTotalStartedContracts());
        contractsPie.set("Total Archived Contracts", getTotalArchivedContracts());
        contractsPie.set("Total Terminated", getTotalTerminatedContracts());
         
        contractsPie.setTitle("Overall Contracts Information");
        contractsPie.setLegendPosition("e");
        contractsPie.setFill(false);
        contractsPie.setShowDataLabels(true);
        contractsPie.setDiameter(150);
        return contractsPie;
    }
    ///////////////////////////////////
    
    // Ends For Pie CHART Timesheets //
    
    ///////////////////////////////////
    
    
    
    
    /////////////////////////////////////
    
    // Begins For Pie CHART Timesheets //
    
    /////////////////////////////////////
    private PieChartModel timesheetsPie;
    
    private Integer totalSignedBySupervisorTimesheets;
    public Integer getTotalSignedBySupervisorTimesheets() {
        totalSignedBySupervisorTimesheets = 0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            totalSignedBySupervisorTimesheets = totalSignedBySupervisorTimesheets + timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.SIGNED_BY_SUPERVISOR))).collect(Collectors.toList()).size();              
        }
        return totalSignedBySupervisorTimesheets;
    }
    
    private Integer totalSignedByEmployeeTimesheets;
    public Integer getTotalSignedByEmployeeTimesheets() {
        totalSignedByEmployeeTimesheets = 0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            totalSignedByEmployeeTimesheets = totalSignedByEmployeeTimesheets + timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.SIGNED_BY_EMPLOYEE))).collect(Collectors.toList()).size();              
        }
        return totalSignedByEmployeeTimesheets;
    }
    
    private Integer totalInProgressTimesheets;
    public Integer getTotalInProgressTimesheets() {
        totalInProgressTimesheets = 0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            totalInProgressTimesheets = totalInProgressTimesheets + timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.IN_PROGRESS))).collect(Collectors.toList()).size();              
        }
        return totalInProgressTimesheets;
    }
    
    private Integer totalArchivedTimesheets;
    public Integer getTotalArchivedTimesheets() {
        totalArchivedTimesheets = 0;
        for(Contract c : contractsAssociatedToEmployee){
            List<TimeSheet> timesheets = timeSheetBusinessLogic.getAllTimeSheetsForContract(c.getUuid());
            totalArchivedTimesheets = totalArchivedTimesheets + timesheets.stream().filter(e -> (e.getStatus().equals(TimesheetStatusEnum.ARCHIVED))).collect(Collectors.toList()).size();              
        }
        return totalArchivedTimesheets;
    }

    public PieChartModel getTimesheetsPie() {
        timesheetsPie = new PieChartModel();
         
        timesheetsPie.set("TotalSignedBySupervisor", getTotalSignedBySupervisorTimesheets());
        timesheetsPie.set("TotalInProgress", getTotalInProgressTimesheets());
        timesheetsPie.set("TotalSignedByEmployee", getTotalSignedByEmployeeTimesheets());
        timesheetsPie.set("TotalArchived", getTotalArchivedTimesheets());
         
        timesheetsPie.setTitle("Overall Timesheets Information");
        timesheetsPie.setLegendPosition("e");
        timesheetsPie.setFill(false);
        timesheetsPie.setShowDataLabels(true);
        timesheetsPie.setDiameter(150);
        return timesheetsPie;
    }
    /////////////////////////////////////
    
    // Ends For Pie CHART Timesheets //
    
    /////////////////////////////////////
    
    
    
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
