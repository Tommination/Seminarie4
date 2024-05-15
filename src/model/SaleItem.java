package model;

import DTOs.ItemDTO;

/**
 * This class groups Items and a quantity of said item.
 */
public class SaleItem {
    private Item item;
    private double quantity;
    private double VATcost;
     /**
      * Creates an instance, calculates the cost of its VAT.
      * @param amount amount how many of the object that has been scanned.
      * @param good the Item object that has been scanned.
      */
    public SaleItem(Item good, double amount){
        item = good;
        quantity = amount;
        setVATcost();
    }
    private void setVATcost(){
        VATcost = getItem().getPrice() * getItem().getVATRate();
        VATcost = VATcost * quantity;
    }
    public double getQuantity() {
        return quantity;
    }

    /**
     * increases quantity of the item
     * @param amount how much to increase by
     */
    public void addQuantity(double amount){
        quantity += amount;
    }

    public ItemDTO getItem() {
        return item.getItem();
    }

    public double getVATcost() {
        return VATcost;
    }
}
