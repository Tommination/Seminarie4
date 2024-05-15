package view;

import DTOs.SaleDTO;
import controller.Controller;
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
    public void runFakeExecution(){
        contr.startSale();
        System.out.println("A new sale has been started.");
        SaleDTO firstAdded = contr.scanItem("1337");
        System.out.println("Add 1 item with ID: " + firstAdded.getLatestScan().getItem().getID());
        printScan(firstAdded);
        SaleDTO secondAdded = contr.scanItem("1337");
        System.out.println("Add 1 item with ID: " + secondAdded.getLatestScan().getItem().getID());
        printScan(secondAdded);
        SaleDTO thirdAdded = contr.scanItem("1111");
        System.out.println("Add 1 item with ID: " + thirdAdded.getLatestScan().getItem().getID());
        printScan(thirdAdded);
        printEndedSale(contr.endSale());
        double paymentAmount = 57;
        System.out.println("Customer pays: " + paymentAmount);
        double change = contr.enterPayment(paymentAmount);
        System.out.println("Change to give to customer: " + change);

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
