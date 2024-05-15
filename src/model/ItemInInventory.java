package model;

import DTOs.ItemDTO;

public class ItemInInventory {
    private final ItemDTO itemInfo;
    private double quantity;
    /**
     * Creates an instance, has a DTO of an item and the amount of that item in stock. To simulate the database
     */
    public ItemInInventory(ItemDTO item, double inStock){
        itemInfo = item;
        quantity = inStock;
    }

    public ItemDTO getItemInfo() {
        return itemInfo;
    }

    public double getQuantity() {
        return quantity;
    }
}
