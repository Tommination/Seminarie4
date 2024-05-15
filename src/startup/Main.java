package startup;

import controller.Controller;
import integration.AccountingHandler;
import integration.InventoryHandler;
import integration.RegisterHandler;
import view.View;

/**
 * Starts the entire application
 * */
public class Main {
    /***
     * Main method used to start the applicaton
     * @param args The application doesn't take command line arguments
     */
    public static void main(String[] args) {
        Controller contr = new Controller();
        View view = new View(contr);
        InventoryHandler inv = new InventoryHandler();
        AccountingHandler acc = new AccountingHandler();
        RegisterHandler reg = new RegisterHandler();
        contr.setInvHandl(inv);
        contr.setAccHandl(acc);
        contr.setRegHandl(reg);
        view.runFakeExecution();
    }
}