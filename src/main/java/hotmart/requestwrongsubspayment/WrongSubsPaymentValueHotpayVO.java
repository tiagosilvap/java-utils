package hotmart.requestwrongsubspayment;

import com.hotmart.util.date.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WrongSubsPaymentValueHotpayVO implements Serializable {
    
    private static final long serialVersionUID = 1664355055380867797L;
    
    private Long subscriptionId;
    
    private Date releaseDate;
    
    private String hotpayReference;
    
    private BigDecimal transactionValue;
    
    private BigDecimal subscriptionPaymentValue;
    
    private Integer installments;
    
    private String paymentType;
    
    private BigDecimal taxRate;
    
    private BigDecimal taxValue;
    
    private BigDecimal taxinstallmentTaxValue;
    
    private boolean smartInstallment;
    
    public String getPaymentTypeInfo() {
        if(paymentType.equals("PAYPAL_INTERNACIONAL")) {
            return "PAYPAL";
        }
        return paymentType;
    }
    
    public String getStartDateInFirstHourOfDay() {
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(releaseDate.getTime());
        
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        
        return DateUtils.formatDateToStringSql(cal.getTime());
    }
    
    public String getEndDateInLastHourOfDay() {
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(releaseDate.getTime());
        
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    
        return DateUtils.formatDateToStringSql(cal.getTime());
    }
}
