package controller;

import integration.NoMatchingItemException;

public class ScanFailedException extends Exception{
    ScanFailedException(){
        super("Unable to scan Item, please try again");
    }
}
