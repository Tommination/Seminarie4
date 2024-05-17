package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Prints message to a file, the log file will be in the current directory and be called "PointOfSaleLog.txt"
 */
public class FileLogger {
    private PrintWriter logStream;

    public FileLogger(String fileName){
        try{
            logStream = new PrintWriter(new FileWriter(fileName), true);
            }
            catch (IOException ioe){
                System.out.println("CAN'T LOG");
                ioe.printStackTrace();
            }
        }
    public void log(String message){
        logStream.println(message);
    }
}



