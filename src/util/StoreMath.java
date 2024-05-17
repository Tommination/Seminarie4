package util;

import static java.lang.Math.round;

public class StoreMath {

    public static double roundTo1Decimal(Double number){
        return (round(number*10))/10.0;
    }
}
