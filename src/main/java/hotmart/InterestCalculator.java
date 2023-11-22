package hotmart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InterestCalculator {
    
    public static final Double INTEREST_RATE = 0.0289;
    public static final Integer INSTALLMENTS = 2;
    
    public static void main(String[] args) {
        
        BigDecimal installmentValue = calculateInstallmentsFromOfferPriceAndInstallments(40.00, INSTALLMENTS);
        BigDecimal purchaseValue = calculateOrderPriceFromInstallmentValueAndInstallmentNumber(installmentValue, INSTALLMENTS);
        BigDecimal totalValue = installmentValue.multiply(BigDecimal.valueOf(INSTALLMENTS));
        
        System.out.println(installmentValue);
        System.out.println(purchaseValue);
        System.out.println(totalValue);
    }
    
    public static BigDecimal calculateInstallmentsFromOfferPriceAndInstallments(Double price, int installments) {
        Double orderPrice = null;
        if (INTEREST_RATE.doubleValue() == 0.0){
            orderPrice = price / installments;
        } else {
            orderPrice = price / ((1 - (Math.pow((1 + INTEREST_RATE), (installments * -1)))) / INTEREST_RATE);
        }
        return new BigDecimal(orderPrice).setScale(2, RoundingMode.UP);
    }
    
    public static BigDecimal calculateOrderPriceFromInstallmentValueAndInstallmentNumber(BigDecimal installmentValue, int installments) {
        BigDecimal orderPrice = null;
        if (INTEREST_RATE.doubleValue() == 0.0 || installments == 1){
            orderPrice = installmentValue.multiply(BigDecimal.valueOf(installments));
            
        } else {
            orderPrice = installmentValue.multiply(BigDecimal.valueOf(((1 - (Math.pow((1 + INTEREST_RATE), (installments * -1)))) / INTEREST_RATE)));
        }
        return orderPrice.setScale(2, RoundingMode.UP);
    }
}
