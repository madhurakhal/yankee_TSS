package jee17.logic.to;

public class Employee extends Role {

    private static final long serialVersionUID = 2813983198416172587L;

    private Contract contract;

    public Employee(String uuid, String name) {
        super(uuid, name);
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
