package model;

import DTOs.SaleDTO;

import static java.lang.Math.round;

/**
 * Groups the data for a payment in one class.
 */
public class Payment {
    private double total;
    private double paidAmount;

    /**
     * Creates an instance, groups both values for payment
     * @param saleInfo where total comes from, an entire sale to improve encapsulation
     * @param amount how much the customer has paid.
     */
    public Payment(SaleDTO saleInfo, double amount){
        total = saleInfo.getTotal();
        paidAmount = amount;
    }

    public double getTotal() {
        return total;
    }

    /**
     * Calculates the change from a given payment
     * @return
     */
    public double getChange(){
        return roundTo1Decimal(getPaidAmount()-getTotal());
    }
    public double getPaidAmount() {
        return paidAmount;
    }

    private double roundTo1Decimal(Double number){
        return (round(number*10))/10.0;
    }
}
