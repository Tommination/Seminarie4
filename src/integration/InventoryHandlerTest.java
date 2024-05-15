package integration;

import DTOs.ItemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryHandlerTest {
    private InventoryHandler testInstance;
    private String IDToCheck;
    ItemDTO expectedItem;
    @BeforeEach
    void setUp() {
        testInstance = new InventoryHandler();
        IDToCheck = "1111";
        expectedItem = new ItemDTO("milk", "whole, 2%", 19, 0.08, "1111");

    }

    @AfterEach
    void tearDown() {
        testInstance = null;
    }
    @Test
    void getName() {
        ItemDTO readAttributes = testInstance.getItemDetails(IDToCheck);
        String nameSaved = readAttributes.getName();
        String expectedOutput = expectedItem.getName();
        assertTrue(nameSaved.equalsIgnoreCase(expectedOutput), "name doesn't match");
    }
    @Test
    void getDescription() {
        ItemDTO readAttributes = testInstance.getItemDetails(IDToCheck);
        String descriptionSaved = readAttributes.getDescription();
        String expectedOutput = expectedItem.getDescription();
        assertTrue(descriptionSaved.equalsIgnoreCase(expectedOutput), "description doesn't match");
    }
    @Test
    void getPrice() {
        ItemDTO readAttributes = testInstance.getItemDetails(IDToCheck);
        double priceSaved = readAttributes.getPrice();
        double expectedOutput = expectedItem.getPrice();
        assertTrue(priceSaved == (expectedOutput), "price doesn't match");
    }
    @Test
    void getVAT() {
        ItemDTO readAttributes = testInstance.getItemDetails(IDToCheck);
        double VATSaved = readAttributes.getVATRate();
        double expectedOutput = expectedItem.getVATRate();
        assertTrue(VATSaved == (expectedOutput), "VAT doesn't match");
    }
    @Test
    void getID() {
        ItemDTO readAttributes = testInstance.getItemDetails(IDToCheck);
        String VATSaved = readAttributes.getID();
        String expectedOutput = expectedItem.getID();
        assertTrue(VATSaved.equals(expectedOutput), "ID doesn't match");
    }
}