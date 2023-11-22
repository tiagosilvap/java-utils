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
public class WrongSubsPaymentValueMktVO implements Serializable {
    
    private static final long serialVersionUID = -1030121275274039778L;
    
    private Long subscriptionId;
    
    private String transaction;
    
    private BigDecimal vatPercentageCost;
    
    private BigDecimal vatCalculatedCost;
}
