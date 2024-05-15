package integration;

import DTOs.ItemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryHandlerTest {
    private InventoryHandler testInstance;
    private String IDToCheck;
    private String WrongID;
    ItemDTO expectedItem;

    @BeforeEach
    void setUp() {
        testInstance = new InventoryHandler();
        IDToCheck = "1111";
        WrongID = "0000";
        expectedItem = new ItemDTO("milk", "whole, 2%", 19, 0.08, "1111");

    }

    @AfterEach
    void tearDown() {
        testInstance = null;
        expectedItem = null;
    }

    @Test
    void testIdentifierNotInInventory() {
        try{
            testInstance.getItemDetails(WrongID);}
        catch (NoMatchingItemException exc) {
            assertTrue( exc.getMessage().contains(WrongID), "Wrong exception message, does not contain ID: " + WrongID);
        }
    }
    @Test
    void testIdentifierInInventory() {
        try{
            testInstance.getItemDetails(IDToCheck);}
        catch (NoMatchingItemException exc) {
            fail("Exception has been thrown when it sould not");
        }

    }

}
