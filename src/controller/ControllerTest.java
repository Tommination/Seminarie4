package controller;

import DTOs.SaleDTO;
import integration.AccountingHandler;
import integration.InventoryHandler;
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
        expectedItem = new Item(inv.getItemDetails("1337"));
        amountOfScans = 2;
        SaleItem item = new SaleItem(expectedItem, amountOfScans);
        IDToTest = "1337";
        expectedLastItem = item;
        expectedTotal = (expectedItem.getItem().getPrice() * amountOfScans);
        expectedVAT = ((expectedItem.getItem().getPrice() * expectedItem.getItem().getVATRate() * amountOfScans));
        expectedTotal += expectedVAT;
        double amountPaid = 57;

    }

    private SaleDTO scanItemTimes(int amountOfScans) {
        testController.startSale();
        for (int i = 0; i < amountOfScans; i++) {
            saleInfo = testController.scanItem(IDToTest);
        }
        return saleInfo;
    }
    private SaleDTO performSale(){
        saleInfo = scanItemTimes(amountOfScans);
        return saleInfo;
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