package model;

import DTOs.ItemDTO;
import DTOs.SaleDTO;
import DTOs.IntegrationDTO;
import integration.FailedConnectionException;
import integration.InventoryHandler;
import integration.NoMatchingItemException;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A sale made by the store, a unit that is payed for by one customer with one payment
 */
public class Sale {
    private InventoryHandler inventory;
    private ArrayList<SaleItem> itemsInSale;
    private LocalTime saleTime;
    private Receipt receipt;
    private double totalCost = 0;
    private double totalVAT = 0;

    /**
     * Creates a new instance and records the time at creation.
     * @param inv the inventoryHandler that is used to interact with inventory during a sale.
     */

    public Sale(InventoryHandler inv){
        saleTime =  LocalTime.now();
        receipt = new Receipt();
        itemsInSale = new ArrayList<SaleItem>();
        inventory = inv;
    }

    /**
     * Checks for an identifier in the sale and if there isnt one passes check to integration layer
     * @param ID the identifier that is checked
     */

    public SaleDTO checkIdentifier(String ID) throws NoMatchingItemException, FailedConnectionException{
        if (IDinSale(ID) == true){
            return increaseItemInSale(ID);
        }
        else {
            return addItemFromDB(ID);
            }
        }



    private SaleDTO addItemFromDB(String ID) throws NoMatchingItemException, FailedConnectionException{
        Item itemFromDB = new Item(inventory.getItemDetails(ID));
        updateTotal(itemFromDB.getItem());
        addItemToSale(itemFromDB);
        return getSaleInfo();
    }


    private SaleDTO increaseItemInSale(String ID){
            increaseQuantity(getItemInSale(ID), 1);
            SaleItem increasedItem = getItemInSale(ID);
            updateTotal(increasedItem.getItem());
            return new SaleDTO(this, increasedItem);
        }

    
    
    private double calculatePrice(ItemDTO product){
       double total = (product.getPrice());
       return total;
    }
    
    
    private double calculateVAT(ItemDTO product){
        double VATCost = (product.getPrice() * product.getVATRate());
        return VATCost;
    }
    
    
    private void updateTotal(ItemDTO scannedItem){
        totalCost += (calculatePrice(scannedItem) + calculateVAT(scannedItem));
        totalVAT += calculateVAT(scannedItem);
    }

    private void addItemToSale(Item item){
        SaleItem itemToAdd = new SaleItem(item, 1);;
        itemsInSale.add(itemToAdd);
    }

    private SaleItem getItemInSale(String ID){
        for (int i = 0; i < itemsInSale.size(); i++){
            SaleItem itemInSale = itemsInSale.get(i);
            if (itemInSale.getItem().getID().equals(ID)) {
                return itemInSale;
            }
        }
        return  null;
    }
    
    private boolean IDinSale(String ID){
        if (getItemInSale(ID) != null){
            return true;
        }
        return false;
    }
    private void increaseQuantity(SaleItem item, double amount){ item.addQuantity(amount);
    }

    /**
     * Finishes a sale by interacting with other units and then printing a receipt
     * @param comms A DTO of integration-units so calls can happen to the right classes in integration
     * @param paidAmount How much the customer has paid in cash
     * @return
     */
    public  double finishSale(IntegrationDTO comms, double paidAmount){
        SaleDTO thisSale = getSaleInfo();
        comms.getAcc().recordSale(thisSale);
        comms.getInv().updateInventory(thisSale);
        Payment saleFinalPayment = new Payment(thisSale, paidAmount);
        printReceipt(saleFinalPayment);
        return comms.getReg().updateRegister(saleFinalPayment);
    }

    /**
     * Returns all information about the sale without altering it.
     * @return SaleDTO with how the sale is currently.
     */
    public SaleDTO getSaleInfo(){
        return new SaleDTO(this, itemsInSale.getLast());
    }

    private void printReceipt(Payment amount) {
        receipt.printReceipt(getSaleInfo(), amount);
    }

    public LocalTime getSaleTime() {
        return saleTime;
    }

    public ArrayList<SaleItem> getItemsInSale() {
        return itemsInSale;
    }

    public double getTotal() {
        return totalCost;
    }

    public double getTotalVAT() {
        return totalVAT;
    }
}
