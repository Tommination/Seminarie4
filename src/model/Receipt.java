package model;

import DTOs.ItemDTO;
import DTOs.SaleDTO;

import java.util.ArrayList;

/**
 * Contains information from a Sale
 */
public class Receipt {
    String itemName;
    String ID;
    double itemCost;
    double VATcostItem;
    double totalCost;
    double totalVAT;
    double change;

    /**
     * Prints a receipt to the console
     * @param saleInfo a SaleDTO for the sale to be printed
     * @param payment  Payment that shows the payment to be printed
     */
    public void printReceipt(SaleDTO saleInfo, Payment payment){
        System.out.println("---------- RECEIPT ----------");
        System.out.println("Time of Sale: " + saleInfo.getSaleTime());
        ArrayList<SaleItem> soldItems = saleInfo.getItemsInSale();
        for (int i = 0; i < soldItems.size(); i++){
            itemName = soldItems.get(i).getItem().getName();
            ID = soldItems.get(i).getItem().getID();
            itemCost = soldItems.get(i).getItem().getPrice();
            double quantity = soldItems.get(i).getQuantity();
            VATcostItem = calculateVAT(soldItems.get(i).getItem()) * quantity;
            System.out.println(itemName  + " ID " + ID + "      " + quantity + " x " + itemCost + "      " + itemCost*quantity + "      VAT: " + VATcostItem);
        }
        totalCost = saleInfo.getTotal();
        totalVAT = saleInfo.getTotalVAT();
        change = payment.getChange();
        System.out.println("Total cost: " + totalCost + " | Total VAT: " + totalVAT + " | Change due: " + change );
        System.out.println("---------- END ----------");
    }
    private double calculateVAT(ItemDTO item){
        return item.getPrice()*item.getVATRate();
    }
}
