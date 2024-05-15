package integration;
public class NoMatchingItemException extends Exception {
    private String ID;

    /**
     * Creates an instance with ta message specifying which ID was not found
      * @param ID the item identifier not found in the database
     *
     */
    public NoMatchingItemException(String ID){
        super("Unable to scan item with Identifier: " + ID + " as no such item appears in the inventory database.");
        this.ID = ID;
    }
}
