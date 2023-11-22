package hotmart.requestwrongsubspayment;

import com.amazonaws.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotmart.checkoutvo.business.load.LoadResponseVO;
import com.hotmart.checkoutvo.checkout.PaymentMethodVO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RequestWrongSubsPayment {

    private String url = "https://api-astrobox.analytics.aws.hotmart:443/v1/executor/reactive/by-alias/oc_wrong_subscription_payment_value_hotpay?subscriptionId={0}";
    
    private String urlMarketplace = "https://api-astrobox.analytics.aws.hotmart:443/v1/executor/reactive/by-alias/oc_wrong_subscription_payment_value_marketplace?transaction={0}";
    
    private String urlLoad = "https://api-astrobox.analytics.aws.hotmart:443/v1/executor/reactive/by-alias/checkout_transaction_log_create?transaction={0}&datePickerRangeBegin={1}&datePickerRangeEnd={2}";
    private String tokenCheckout = "Bearer H4sIAAAAAAAAAHVS25KrKBT9okyhJnZ8bKM4kogBAS8vVjTxbjrdxjb69Qdn6tRM19Q8AZvNurFvM6oyJ6%2F9GgV8cRVcu4N7p7v84Opu%2B4jEARl%2F3Ga0xKoxJYGrhzOakxC2bj3VVw09rg6vo2Cq40iAi2PMl4g%2Bksir%2FW6ohd1ZARA8Akgw%2B1kwxYS8hTzghs9nSdRWZwFNMwzWfQKJoIK3oghsg3MbJhEQMQGVydorjAAOKKdIQMMiAtkr%2F9pHWugHv7FsJHufhWhQIXFMevhvXfZ7hO8sKgyJt2JCTlf%2BDgvSYYu3HZG6z6z%2B%2BZYC4VOBEFEMRgXlzBaY8h3%2FS4dc5Z1P%2BKsgwiRM%2BpZeEAPb%2BnRALlPoQXKeIiWeI8WUHrFLBIY%2FPChG6HUGIaL7B%2Fd3DgrmhF8Ra%2FMVj0ndEqPjf%2BeGXXk%2BMWXN%2Bd95%2FvT9v%2Fo7MLj9A1zk37qNBxJmdj6j9YmRyWviJ7bkegBb7LhA1lS8lK%2B4yYHPPC1Z8%2B3xcAnFeIXGI1mHpuFazHATs3x7Yi2IQ%2FLEDmziWal8C0ncpMGN6OOlqrzlWnmzHLAE697XWZSpegPpK%2FdtnPvkLbWWk16rU7qfVWV69lFJXL3e59%2F%2BI3hvnGYPlUarLl97aJWuKEPDMu7XjfiubtRLo0%2FtNVxKWiyn7PgyCyimDyOZ8j7n5W6nMh%2F92TwfaTjeTntS64GXFZ%2FLckiUgm%2FieZ8s2n3s1W1qqp%2BNZvTl9%2FE%2Bxd93rVSTUfeDrM%2Bebs9h6jvbOXidjGfbVNA2WVWV5jZK434U4yF5G1rkPfaDgaudmsXePE4YKMoIzAUejsP7bO4%2Fr3r0SqaNol%2B1s%2FZ2fLw0oAub3sM5c8z08mEXeRbenMZyX%2FbNWu7wlo330Nksxy%2F1eKzrYVu9UX6Mb4LlFp0AAJuPOdbO778AX2BmBtwDAAA%3D";

    public static RequestWrongSubsPayment getInstance() {
        return new RequestWrongSubsPayment();
    }

    public List<WrongSubsPaymentValueHotpayVO> findTransactions(List<Integer> subscriptionIds) {
    
        List<WrongSubsPaymentValueHotpayVO> responseVO = new ArrayList<>();
        WrongSubsPaymentValueHotpayVO response;
    
        if(CollectionUtils.isNotEmpty(subscriptionIds)) {
            for(Integer id : subscriptionIds) {
                Unirest.config().verifySsl(false);
    
                String endpoint = getUrl(url, String.valueOf(id));
    
                HttpResponse<String> httpResponse = Unirest.get(endpoint).header("Authorization", tokenCheckout).asString();
                
                try {
                    response = new ObjectMapper().readValue(httpResponse.getBody(), WrongSubsPaymentValueHotpayVO.class);
                    responseVO.add(response);
                } catch (Exception e) {
                    System.err.println("[findLoadsCheckout] SUBSCRIPTION: " + id + " ERROR: " + e.getMessage());
                }
            }
        }
        return responseVO;
    }
    
    public Response findLoadsCheckoutWithVatAndTax(List<WrongSubsPaymentValueHotpayVO> transactions) {
    
        Response response = new Response();
        WrongSubsPaymentValueMktVO marketplaceVO;
    
        if(CollectionUtils.isNotEmpty(transactions)) {
            for(WrongSubsPaymentValueHotpayVO transaction : transactions) {
                String endpoint = getUrl(urlMarketplace, String.valueOf(transaction.getHotpayReference()));
                HttpResponse<String> httpResponse = Unirest.get(endpoint).header("Authorization", tokenCheckout).asString();
                try {
                    marketplaceVO = new ObjectMapper().readValue(httpResponse.getBody(), WrongSubsPaymentValueMktVO.class);
                    
                    BigDecimal taxValue = transaction.isSmartInstallment() ? transaction.getTaxinstallmentTaxValue() : transaction.getTaxValue();
    
                    boolean isEquals = valuesApproximatelyEqual(transaction.getTaxRate(), marketplaceVO.getVatPercentageCost())
                            && valuesApproximatelyEqual(taxValue, marketplaceVO.getVatCalculatedCost());
                    
                    response.getSuccess().add(transaction.getSubscriptionId().toString() + " " + isEquals);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getError().add(transaction.getSubscriptionId().toString() + " " + e.getMessage());
                }
                
            }
        }
        return response;
    }
    
    public Response findLoadsCheckout(List<WrongSubsPaymentValueHotpayVO> transactions) {
        
        Response response = new Response();
        BigDecimal loadValue = null;
        String result = null;
        String products;
        LoadResponseVO loadCheckout = null;
        
        if(CollectionUtils.isNotEmpty(transactions)) {
            for(WrongSubsPaymentValueHotpayVO transaction : transactions) {
                Unirest.config().verifySsl(false);
                String endpoint = getUrl(urlLoad, transaction.getHotpayReference(), transaction.getStartDateInFirstHourOfDay(), transaction.getEndDateInLastHourOfDay());
                HttpResponse<String> httpResponse = Unirest.get(endpoint).header("Authorization", tokenCheckout).asString();
                
                if(httpResponse != null && !StringUtils.isNullOrEmpty(httpResponse.getBody())) {
                    try {
                        products = new ObjectMapper().readTree(httpResponse.getBody()).get("_col6").asText();
                        loadCheckout = new ObjectMapper().readValue(products, LoadResponseVO.class);
        
                        PaymentMethodVO paymentMethodVO = Stream.of(loadCheckout)
                                .filter(load -> Objects.nonNull(load) && CollectionUtils.isNotEmpty(load.getProducts()))
                                .flatMap(load -> load.getProducts().stream())
                                .filter(p -> CollectionUtils.isNotEmpty(p.getOffer().getPaymentMethods()))
                                .flatMap(p -> p.getOffer().getPaymentMethods().stream())
                                .filter(method -> {
                                    if(transaction.getInstallments() > 1 && transaction.getPaymentTypeInfo().equals("WALLET")) {
                                        return method.getType().name().equals("CREDIT_CARD");
                                    }
                                    return method.getType().name().equals(transaction.getPaymentTypeInfo());
                                })
                                .findFirst()
                                .orElse(null);
        
        
                        if(Objects.nonNull(paymentMethodVO)) {
                            loadValue = transaction.getInstallments() > 1
                                    ? Stream.of(paymentMethodVO.getInstallments())
                                    .filter(Objects::nonNull)
                                    .flatMap(Collection::stream)
                                    .filter(i -> transaction.getInstallments().equals(i.getNumber()))
                                    .findFirst()
                                    .orElseGet(null)
                                    .getFullAmount()
                                    .getValue()
                                    : paymentMethodVO.getAmount().getValue();
                        }
        
                        boolean isEquals = Objects.nonNull(loadValue) ? loadValue.equals(transaction.getTransactionValue()) : false;
        
                        result = transaction.getSubscriptionId().toString() + " " + isEquals;
                        response.getSuccess().add(result);
        
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = transaction.getSubscriptionId().toString() + " " + e.getMessage();
                        response.getError().add(result);
        
                    }
                } else {
                    result = transaction.getSubscriptionId().toString() + " Body response is null or empty ";
                    response.getError().add(result);
                }
            }
        }
        return response;
    }
    
    
    private String getUrl(String url, String ... params) {
        return MessageFormat.format(url, params);
    }
    
    private boolean valuesApproximatelyEqual(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2.add(BigDecimal.valueOf(0.01))) <= 0
                && value1.compareTo(value2.subtract(BigDecimal.valueOf(0.01))) >= 0;
    }
    

    
    public static void main(String[] args) throws IOException {
        List<Integer> subscriptionIds = Arrays.asList(21179849);
        
        List<WrongSubsPaymentValueHotpayVO> transactions = RequestWrongSubsPayment.getInstance().findTransactions(subscriptionIds);
        Response response = RequestWrongSubsPayment.getInstance().findLoadsCheckout(transactions);
//        Response response = RequestWrongSubsPayment.getInstance().findLoadsCheckoutWithVatAndTax(transactions);
        
        response.getSuccess().forEach(System.out::println);
        System.out.println("\n");
        response.getError().forEach(System.out::println);
        System.out.println("-------------------- FIM --------------------");
    }
}
