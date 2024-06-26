package controller;

import DTOs.SaleDTO;
import integration.AccountingHandler;
import integration.InventoryHandler;
import integration.NoMatchingItemException;
import integration.RegisterHandler;
import model.Item;
import model.Sale;
import model.SaleItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller testController;
    private String IDToTest;
    private Item expectedItem;
    private SaleItem expectedLastItem;
    private double expectedTotal;
    int amountOfScans = 1;
    SaleDTO saleInfo;
    double expectedVAT;
    double amountPaid;

    @BeforeEach
    void setUp() {
        testController = new Controller();
        testController.setInvHandl(new InventoryHandler());
        testController.setRegHandl(new RegisterHandler());
        testController.setAccHandl(new AccountingHandler());
        InventoryHandler inv = new InventoryHandler();
        IDToTest = "1337";
        try{
        expectedItem = new Item(inv.getItemDetails(IDToTest));}
        catch( NoMatchingItemException exception){}
        amountOfScans = 2;
        SaleItem item = new SaleItem(expectedItem, amountOfScans);
        expectedLastItem = item;
        expectedTotal = (expectedItem.getItem().getPrice() * amountOfScans);
        expectedVAT = ((expectedItem.getItem().getPrice() * expectedItem.getItem().getVATRate() * amountOfScans));
        expectedTotal += expectedVAT;
        double amountPaid = 57;

    }

    private SaleDTO scanItemTimes(int amountOfScans) throws ScanFailedException, NoMatchingItemException{
        testController.startSale();
        for (int i = 0; i < amountOfScans; i++) {
            saleInfo = testController.scanItem(IDToTest);
        }
        return saleInfo;
    }
    private SaleDTO performSale(){
        try{
        saleInfo = scanItemTimes(amountOfScans);
        return saleInfo;}
        catch (ScanFailedException exception){
            return null;
        }
        catch (NoMatchingItemException exception){
            return null;
        }
    }

    @AfterEach
    void tearDown() {
        expectedLastItem = null;
        testController = null;
        expectedTotal = 0;
    }

    @Test
    void checkTotal() {
        saleInfo = performSale();
        double foundTotal = saleInfo.getTotal();
        assertTrue((foundTotal == expectedTotal), "the total isn't what is expected");
    }

    @Test
    void checkLastItemQuantity() {
        saleInfo = performSale();
        SaleItem lastItemFound = saleInfo.getLatestScan();
        assertTrue(lastItemFound.getQuantity() == expectedLastItem.getQuantity(), "The quantities don't match");
    }

    @Test
    void checkLastItem() {
        saleInfo = performSale();
        SaleItem lastItemFound = saleInfo.getLatestScan();
        assertTrue(lastItemFound.getItem().getName().equals(expectedLastItem.getItem().getName()), "The names don't match");
        assertTrue(lastItemFound.getItem().getDescription().equals(expectedLastItem.getItem().getDescription()), "The descriptions don't match");
    }

    @Test
    void testEndSale(){
        performSale();
        SaleDTO endedSaleInfo = testController.endSale();
        checkTotal();
        assertTrue(expectedVAT == endedSaleInfo.getTotalVAT(), "the VAT isn't what was expected");
    }

    @Test
    void testPayment(){
        performSale();
        testController.endSale();
        double expectedChange = amountPaid - expectedTotal;
        double receivedChange = testController.enterPayment(amountPaid);
        assertTrue(expectedChange == receivedChange, "The two changes do not match");

    }
}