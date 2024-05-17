package model;

import DTOs.ItemDTO;
import DTOs.SaleDTO;
import integration.FailedConnectionException;
import integration.InventoryHandler;
import integration.NoMatchingItemException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
public class SaleExceptionTest {
    private String IDFailConn;
    private String IDNotInDB;
    private String IDInDB;

    private LocalTime timeOfTest;
    Sale testSale;

    @BeforeEach
    void setUp(){
        IDInDB = "1337";
        IDFailConn = "0070";
        IDNotInDB = "0000";
        InventoryHandler inv = new InventoryHandler();
        testSale = new Sale(inv);
        timeOfTest = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    @AfterEach
    void tearDown(){
        testSale = null;
    }

    @Test
    void testFailedConnection(){

        try{
            testSale.checkIdentifier(IDFailConn);
        }
        catch (FailedConnectionException exc){
            assertTrue(exc.getMessage().contains(timeOfTest.toString()), "Wrong time in Error message");
        }
        catch (NoMatchingItemException exc){
            fail("Wrong exception thrown");
        }
    }
    @Test
    void testItemNotAvailable(){

        try{
            testSale.checkIdentifier(IDNotInDB);
        }
        catch (FailedConnectionException exc){
            fail("Wrong exception thrown");
        }
        catch (NoMatchingItemException exc){
            assertTrue(exc.getMessage().contains(IDNotInDB));
        }
    }

    @Test
    void testSaleNotChangedByConnectionException(){
        SaleDTO saleBeforeException = makeSale(IDInDB);
        try{
            testSale.checkIdentifier(IDFailConn);
        }
        catch (FailedConnectionException exc){
            assertFalse(compareSale(saleBeforeException), "State has changed after failed connection exception");

        }
        catch(NoMatchingItemException exc){
            fail("Wrong exception type thrown");
        }
    }
    @Test
    void testSaleNotChangedByNoMatchingItemException(){
        SaleDTO saleBeforeException = makeSale(IDInDB);
        try{
            testSale.checkIdentifier(IDNotInDB);
        }
        catch (FailedConnectionException exc){
            fail("Wrong exception type thrown");
        }
        catch(NoMatchingItemException exc){
            assertFalse(compareSale(saleBeforeException), "State has changed after failed connection exception");
        }
    }

    private SaleDTO makeSale(String ID){
        SaleDTO sale = null;
        try{
            sale = testSale.checkIdentifier(ID);
        }
        catch (FailedConnectionException | NoMatchingItemException exc){
            fail("Exception thrown for ID that exists");
        }
        return sale;
    }
    private boolean compareSale(SaleDTO saleToCompare){
        boolean saleStateChanged = true;
        if (saleToCompare.getTotal() == testSale.getTotal()){
            if (saleToCompare.getLatestScan().equals(testSale.getItemsInSale().getLast())){
                if(saleToCompare.getItemsInSale().size() == testSale.getItemsInSale().size()){
                    for(int i = 0; i<saleToCompare.getItemsInSale().size(); i++){
                        if (saleToCompare.getItemsInSale().get(i).getItem().getID().equals(testSale.getItemsInSale().get(i).getItem().getID())){
                            saleStateChanged = false;
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        }
        return saleStateChanged;
    }
}
