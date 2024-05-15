package integration;

import model.Payment;

/**
 * Placeholder for an actual integration class that handles interacting with the register.
 */
public class RegisterHandler {
    /**
     * fakes an interaction with a register of some sort.
     * @param amount an instance of Payment for a given sale
     * @return Returns the change to be given to the customer and shown on the receipt.
     */
    public double updateRegister(Payment amount){
        double change = amount.getChange();
        double paidAmount = amount.getPaidAmount();
        System.out.println("Updated register balance by adding " + paidAmount + " to the register and giving " + change + " back as change.");
        return change;
    }
}
