package view;
import DTOs.SaleDTO;
import model.TotalRevenueObserver;
import util.StoreMath;

public class TotalRevenueView implements TotalRevenueObserver {
    private double totalRevenue;

    public TotalRevenueView(){
        totalRevenue = 0;
    }

    @Override
    public void revenueStateChanged(SaleDTO sale){
        totalRevenue += sale.getTotal();
        totalRevenue = StoreMath.roundTo1Decimal(totalRevenue);
        printRevenue();
    }
    private void printRevenue(){
        System.out.println("Total revenue since program start: " + totalRevenue);
    }
}
