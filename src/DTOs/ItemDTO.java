package DTOs;

/**
 * Class stores data about an Item
 */
public class ItemDTO {
    private final String name;
    private final String description;
    private final double price;
    private final double VATRate;
    private final String ID;
    /**
     * Creates a DTO for the class Item, with the attributes of Item
     */
    public ItemDTO(String name, String description, double price, double VATRate, String ID){
        this.name = name;
        this.description = description;
        this.price = price;
        this.VATRate = VATRate;
        this.ID = ID;
    }
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getVATRate() {
        return VATRate;
    }

    public String getID() {
        return ID;
    }
}
