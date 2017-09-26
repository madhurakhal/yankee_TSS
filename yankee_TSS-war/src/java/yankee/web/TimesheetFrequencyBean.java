package yankee.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import yankee.logic.ENUM.TimesheetFrequencyEnum;

@RequestScoped
@Named
public class TimesheetFrequencyBean {    

    private TimesheetFrequencyEnum timesheetFrequencyEnum;

    public TimesheetFrequencyEnum[] getTimesheetFrequencyEnum() {
        return timesheetFrequencyEnum.values();
    }

    public void setTimesheetFrequencyEnum(TimesheetFrequencyEnum timesheetFrequencyEnum) {
        this.timesheetFrequencyEnum = timesheetFrequencyEnum;
    }
}
