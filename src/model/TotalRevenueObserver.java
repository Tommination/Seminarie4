package model;

import DTOs.SaleDTO;

public interface TotalRevenueObserver {
    /**
     * Invoked after a payment has been made
     * @param sale The latest sale to add to the total revenue
     */
    void revenueStateChanged(SaleDTO sale);
}
