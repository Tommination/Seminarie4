package integration;

import DTOs.ItemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class InventoryHandlerTest {
    private InventoryHandler testInstance;
    private String IDToCheck;
    private LocalTime timeOfTest;
    private String WrongID;
    ItemDTO expectedItem;

    @BeforeEach
    void setUp() {
        testInstance = new InventoryHandler();
        IDToCheck = "1111";
        WrongID = "0000";
        timeOfTest = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        expectedItem = new ItemDTO("milk", "whole, 2%", 19, 0.08, "1111");

    }

    @AfterEach
    void tearDown() {
        testInstance = null;
        expectedItem = null;
    }

    @Test
    void testIdentifierInInventory() {
        try{
            testInstance.getItemDetails(IDToCheck);}
        catch (NoMatchingItemException exc) {
            fail("Exception has been thrown when it sould not");
        }
    }
    @Test
    void testIdentifierNotInInventory() {
        try{
            testInstance.getItemDetails(WrongID);}
        catch (NoMatchingItemException exc) {
            assertTrue(exc.getMessage().contains(WrongID), "Wrong exception message, does not contain ID: " + WrongID);
        }
    }
    @Test
    void testNetworkIssue() {

        try{
            testInstance.getItemDetails("0070");}
        catch (FailedConnectionException exc) {
            assertTrue(exc.getMessage().contains(timeOfTest.toString()), "Wrong exception message, time saved does not match.");
        }
        catch (NoMatchingItemException exc){
            fail("wrong exception was thown");
        }
    }
}
