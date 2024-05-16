package controller;

import integration.NoMatchingItemException;

/**
 * Exception thrown by the contoller when an item was not able to be scanned.
 */
public class ScanFailedException extends Exception{
    /**
     * Reduces information to what is relevant for the view.
     */
    ScanFailedException(){
        super("Scan has failed.");
    }
}
