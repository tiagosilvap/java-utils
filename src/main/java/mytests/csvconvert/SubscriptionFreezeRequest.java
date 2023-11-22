package mytests.csvconvert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionFreezeRequest implements Serializable {
    
    private static final long serialVersionUID = 1;
    
    @NotNull
    @JsonProperty("subscriber_code")
    private String subscriberCode;
    
    @NotNull
    @JsonProperty("new_date_next_charge")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date newDateNextCharge;
    
    @Override
    public String toString() {
        return "SubscriptionFreezeRequest{" +
                "subscriberCode='" + subscriberCode + '\'' +
                ", newDateNextCharge=" + newDateNextCharge +
                '}';
    }
}
