package integration;

import DTOs.SaleDTO;

/**
 * Placeholder for an actual integration class that handles accounting.
 */
public class AccountingHandler {
    /**
     * fakes call to other unit, prints message to represent actual integration
     * @param saleInfo The sale information that would be sent to an accountnig unit.
     */
    public void recordSale(SaleDTO saleInfo){
        System.out.println("sent sale info presented on receipt to External inventory system");
    }
}
