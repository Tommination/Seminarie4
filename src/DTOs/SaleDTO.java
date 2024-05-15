package DTOs;

import model.Receipt;
import model.Sale;
import model.SaleItem;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * DTO for the Sale class
 */
public class SaleDTO {
    private ArrayList<SaleItem> itemsInSale;
    private LocalTime saleTime;
    private SaleItem latestScan;
    private double total;
    private double totalVAT;

    /**
     * Creates an instance from a sale, stores its data plus information about the latest scanned item
     * @param sale The sale data comes from
     * @param latest The last item scanned
     */
    public SaleDTO(Sale sale, SaleItem latest){
        itemsInSale = sale.getItemsInSale();
        saleTime = sale.getSaleTime();
        latestScan = latest;
        total = sale.getTotal();
        totalVAT = sale.getTotalVAT();
    }

    public LocalTime getSaleTime() {
        return saleTime;
    }

    public SaleItem getLatestScan() {
        return latestScan;
    }

    public ArrayList<SaleItem> getItemsInSale() {
        return itemsInSale;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalVAT() {
        return totalVAT;
    }
}
