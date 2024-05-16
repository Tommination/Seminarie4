package integration;

import DTOs.ItemDTO;
import DTOs.SaleDTO;
import model.ItemInInventory;
import model.SaleItem;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Placeholder for an actual integration class that handles inventory
 */
public class InventoryHandler {
    private final ItemInInventory[] dummyInventory = new ItemInInventory[4];

    /**
     * Sets up the integration layer with the dummy data it will operate on.
     * (4 different products)
     */
    public InventoryHandler() {
    ItemDTO banana = new ItemDTO("Banana", "Yellow", 12.5, 0.12, "1337");
    dummyInventory[0] = new ItemInInventory(banana, 15);
    ItemDTO onion = new ItemDTO("Onion", "Sustainably Farmed", 2, 0.12, "4242");
    dummyInventory[1] = new ItemInInventory(onion, 34);
    ItemDTO apple = new ItemDTO("Apple", "Green", 5, 0.08, "1234");
    dummyInventory[2] = new ItemInInventory(apple, 89);
    ItemDTO milk = new ItemDTO("Milk", "Whole, 2%", 10, 0.08, "1111");
    dummyInventory[3] = new ItemInInventory(milk, 60);
    }
    /**
     * Finds item with matching ID, in reality would check with an actual database
     * @param ID The identifier of a given item in a store
     * @throws NoMatchingItemException thrown when there is a request to get information about an item with an ID not in the database
     * @throws FailedConnectionException Fake network issue, thrown when a certain item code is scanned, also sends time of failiure.
     */
    public ItemDTO getItemDetails(String ID) throws NoMatchingItemException, FailedConnectionException {
        if (ID == "0070")
        {
            throw new FailedConnectionException(LocalTime.now());
        }
        for (int i = 0; i < dummyInventory.length; i++){
            if (dummyInventory[i].getQuantity() > 0){
                if (dummyInventory[i].getItemInfo().getID().equals(ID)){
                    return dummyInventory[i].getItemInfo();
                }
            }
        }
        throw new NoMatchingItemException(ID);
    }

    /**
     * Method that updates the inventory, would in reality interact with the database.
     * Never throws FailedConnectionException but if this interacted with a real database it could.
     * @param saleInfo information about the sale that has finished
     */
    public void updateInventory(SaleDTO saleInfo) throws FailedConnectionException{
        ArrayList<SaleItem> itemsToUpdate = saleInfo.getItemsInSale();
        for (int i = 0; i < itemsToUpdate.size(); i++){
            double soldItemQuantity = itemsToUpdate.get(i).getQuantity();
            String soldItemID = itemsToUpdate.get(i).getItem().getID();
            System.out.println("Inventory database called to decrease stock of " + soldItemID + " by " + soldItemQuantity + " units.");
        }
    }

}
