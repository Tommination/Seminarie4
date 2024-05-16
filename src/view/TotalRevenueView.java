package view;
import DTOs.SaleDTO;
import model.TotalRevenueObserver;

public class TotalRevenueView implements TotalRevenueObserver {
    private double totalRevenue;

    public TotalRevenueView(){
        totalRevenue = 0;
    }

    @Override
    public void revenueStateChanged(SaleDTO sale){
        totalRevenue += sale.getTotal();
        System.out.println("Total revenue since program start: " + totalRevenue);
    }
}
