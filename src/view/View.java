package view;

import DTOs.SaleDTO;
import controller.Controller;
import controller.ScanFailedException;
import integration.NoMatchingItemException;
import model.SaleItem;

/**
 * This is a placeholder for the real view. It has hardcoded executions for all system operations.
 */
public class View {
    private Controller contr;
/**
 * Creates a new instance, uses the specified controller for calls to other layers
 * @param contr The controller that is used for calls to other layers
 */
    public View(Controller contr){
        this.contr = contr;
    }

    private void printFailedScan(){
        System.out.println("Unable to scan item, please try again");
    }
    private void printwrongID(String ID){
        System.out.println("Unable to scan item with ID: " + ID + ", try entering ID manually");
    }
    /**
     * Performs a fake sale by calling all the system operations and printing returns to console
     */
    public void runFakeExecution(){
        contr.startSale();
        System.out.println("A new sale has been started.");
        scanItem("1337");
        scanItem("1337");
        scanItem("1111");
        scanItem("0000");
        scanItem("0070");

        printEndedSale(contr.endSale());
        double paymentAmount = 57;
        System.out.println("Customer pays: " + paymentAmount);
        double change = contr.enterPayment(paymentAmount);
        System.out.println("Change to give to customer: " + change);

    }
    private void scanItem(String ID){
        try{
        SaleDTO Added = contr.scanItem(ID);
        System.out.println("Add 1 item with ID: " + Added.getLatestScan().getItem().getID());
        printScan(Added);}
        catch(NoMatchingItemException itemException){
            printwrongID(itemException.getID());
        }
        catch(ScanFailedException scanException){
            printFailedScan();
        }
    }
    private void printEndedSale(SaleDTO saleToPrint){
        double cost = saleToPrint.getTotal();
        double VAT = saleToPrint.getTotalVAT();
        System.out.println("Sale has ended, cost: " + cost + " VAT: " + VAT);
    }

    private void printScan(SaleDTO saleToPrint){
        SaleItem itemToShow = saleToPrint.getLatestScan();
        String lastItemName = itemToShow.getItem().getName();
        String lastItemDescription = itemToShow.getItem().getDescription();
        Double lastItemQuantity = itemToShow.getQuantity();
        double lastItemCost = itemToShow.getItem().getPrice();
        double lastItemVAT = itemToShow.getVATcost();
        double totalCost = saleToPrint.getTotal();
        double totalVAT = saleToPrint.getTotalVAT();

        System.out.println("Item: " + lastItemName + " | " + lastItemDescription + " | Quantity: " + lastItemQuantity);
        System.out.println("Cost: " + lastItemCost + " VAT: " + lastItemVAT);
        System.out.println("Total: " + totalCost + " Total VAT: " + totalVAT);

    }

}
