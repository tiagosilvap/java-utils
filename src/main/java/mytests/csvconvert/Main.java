package mytests.csvconvert;

import com.hotmart.util.exception.HotmartException;

import java.util.List;

public class Main {
    
    public static void main(String[] args) throws HotmartException {
        String payload = "subscriber_code,new_date_next_charge\n" +
                "S9OHD5HY,11/10/2022\n" +
                "NYCBEOAV,11/10/2022";
        List<SubscriptionFreezeRequest> subscriptionsFreezeRequest = new SubscriptionFreezeCSVParser().parse(payload);
        System.out.println(subscriptionsFreezeRequest);
    }
}
