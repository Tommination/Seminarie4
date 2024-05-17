package startup;

import controller.Controller;
import util.FileLogger;
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
        InventoryHandler inv = new InventoryHandler();
        AccountingHandler acc = new AccountingHandler();
        RegisterHandler reg = new RegisterHandler();
        contr.setInvHandl(inv);
        contr.setAccHandl(acc);
        contr.setRegHandl(reg);
        contr.setErrorLogger(new FileLogger("PointOfSaleErrorLog.txt"));
        View view = new View(contr);
        view.runFakeExecution();
    }
}