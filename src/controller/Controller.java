package controller;

import DTOs.IntegrationDTO;
import DTOs.SaleDTO;
import integration.AccountingHandler;
import integration.InventoryHandler;
import integration.RegisterHandler;
import model.Sale;

/**
 * This is the Applications controller class, all calls to the
 * model will pass through an object of this class.
 */
public class Controller {
    private InventoryHandler inv;
    private AccountingHandler acc;
    private RegisterHandler reg;
    private Sale sale;

    public void setAccHandl(AccountingHandler acc) {
        this.acc = acc;
    }

    public void setInvHandl(InventoryHandler inv) {
        this.inv = inv;
    }

    public void setRegHandl(RegisterHandler reg) {
        this.reg = reg;
    }
    /**
     * Starts a new sale, method must be the first called in a sale
     */
    public void startSale(){
        sale = new Sale(inv);
    }
    /**
     * Scans an item, forwards the request to be handled at an appropriate level.
     */
    public SaleDTO scanItem(String ID){
        return sale.checkIdentifier(ID);
    }
    /**
     * Signals a sale has ended, returns information about the sale to perform payment
     */
    public SaleDTO endSale(){
        return sale.getSaleInfo();
    }
    public double enterPayment(double amount){
        return sale.finishSale(new IntegrationDTO(this), amount);
    }

    public InventoryHandler getInv() {
        return inv;
    }
    public RegisterHandler getReg() {
        return reg;
    }
    public AccountingHandler getAcc(){
        return acc;
    }
}


