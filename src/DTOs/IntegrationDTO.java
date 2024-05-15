package DTOs;

import controller.Controller;
import integration.AccountingHandler;
import integration.InventoryHandler;
import integration.RegisterHandler;

/**
 * Class that groups data for integration
 */
public class IntegrationDTO {
    InventoryHandler inv;
    AccountingHandler acc;
    RegisterHandler reg;

    /**
     * Creates instance from controller object with integration units
     * @param contr controller from which integration units are stored.
     */
    public IntegrationDTO(Controller contr){
        inv = contr.getInv();
        acc = contr.getAcc();
        reg = contr.getReg();
    }

    /**
     * Creates instance without parameters from controller, useful for unit testing.
     */
    public IntegrationDTO(){
        inv = new InventoryHandler();
        acc = new AccountingHandler();
        reg = new RegisterHandler();
    }

    public InventoryHandler getInv(){
        return inv;
    }
    public AccountingHandler getAcc(){
        return acc;
    }
    public RegisterHandler getReg() {
        return reg;
    }
}
