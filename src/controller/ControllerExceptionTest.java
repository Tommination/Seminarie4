package controller;

import integration.AccountingHandler;
import integration.InventoryHandler;
import integration.RegisterHandler;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ControllerExceptionTest{
    private Controller testController;
    private String IDFailConn;
    private String IDNotInDB;

    private String readFromfile;
    private LocalTime timeOfTest;

    @BeforeEach
    void setUp(){
        testController = new Controller();
        testController.setInvHandl(new InventoryHandler());
        testController.setRegHandl(new RegisterHandler());
        testController.setAccHandl(new AccountingHandler());
        testController.setErrorLogger(new FileLogger());
        IDFailConn = "0070";
        IDNotInDB = "0000";
        timeOfTest = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        testController.startSale();
    }
    @AfterEach
    void tearDown(){
        testController = null;
        IDFailConn = null;
    }

    @Test
    void testFailedConnection(){
        try{
            testController.scanItem(IDFailConn);
        }
        catch(ScanFailedException exception){
            try {
                readFromfile = Files.readString(Path.of("pointOfSaleLog.txt"));
                assertTrue(readFromfile.contains(timeOfTest.toString()) && readFromfile.contains("connection"), "The log file has the wrong text");
            }
            catch(IOException ioe){
                fail("reading the file went wrong");
            }
        }
    }
    @Test
    void testWrongID(){
        try{
            testController.scanItem(IDNotInDB);
        }
        catch(ScanFailedException exception){
            assertTrue(exception.getMessage().contains("Scan has failed."));
            }
        }
}

