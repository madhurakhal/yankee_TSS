package yankee.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import yankee.logic.ENUM.TimesheetFrequencyEnum;

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
public class TimesheetFrequencyBean {    

    private TimesheetFrequencyEnum timesheetFrequencyEnum;

    public TimesheetFrequencyEnum[] getTimesheetFrequencyEnum() {
        return timesheetFrequencyEnum.values();
    }

    public void setTimesheetFrequencyEnum(TimesheetFrequencyEnum timesheetFrequencyEnum) {
        this.timesheetFrequencyEnum = timesheetFrequencyEnum;
    }
}
