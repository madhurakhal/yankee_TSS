/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yankee.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import yankee.entities.ContractEntity;
import yankee.entities.TimesheetEntity;
import yankee.logic.TestDataGeneratorBusinessLogic;
import yankee.logic.TimeSheetBusinessLogic;
import yankee.logic.to.TimeSheet;

/**
 *
 * @author Shriharsh Ambhore (ashriharsh@uni-koblenz.de)
 */
@Named(value = "testDataGeneratorBean")
@SessionScoped
public class TestDataGeneratorBean implements Serializable{

    /**
     * Creates a new instance of TestDataGenerator
     */
    
    
  @EJB
  TestDataGeneratorBusinessLogic testDataGenerator;
  
  
  @EJB
  TimeSheetBusinessLogic timeSheetService;
  
  public void createTestData()
  {
      if(testDataGenerator!=null)
      {
          testDataGenerator.generateTestData();
      }
  }
    
  
  public void createTimeSheet()
  {
      if(timeSheetService!=null)
      {
          ContractEntity centity=timeSheetService.getContractByUUID("e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941");
          List<TimesheetEntity> timeSheetList=timeSheetService.createTimeSheet(centity.getUuid(),centity.getStartDate(), centity.getEndDate(),centity.getFrequency().toString(), centity.getStatus().toString());
      
      }
      
  
  }
  
  public void getTimeSheet() {
        if (timeSheetService != null) {
            ContractEntity centity = timeSheetService.getContractByUUID("e88c3ba2-f2e7-49e6-a3c7-4d7bc0186941");
            timeSheetService.getAllTimeSheetsForContract(centity.getUuid());
        }
    }
    
    
    
    
}
