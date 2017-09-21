package yankee.logic.to;


public class ContractStatistics{   

    private String contractUUID;    
    private int totalHoursDue;    
    private int vacationHours;
    
    public ContractStatistics(String contractUUID) {
        this.contractUUID = contractUUID;
    }

    public int getTotalHoursDue() {
        return totalHoursDue;
    }

    public void setTotalHoursDue(int totalHoursDue) {
        this.totalHoursDue = totalHoursDue;
    }

    public int getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(int vacationHours) {
        this.vacationHours = vacationHours;
    }

    public String getContractUUID(){
        return contractUUID;
    }

    public void setContractUUID(String contractUUID) {
        this.contractUUID = contractUUID;
    }    
}
