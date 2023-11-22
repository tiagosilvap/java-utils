package mytests.csvconvert;

import com.hotmart.util.exception.HotmartException;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SubscriptionFreezeCSVParser {
    
    private static Logger log = LoggerFactory.getLogger(SubscriptionFreezeCSVParser.class);
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public List<SubscriptionFreezeRequest> parse(String rawContent) throws HotmartException {
        try {
            CSVReader csvReader = new CSVReader(new StringReader(rawContent));
            csvReader.skip(1);
            List<SubscriptionFreezeRequest> subscriptionsFreezeRequest = new LinkedList<>();
            
            for (String[] line : csvReader) {
                String subscriberCode = line[0];
                Date newDateNextCharge = dateFormat.parse(line[1]);
                
                subscriptionsFreezeRequest.add(SubscriptionFreezeRequest.builder()
                        .subscriberCode(subscriberCode)
                        .newDateNextCharge(newDateNextCharge)
                        .build());
            }
            return subscriptionsFreezeRequest;
            
        } catch (Exception e) {
            String errorMessage = "Error converting CSV to subscription freeze object. " + e.getMessage();
            log.error(errorMessage, e);
            throw new HotmartException(errorMessage, e);
        }
    }
}
