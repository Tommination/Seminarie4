package controller;

import DTOs.ItemDTO;
import DTOs.SaleDTO;
import integration.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.FileLogger;

import static org.junit.jupiter.api.Assertions.*;
public class ControllerExceptionTest{
    private Controller testController;
    private String IDFailConn;
    private String IDNotInDB;
    private String IDInDB;
    private String readFromfile;
    private LocalTime timeOfTest;

    @BeforeEach
    void setUp(){
        testController = new Controller();
        testController.setInvHandl(new InventoryHandler());
        testController.setRegHandl(new RegisterHandler());
        testController.setAccHandl(new AccountingHandler());
        testController.setErrorLogger(new FileLogger("PointOfSaleErrorLog.txt"));
        IDFailConn = "0070";
        IDNotInDB = "0000";
        IDInDB = "1337";
        timeOfTest = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        testController.startSale();
    }
    @AfterEach
    void tearDown(){
        testController = null;
        IDFailConn = null;
        IDNotInDB = null;
        timeOfTest = null;
    }

    @Test
    void testFailedConnection(){
        try{
            testController.scanItem(IDFailConn);
        }
        catch(ScanFailedException exception){
            try {
                readFromfile = Files.readString(Path.of("pointOfSaleErrorLog.txt"));
                assertTrue(readFromfile.contains(timeOfTest.toString()) && readFromfile.contains("connection"), "The log file has the wrong text");
            }
            catch(IOException ioe){
                fail("reading the file went wrong");
            }
        }
        catch (NoMatchingItemException exc){
            fail("Wrong type of exception thrown");
        }
    }
    @Test
    void testWrongID(){
        try{
            testController.scanItem(IDNotInDB);
        }
        catch(ScanFailedException exception){
            fail("Wrong type of exception thrown.");
            }
        catch(NoMatchingItemException exception){
            assertTrue(exception.getMessage().contains(IDNotInDB), "Wrong message returned with exception");
        }
        }
    @Test
    void testRightID(){
        SaleDTO saleInfo = null;
        saleInfo = scanID(IDInDB);
        assertTrue(saleInfo.getLatestScan().getItem().getID() == IDInDB, "IDs do not match");
    }
    private SaleDTO scanID(String ID){
        SaleDTO saleInfo = null;
        try{
            saleInfo = testController.scanItem(ID);
        }
        catch(ScanFailedException exception){
            fail("Wrong type of exception thrown.");
        }
        catch(NoMatchingItemException exception){
            fail("Exception thrown when it should not be");
        }
        return saleInfo;
    }
}




