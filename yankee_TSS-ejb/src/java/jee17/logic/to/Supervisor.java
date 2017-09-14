package jee17.logic.to;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import jee17.entities.ContractEntity;
import jee17.entities.RoleEntity;

public class Supervisor extends Role {

    private static final long serialVersionUID = 2813983198416172587L;

    private Contract contract;

    public Supervisor(String uuid, String name) {
        super(uuid, name);
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
