package model;

import DTOs.IntegrationDTO;
import DTOs.SaleDTO;
import integration.InventoryHandler;
import integration.NoMatchingItemException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {
    private String IDToTest;
    private Sale instanceToTest;
    private Item expectedItem;
    private SaleItem expectedLastItem;
    private double expectedTotal;
    int amountOfScans = 1;
    SaleDTO saleInfo;
    private double paidAmount = 57;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;
    @BeforeEach
    void setUp() {
        InventoryHandler inv = new InventoryHandler();
        try{
        expectedItem = new Item (inv.getItemDetails("1337"));}
        catch (NoMatchingItemException exception){}
        amountOfScans = 2;
        SaleItem item = new SaleItem(expectedItem, amountOfScans);
        IDToTest = "1337";
        instanceToTest = new Sale(inv);
        expectedLastItem = item;
        expectedTotal = (expectedItem.getItem().getPrice() * amountOfScans);
        expectedTotal += ((expectedItem.getItem().getPrice() * expectedItem.getItem().getVATRate()* amountOfScans));
        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);


    }
    private SaleDTO scanItemTimes(int amountOfScans){
        try{
        for (int i = 0; i < amountOfScans; i++){
            saleInfo = instanceToTest.checkIdentifier(IDToTest);
        }
        return saleInfo;
        }
        catch (NoMatchingItemException exception){
        }
        return saleInfo;
    }

    @AfterEach
    void tearDown() {
        instanceToTest = null;
        expectedLastItem = null;
        printoutBuffer = null;
        expectedTotal = 0;

        System.setOut(originalSysOut);
    }

    @Test
    void checkTotal() {
        saleInfo = scanItemTimes(amountOfScans);
        double foundTotal = saleInfo.getTotal();
        assertTrue((foundTotal == expectedTotal), "the total isn't what is expected");
    }
    @Test
    void checkLastItemQuantity() {
        saleInfo = scanItemTimes(amountOfScans);
        SaleItem lastItemFound = saleInfo.getLatestScan();
        assertTrue(lastItemFound.getQuantity() == expectedLastItem.getQuantity(), "The quantities don't match");
    }

    @Test
    void checkLastItem() {
        saleInfo = scanItemTimes(amountOfScans);
        SaleItem lastItemFound = saleInfo.getLatestScan();
        assertTrue(lastItemFound.getItem().getName().equals(expectedLastItem.getItem().getName()), "The names don't match");
        assertTrue(lastItemFound.getItem().getDescription().equals(expectedLastItem.getItem().getDescription()), "The descriptions don't match");
    }
    @Test
    void finishSaleTestPrints(){
        IntegrationDTO comm = new IntegrationDTO();
        scanItemTimes(amountOfScans);
        String expectedOutput[] = {"units", "change", "Banana", "cost", "VAT"};
        instanceToTest.finishSale(comm, paidAmount);
        String printout = printoutBuffer.toString();
        for(int i = 0; i<expectedOutput.length; i++)
            assertTrue(printout.contains(expectedOutput[i]));
    }
}