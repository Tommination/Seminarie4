package view;

import DTOs.SaleDTO;
import controller.Controller;
import controller.ScanFailedException;
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
    /**
     * Performs a fake sale by calling all the system operations and printing returns to console
     */
    private void printFailedScan(){
        System.out.println("Unable to scan item, please try again");
    }
    public void runFakeExecution(){
        contr.startSale();
        System.out.println("A new sale has been started.");
        try{scanItem("1337");}
        catch(ScanFailedException exception){
            printFailedScan();
        }
        try{scanItem("1337");}
        catch(ScanFailedException exception){
            printFailedScan();
        }
        try{scanItem("1111");}
        catch(ScanFailedException exception){
            printFailedScan();
        }
        try{scanItem("0000");}
        catch(ScanFailedException exception){
            printFailedScan();
        }
        printEndedSale(contr.endSale());
        double paymentAmount = 57;
        System.out.println("Customer pays: " + paymentAmount);
        double change = contr.enterPayment(paymentAmount);
        System.out.println("Change to give to customer: " + change);

    }
    private void scanItem(String ID) throws ScanFailedException {
        SaleDTO Added = contr.scanItem(ID);
        System.out.println("Add 1 item with ID: " + Added.getLatestScan().getItem().getID());
        printScan(Added);
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
