package yankee.logic.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import yankee.entities.ContractEntity;
import yankee.entities.TimesheetEntity;
import yankee.entities.TimesheetEntryEntity;
import yankee.logic.AdministrationBusinessLogic;
import yankee.logic.ContractBusinessLogic;
import yankee.logic.ENUM.ContractStatusEnum;
import yankee.logic.ENUM.GermanyStatesEnum;
import yankee.logic.ENUM.TimesheetStatusEnum;
import yankee.logic.PublicHolidaysBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.dao.ContractAccess;
import yankee.logic.dao.TimeSheetAccess;
import yankee.logic.dao.TimeSheetEntryAccess;
import yankee.logic.to.Contract;
import yankee.logic.to.TimeSheet;
import yankee.logic.to.TimeSheetEntry;
import yankee.utilities.UTILNumericSupport;


@Stateless
public class TimeSheetBusinessLogicImpl implements TimeSheetBusinessLogic {

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private TimeSheetAccess timeSheetAccess;

    @EJB
    private TimeSheetEntryAccess timeSheetEntryAccess;

    @EJB
    private PublicHolidaysBusinessLogic publicHolidaysBusinessLogic;

    @EJB
    private AdministrationBusinessLogic administrationBusinessLogic;

    @EJB
    private ContractBusinessLogic contractBusinessLogic;

    @Override
    public List<TimeSheet> createTimeSheet(final String contractUUID) {
        // Steps for creating timesheet
        // 1. Get the contract for the contractUUID.
        // 2. Check if contract started.
        // 3. Check if timesheetfrequency exists
        // 4. case of timesheetFrequency 
        // 5. Create time sheet in INPROGRESS state

        //1.
        ContractEntity ce = contractAccess.getByUuid(contractUUID);

        //2.
        if (ce.getStatus().equals(ContractStatusEnum.STARTED)) {
            if (ce.getFrequency() != null) {
                GermanyStatesEnum statesEnum;
                try {
                    statesEnum = administrationBusinessLogic.getAdminSetState().getGermanState();
                } catch (Exception e) {
                    statesEnum = GermanyStatesEnum.RHINELANDPALATINATE;
                }
                switch (ce.getFrequency()) {
                    case MONTHLY:
                        int monthsInPeriod = (int) ChronoUnit.MONTHS.between(ce.getStartDate(), ce.getEndDate());
                        for (int i = 0; i <= monthsInPeriod; i++) {
                            TimesheetEntity tsEntity = timeSheetAccess.createEntity("TimeSheet");
                            tsEntity.setStartDate(ce.getStartDate().plusMonths(i));
                            tsEntity.setEndDate(ce.getStartDate().plusMonths(i + 1).minusDays(1));
                            tsEntity.setContract(ce);

                            // BEGIN END Hours Due Calculation variable initialisation                                                        
                            int workingDaysInPeriod = 0;
                            int publicHolidaysInPeriod = 0;
                            int workingDaysPerWeek = ce.getWorkingDaysPerWeek();
                            double hoursPerweek = ce.getHoursPerWeek();
                            List<DayOfWeek> validWorkingDays = _getWorkingDays(workingDaysPerWeek);
                            // END Hours Due Calculation variable initialisation

                            // Now Create Time Sheet Entry at the timesheet period.                            
                            Period tEntryPeriod = Period.between(tsEntity.getStartDate(), tsEntity.getEndDate());
                            for (int j = 0; j <= tEntryPeriod.getDays(); j++) {
                                TimesheetEntryEntity entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry");
                                LocalDate tsEntryDate = tsEntity.getStartDate().plusDays(j);
                                entryEntity.setEntryDate(tsEntryDate);
                                entryEntity.setTimesheet(tsEntity);

                                // BEGINS Hours Due Value update for each date. 
                                DayOfWeek tsEntryDay = tsEntryDate.getDayOfWeek();
                                if (validWorkingDays.contains(tsEntryDay)) {
                                    workingDaysInPeriod += 1;

                                    // Check if that current date is public holiday for the Given state. NOTE state is to obtained from tss setup.
                                    if (publicHolidaysBusinessLogic.databaseEmpty()) {
                                        throw new IllegalStateException("Public Holidays not loaded in DATABASE. Ask Admin to load public Holidays");
                                    }
                                    if (publicHolidaysBusinessLogic.isPublicHoliday(tsEntryDate.getDayOfMonth(), tsEntryDate.getMonthValue(), tsEntryDate.getYear(), statesEnum)) {
                                        System.out.println("IS IT PUBLICCCCCCCCC HOLIDAYyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy" + tsEntryDate);
                                        publicHolidaysInPeriod += 1;
                                    }
                                }
                                // Ends Hours Due Value update for each date.
                            }

                            // Now I have "WorkingDaysInPeriod" "publicHoldaysInPeriod" "hoursPerWeek" "WorkingDaysPerWeek"
                            double hoursDue = (workingDaysInPeriod - publicHolidaysInPeriod) * (hoursPerweek / workingDaysPerWeek);
                            TimesheetEntity tse = timeSheetAccess.getByUuid(tsEntity.getUuid());
                            tse.setHoursDue(hoursDue);
                        }
                        break;

                    case WEEKLY:
                        int daysInPeriod = (int) ChronoUnit.DAYS.between(ce.getStartDate(), ce.getEndDate());
                        LocalDate tempStartDate = ce.getStartDate();
                        for (int i = 0; i <= daysInPeriod / 7; i++) {
                            TimesheetEntity tsEntity = timeSheetAccess.createEntity("TimeSheet");
                            if (i == 0) {
                                tsEntity.setStartDate(tempStartDate.plusWeeks(i));
                                int daysToAddToGetFriday = _getDaysToFriday(tempStartDate.getDayOfWeek());
                                tsEntity.setEndDate(ce.getStartDate().plusDays(daysToAddToGetFriday));
                                tempStartDate = ce.getStartDate().plusDays(daysToAddToGetFriday + 1).minusWeeks(i + 1);
                            } else {
                                tsEntity.setStartDate(tempStartDate.plusWeeks(i));

                                // The last days end date might just be few days and not week. End date = contract end date.
                                if (i == ((daysInPeriod / 7))) {
                                    tsEntity.setEndDate(ce.getEndDate());
                                } else {
                                    tsEntity.setEndDate(tempStartDate.plusWeeks(i + 1).minusDays(1));
                                }
                            }
                            tsEntity.setContract(ce);

                            // BEGIN END Hours Due Calculation variable initialisation                                                        
                            int workingDaysInPeriod = 0;
                            int publicHolidaysInPeriod = 0;
                            int workingDaysPerWeek = ce.getWorkingDaysPerWeek();
                            double hoursPerweek = ce.getHoursPerWeek();
                            List<DayOfWeek> validWorkingDays = _getWorkingDays(workingDaysPerWeek);
                            // END Hours Due Calculation variable initialisation

                            // Now Create Time Sheet Entry at the timesheet period.                            
                            Period tEntryPeriod = Period.between(tsEntity.getStartDate(), tsEntity.getEndDate());
                            for (int j = 0; j <= tEntryPeriod.getDays(); j++) {
                                TimesheetEntryEntity entryEntity = timeSheetEntryAccess.createEntity("TimeSheetEntry");
                                LocalDate tsEntryDate = tsEntity.getStartDate().plusDays(j);
                                entryEntity.setEntryDate(tsEntryDate);
                                entryEntity.setTimesheet(tsEntity);
                                entryEntity.setFilled(false);

                                // BEGINS Hours Due Value update for each date. 
                                DayOfWeek tsEntryDay = tsEntryDate.getDayOfWeek();
                                if (validWorkingDays.contains(tsEntryDay)) {
                                    workingDaysInPeriod += 1;

                                    // Check if that current date is public holiday for the Given state. NOTE state is to obtained from tss setup.
                                    if (publicHolidaysBusinessLogic.databaseEmpty()) {
                                        throw new IllegalStateException("Public Holidays not loaded in DATABASE. Ask Admin to load public Holidays");
                                    }
                                    if (publicHolidaysBusinessLogic.isPublicHoliday(tsEntryDate.getDayOfMonth(), tsEntryDate.getMonthValue(), tsEntryDate.getYear(), statesEnum)) {

                                        publicHolidaysInPeriod += 1;
                                    }
                                }
                                // Ends Hours Due Value update for each date.
                            }

                            // Now I have "WorkingDaysInPeriod" "publicHoldaysInPeriod" "hoursPerWeek" "WorkingDaysPerWeek"
                            double hoursDue = (workingDaysInPeriod - publicHolidaysInPeriod) * (hoursPerweek / workingDaysPerWeek);
                            TimesheetEntity tse = timeSheetAccess.getByUuid(tsEntity.getUuid());
                            tse.setHoursDue(UTILNumericSupport.round(hoursDue, 2));
                        }
                        break;

                    default:
                        break;
                }
            }
        }
        return null;
    }
    // Helper method to evaluate days required to get to Friday
    private int _getDaysToFriday(DayOfWeek weekday) {
        switch (weekday) {
            case FRIDAY:
                return 0;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 5;
            case MONDAY:
                return 4;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 1;
            default:
                return 0;

        }
    }

    // Helper method. Takes working days. for example 4. Working days becomes Monday Tuesday Wednesday Thursday
    private List<DayOfWeek> _getWorkingDays(int workingDaysPerWeek) {
        List<DayOfWeek> workingDaysEnum = new ArrayList<>();
        if (workingDaysPerWeek <= 7) {

            for (int workday = 0; workday < workingDaysPerWeek; workday++) {
                switch (workday) {
                    case 0:
                        workingDaysEnum.add(DayOfWeek.MONDAY);
                        break;
                    case 1:
                        workingDaysEnum.add(DayOfWeek.TUESDAY);
                        break;
                    case 2:
                        workingDaysEnum.add(DayOfWeek.WEDNESDAY);
                        break;
                    case 3:
                        workingDaysEnum.add(DayOfWeek.THURSDAY);
                        break;
                    case 4:
                        workingDaysEnum.add(DayOfWeek.FRIDAY);
                        break;
                    case 5:
                        workingDaysEnum.add(DayOfWeek.SATURDAY);
                        break;
                    case 6:
                        workingDaysEnum.add(DayOfWeek.SUNDAY);
                        break;
                    default:
                        break;

                }
            }
        }
        return workingDaysEnum;
    }

    /*
     * // check if the timesheet is in in_progress state and contract is
     * started then only allow to perform action

     *
     * @param TimeSheetEntry object containing the values to be saved/updated
     * @return String containing message.
     */
    @Override
    public String addUpdateTimeSheetEntry(final TimeSheetEntry obj) {
        String messageString = null;
        try {
            String uuid = obj.getUuid();
            if (uuid == null) {
                throw new IllegalStateException("parameter cannot be null!!");
            }
            TimesheetEntryEntity tsEntry = timeSheetEntryAccess.findByPrimaryKey(timeSheetEntryAccess.getByUuid(uuid).getId());
            final String timeSheetStatus = tsEntry.getTimesheet().getStatus().toString();
            final ContractEntity contract = contractAccess.findByPrimaryKey(tsEntry.getTimesheet().getContract().getId());

            if (timeSheetStatus.equalsIgnoreCase("IN_PROGRESS") && contract.getStatus().toString().equalsIgnoreCase("STARTED")) {

                if (obj.getEndDateTime() != null && obj.getStartDateTime() != null) {
                    long hoursCalc = obj.getEndDateTime().getTime() - obj.getStartDateTime().getTime();
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(hoursCalc);
                    long hours = TimeUnit.MILLISECONDS.toHours(hoursCalc);
                    double hours_fin = (double) (hours + ((minutes - 60 * hours) / 60.0));
                    tsEntry.setHours(UTILNumericSupport.round(hours_fin, 2));
                    tsEntry.setFilled(true);
                    tsEntry.setDescription(obj.getDescription());
                    tsEntry.setEndTime(new java.sql.Time(obj.getEndDateTime().getTime()));
                    tsEntry.setStartTime(new java.sql.Time(obj.getStartDateTime().getTime()));
                } else {
                    tsEntry.setDescription(null);
                    tsEntry.setEndTime(null);
                    tsEntry.setStartTime(null);
                    tsEntry.setHours(0.0);
                    tsEntry.setFilled(false);
                }
                messageString = "Saved!!"; // need to do internationalization;

            } else {
                throw new IllegalStateException("Please check contract status or timesheet status");
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return messageString;
    }

    @Override
    public String deleteTimeSheet(final String contractUuid, Boolean isTerminateContract) {
        try {
            if (contractUuid == null) {
                throw new IllegalStateException("Uuid cannot be null");
            }
            // get all timesheets for a contract 

            List<TimesheetEntity> timeSheets = timeSheetAccess.getTimeSheetsForContractByID(contractAccess.getByUuid(contractUuid).getId());

            if (isTerminateContract) {
                for (final TimesheetEntity e : timeSheets) {
                    if (TimesheetStatusEnum.IN_PROGRESS.toString().equalsIgnoreCase(e.getStatus().toString())) {
                        // Delete entries for the timesheets.
                        // Also set the Contract as null
                        e.setContract(null);

                        System.out.println("TIME SHEEEEEEEEEEET ID" + e.getUuid());
                        for (TimesheetEntryEntity tee : timeSheetEntryAccess.getTimeSheetEntriesForTimeSheet(e.getUuid())) {
                            tee.setTimesheet(null);
                            System.out.println("HERE FOR TIE  SHEEEET ENTRY" + tee.getName());
                            timeSheetEntryAccess.deleteEntity(tee);
                        }
                        timeSheetAccess.deleteEntity(e);
                        //removeList.add(e);
                    }
                }
            }

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return "Success";
    }

    @Override
    public ContractEntity getContractByTimesheetUUID(String uuid) {
        return timeSheetAccess.getByUuid(uuid).getContract();
        //return contractAccess.getContractEntity(uuid); // hardcoding for testing purpose
    }

    /**
     *
     * @param contractUUID uuid of contract
     * @return List<TimeSheet> objects containing the timesheet details
     */
    @Override
    public List<TimeSheet> getAllTimeSheetsForContract(String contractUUID) {
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
            if (contractUUID == null) {
                throw new IllegalStateException("****contract cannot be null****");
            }
            timeSheetList = timeSheetAccess.getTimeSheetsForContractByID(contractAccess.getByUuid(contractUUID).getId());

            tsObjList = new ArrayList<>(timeSheetList.size());
            TimeSheet ts;
            for (final TimesheetEntity entity : timeSheetList) {
                ts = new TimeSheet(entity.getUuid(), entity.getName());
                ts.setEndDate(entity.getEndDate());
                ts.setStartDate(entity.getStartDate());
                ts.setStatus(entity.getStatus());
                ts.setHoursDue(entity.getHoursDue());
                ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());
                tsObjList.add(ts);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return tsObjList;
    }

    /**
     *
     * @param timeSheetUuid uuid of the timeSheet of contract
     * @return List<TimeSheetEntry> containing the TimeSheet entries for a given
     * timeSheet.
     */
    @Override
    public List<TimeSheetEntry> getEntriesForTimeSheet(final String timeSheetUuid) {
        List<TimeSheetEntry> entryList = null;
        Boolean isHoliday;
        try {
            if (timeSheetUuid == null) {
                throw new IllegalStateException("Please select a timesheet");
            }
            entryList = new ArrayList<>();
            final List<TimesheetEntryEntity> objList = timeSheetEntryAccess.getTimeSheetEntriesForTimeSheet(timeSheetUuid);
            TimeSheetEntry entryObj;

            for (TimesheetEntryEntity e : objList) {
                entryObj = new TimeSheetEntry(e.getUuid(), e.getName());
                entryObj.setEntryDate(e.getEntryDate());
                entryObj.setDateString(e.getEntryDate().toString());
                entryObj.setDescription(e.getDescription());
                entryObj.setEndDateTime(e.getEndTime());
                entryObj.setHours(e.getHours() == null ? 0.0 : UTILNumericSupport.round(e.getHours(), 2));
                entryObj.setStartDateTime(e.getStartTime());
                entryObj.setIsFilled(e.isFilled());

                boolean flag = publicHolidaysBusinessLogic.isPublicHoliday(e.getEntryDate().getDayOfMonth(), e.getEntryDate().getMonthValue(), e.getEntryDate().getYear(), administrationBusinessLogic.getAdminSetState().getGermanState());
                if (flag || e.getEntryDate().getDayOfWeek().toString().equalsIgnoreCase("sunday") || e.getEntryDate().getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                    isHoliday = Boolean.TRUE;
                } else {
                    isHoliday = Boolean.FALSE;
                }
                entryObj.setIsHoliday(isHoliday);
                entryList.add(entryObj);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return entryList;
    }

    /**
     * // CALL this method when and emp/supervisor wants to sign the timesheet
     *
     * @param uuid of the timesheet
     * @param submittedByEmp set true if submitted by emp . Set false if
     * submitted by supervisor. Set null if not called by emp or supervisor.
     *
     * @return String message.
     */
    @Override
    public String submitTimeSheet(final String uuid, final Boolean submittedByEmp) {

        String messageString = null;
        try {
            if (uuid == null) {
                throw new IllegalStateException("uuid of timesheet cannot ne null!!");
            }

            TimesheetEntity tsEntity = timeSheetAccess.findByPrimaryKey(timeSheetAccess.getByUuid(uuid).getId());

            // signed by employee should come here
            if (submittedByEmp) {

                tsEntity.setSignedByEmployee(LocalDate.now());
                tsEntity.setStatus(TimesheetStatusEnum.SIGNED_BY_EMPLOYEE);
            } else {
                // signed by supervisor should come here
                tsEntity.setSignedBySupervisor(LocalDate.now());
                tsEntity.setStatus(TimesheetStatusEnum.SIGNED_BY_SUPERVISOR);
            }
            messageString = "TimeSheet Submitted Successfully!";
            return messageString;

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return messageString;
    }

    @Override
    public String deleteTimeSheetEntry(String uuid) {
        String messageString = null;
        try {
            if (uuid == null) {
                throw new IllegalStateException("please a timeSheet entry");
            }
            TimesheetEntryEntity entry = timeSheetEntryAccess.findByPrimaryKey(timeSheetEntryAccess.getByUuid(uuid).getId());
            entry.setDescription(null);
            entry.setStartTime(null);
            entry.setEndTime(null);

            messageString = "Entry removed";

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return messageString;
    }

    // Pradip Code
    @Override
    public List<TimeSheet> getAllTimeSheetsByGivenDate(LocalDate givenDate) {
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
            timeSheetList = timeSheetAccess.getAllTimeSheetsByGivenDate(givenDate);
            if (timeSheetList == null) {
                return null;
            }
            tsObjList = new ArrayList<>();
            TimeSheet ts;
            for (final TimesheetEntity entity : timeSheetList) {
                if (!getContractByTimesheetUUID(entity.getUuid()).getStatus().equals(ContractStatusEnum.TERMINATED)) {
                    ts = new TimeSheet(entity.getUuid(), entity.getName());
                    ts.setEndDate(entity.getEndDate());
                    ts.setStartDate(entity.getStartDate());
                    ts.setStatus(entity.getStatus());
                    ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());

                    ContractEntity contract = entity.getContract();
                    // to get the contract id
                    Contract c = new Contract(contract.getUuid(), contract.getName());
                    // to do fill up contract transfer object
                    ts.setContract(c);

                    tsObjList.add(ts);
                }
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return tsObjList;
    }

    // Called only by Delete Archive service in 2 years interval
    @Override
    public void deleteOldTimeSheetSignedBySupervisor(LocalDate givenDate) {
        Set<String> setContractUUIDTimeSheetDeleted = new HashSet<>();
        List<TimesheetEntity> listTE = timeSheetAccess.getOldTimeSheetSignedBySupervisor(givenDate);
        // Get all time sheets with less then given date
        listTE.stream().map((timesheetEntity) -> {
            setContractUUIDTimeSheetDeleted.add(timesheetEntity.getContract().getUuid());
            return timesheetEntity;
        }).map((timesheetEntity) -> {
            // delete timesheet entry entity
            List<TimesheetEntryEntity> ltee = timeSheetEntryAccess.getTimeSheetEntriesForTimeSheet(timesheetEntity.getUuid());
            ltee.forEach(tee -> {
                tee.setTimesheet(null);
                timeSheetEntryAccess.deleteEntity(tee);
            });
            // Now delete timesheet
            timesheetEntity.setContract(null);
            return timesheetEntity;
        }).forEachOrdered((timesheetEntity) -> {
            timeSheetAccess.deleteEntity(timesheetEntity);
        });
        setContractUUIDTimeSheetDeleted.stream().filter((contractUUID) -> (timeSheetAccess.getTimeSheetsForContract(contractUUID).isEmpty())).forEachOrdered((contractUUID) -> {
            contractBusinessLogic.deleteContract(contractUUID);
        });

    }

    @Override
    public List<TimeSheet> getAllTimeSheetsSignedBySupervisor(LocalDate givenDate) {
        List<TimesheetEntity> timeSheetList;
        List<TimeSheet> tsObjList = null;
        try {
            timeSheetList = timeSheetAccess.getAllTimeSheetsSignedBySupervisor(givenDate);
            tsObjList = new ArrayList<>();
            TimeSheet ts;
            for (final TimesheetEntity entity : timeSheetList) {
                ts = new TimeSheet(entity.getUuid(), entity.getName());
                ts.setEndDate(entity.getEndDate());

                ts.setStartDate(entity.getStartDate());
                ts.setStatus(entity.getStatus());
                ts.setHoursDue(entity.getHoursDue());
                ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());

                ContractEntity contract = entity.getContract();
                // to get the contract id
                Contract c = new Contract(contract.getUuid(), contract.getName());
                // to do fill up contract transfer object
                ts.setContract(c);

                tsObjList.add(ts);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        return tsObjList;
    }

    @Override
    public TimeSheet getByUUID(String timeSheetUUID) {
        TimesheetEntity entity = timeSheetAccess.getByUuid(timeSheetUUID);
        TimeSheet ts = new TimeSheet(entity.getUuid(), entity.getName());
        ts.setEndDate(entity.getEndDate());
        ts.setStartDate(entity.getStartDate());
        ts.setStatus(entity.getStatus());
        ts.setHoursDue(entity.getHoursDue());
        ts.setDisplayString(entity.getStartDate().toString() + " - " + entity.getEndDate().toString());

        ContractEntity contract = entity.getContract();
        // to get the contract id
        Contract c = new Contract(contract.getUuid(), contract.getName());
        // to do fill up contract transfer object
        ts.setContract(c);
        return ts;
    }

    @Override
    public void archiveTimeSheet(String timeSheetUUID) {
        TimesheetEntity te = timeSheetAccess.getByUuid(timeSheetUUID);
        te.setStatus(TimesheetStatusEnum.ARCHIVED);
        contractBusinessLogic.calledForContractArchive(timeSheetAccess.getByUuid(timeSheetUUID).getContract().getUuid());

    }

    @Override
    public void revokeSignature(String timeSheetUUID) {
        TimesheetEntity te = timeSheetAccess.getByUuid(timeSheetUUID);
        te.setStatus(TimesheetStatusEnum.IN_PROGRESS);
        te.setSignedByEmployee(null);
    }

}
