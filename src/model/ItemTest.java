package model;

import DTOs.ItemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item testInstance;

    @BeforeEach
    void setUp() {
        ItemDTO testAttributes = new ItemDTO("Banan", "Gul", 12.5,0.12, "1337");
        testInstance = new Item(testAttributes);
    }

    @AfterEach
    void tearDown() {
        testInstance = null;
    }

    @Test
    void getName() {
        ItemDTO readAttributes = testInstance.getItem();
        String nameSaved = readAttributes.getName();
        String expectedOutput = "Banan";
        assertTrue(nameSaved.equals(expectedOutput), "name doesn't match");
    }
    @Test
    void getDescription() {
        ItemDTO readAttributes = testInstance.getItem();
        String descriptionSaved = readAttributes.getDescription();
        String expectedOutput = "Gul";
        assertTrue(descriptionSaved.equals(expectedOutput), "description doesn't match");
    }
    @Test
    void getPrice() {
        ItemDTO readAttributes = testInstance.getItem();
        double priceSaved = readAttributes.getPrice();
        double expectedOutput = 12.5;
        assertTrue(priceSaved == (expectedOutput), "price doesn't match");
    }
    @Test
    void getVAT() {
        ItemDTO readAttributes = testInstance.getItem();
        double VATSaved = readAttributes.getVATRate();
        double expectedOutput = 0.12;
        assertTrue(VATSaved == (expectedOutput), "VAT doesn't match");
    }
    @Test
    void getID() {
        ItemDTO readAttributes = testInstance.getItem();
        String VATSaved = readAttributes.getID();
        String expectedOutput = "1337";
        assertTrue(VATSaved.equals(expectedOutput), "ID doesn't match");
    }
}