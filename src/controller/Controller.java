package controller;

import DTOs.IntegrationDTO;
import DTOs.SaleDTO;
import integration.*;
import model.Sale;
import model.TotalRevenueObserver;
import util.FileLogger;

import java.util.ArrayList;

/**
 * This is the Applications controller class, all calls to the
 * model will pass through an object of this class.
 */
public class Controller {
    private InventoryHandler inv;
    private AccountingHandler acc;
    private RegisterHandler reg;
    private Sale sale;
    private FileLogger errorLogger;

    private ArrayList<TotalRevenueObserver> revenueObservers = new ArrayList<>();

    public void setErrorLogger(FileLogger errorLogger) {
        this.errorLogger = errorLogger;
    }

    public void setAccHandl(AccountingHandler acc) {
        this.acc = acc;
    }

    public void setInvHandl(InventoryHandler inv) {
        this.inv = inv;
    }

    public void setRegHandl(RegisterHandler reg) {
        this.reg = reg;
    }

    public void addRevenueObserver(TotalRevenueObserver revenueObserver) {
        revenueObservers.add(revenueObserver);
    }

    /**
     * Starts a new sale, method must be the first called in a sale
     */
    public void startSale(){
        sale = new Sale(inv);
        sale.addObserver(revenueObservers);
    }
    /**
     * Scans an item, forwards the request to be handled at an appropriate level.
     * @param ID The item identifier scanned
     * @return returns information about the current state of the sale
     * @throws ScanFailedException Thrown if the scan fails for any reason deemed irrelevant to the caller
     * @throws NoMatchingItemException Thrown if theres an input of an item thats not available. Presumed user fault.
     */
    public SaleDTO scanItem(String ID) throws ScanFailedException, NoMatchingItemException{
        try{
        return sale.checkIdentifier(ID);
        }
        catch(FailedConnectionException connectionException){
            errorLogger.log(connectionException.getMessage());
            throw new ScanFailedException();
        }
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


