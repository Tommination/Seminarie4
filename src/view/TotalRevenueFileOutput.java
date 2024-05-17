package view;

import DTOs.SaleDTO;
import model.TotalRevenueObserver;
import util.FileLogger;
import util.StoreMath;

public class TotalRevenueFileOutput implements TotalRevenueObserver {
    private double totalRevenue;
    private FileLogger totalRevenueLog;
    public TotalRevenueFileOutput(){
        totalRevenueLog = new FileLogger("totalRevenueLog.txt");
        totalRevenue = 0;

    }

    @Override
    public void revenueStateChanged(SaleDTO sale) {
        totalRevenue += sale.getTotal();
        totalRevenue = StoreMath.roundTo1Decimal(totalRevenue);
        updateLogFile();
    }
    private void updateLogFile(){
        totalRevenueLog.log("Total revenue brought in since program started: " + totalRevenue);
    }
}
