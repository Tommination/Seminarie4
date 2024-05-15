package model;

import DTOs.ItemDTO;

/***
 * Class that represents an item being bought/sold
 */
public class Item {
    private String name;
    private String description;
    private double price;
    private double VATRate;
    private String ID;
    /**
     * Creates an instance with parameters from a DTO
     * @param attributes The DTO from which an Item is created
     */
    public Item(ItemDTO attributes){
        name = attributes.getName();
        description = attributes.getDescription();
        price = attributes.getPrice();
        VATRate = attributes.getVATRate();
        ID = attributes.getID();
    }
    /**
     * Returns an ItemDTO with the same attributes as the Item
     */
    public ItemDTO getItem(){
        ItemDTO details = new ItemDTO(name, description, price, VATRate, ID);
        return details;
    }



}
