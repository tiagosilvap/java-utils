package mytests;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {
    
    public static void main(String[] args) {
        
        System.out.println("\u001B[34m \nBigDecimal Test \u001B[0m");
        System.out.println(BigDecimal.TEN.compareTo(BigDecimal.ONE) > 0);
        
        System.out.println(
                BigDecimal.valueOf(100)
                        .divide(BigDecimal.valueOf(6), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(2))
        );
        
        System.out.println(BigDecimal.valueOf(100)
                .divide(BigDecimal.valueOf(6), 2, RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(2))
        );
    }
}
