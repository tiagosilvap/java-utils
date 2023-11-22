package mytests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import kong.unirest.ContentType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.text.MessageFormat;

public class UnirestRequestWithHystrix {
    
    public static final String token = "H4sIAAAAAAAAAHVWXZOiuhb9RX2LD22bRxGCMBKaQALkpUugESHYngEV%2BPV3B3vuzNyq82Bp4mZ%2FrrU2n5NX505xDs5eRGdXxWe3dy9kXezcV7e9pmznGf%2F5nDy10NiUaus6T6g01spkbLJk3ebT8M8x5Y%2Fv8z106nvpqHVxCW%2BZZgzBzJtsNlsen1aHmDe%2BZQ%2FY8kQWqXUWh9Mh9s5%2B49VBXLaBFSp4JxPATa6bwm2uuXsB2yQ8H3aeXnRC4ZFXwu%2BvXA%2BHMsWiOEOijTuB3xWeBzN3xI1HJuUpVlwH3%2FPIsq5vQfN43VtfVr%2Fxm%2B3j0NhaEK1Gf7fSAp2ruYPrvCP3H9r1Wl7M77OxPTqGkidoKnfrJteUTZaM17xjivQdTArE3Gp%2BHKpBXDzg9yNQ1Ht%2BITNPvSlLw02gjIgoyE8VFjBb0CQezNIRWj4PKG4GRpVwk81C4IRCl8LBt1qdR8roJ1Q5JJ7AFh38xgXf5hlb2zVv6jqY7RnP4YxjG%2BJnD3%2FOblDHiONshHvVn7cr3zo98OyuA6t%2FgL3MTw%2Bs7Ywb2aPtxldrMxb4nGvjjaenKbGQ6iPzBt9nlgwrH7HRp94zdnzSpQ9snTTwr%2FuxLWvdBDHufA23ftMOWbOds0iZuUNVyLvxuxBmHGpcztuxHzgum0AnU5nQTTD3ih8Xoz%2F38rf8yNmKz%2F32HDRLrmsc%2ByPk2rsdUkvAk5z7MWHzgoe2fme2alJBqpCZXqp4iNm29PHIHbTOOmPlnh9nqrIdVXAUMZNG1AioaoS0NaKQEUQWjJlTrhE100mV6yX0wZU%2B3Fglu5CuD6maTanqoZAu2JsBC1PpMLDzKomx4wQ%2BBAuJjShhRkBYWVF7hL4a76TliC5EwmqxN%2B%2FFBWIk6JYlpcSrJNh0TFAv8wTO1Ple1gcTg3Ohs0eue4Bz6d80Q4Yr0jKTIRYRedchhQPv4PO06fAXT%2FDPXGdKqkE8nQDm4b71Z2q%2FDdCj8EC9AzmrVqyomNG1Fypo6cm%2F9Mll1KtiFWoQ3JN1hS2j4RJLcmWsIEYNvRhkDtIH1HbNdyrkpcoZQF7AbZ1cS5nr8py4gf0k9SDVyyt3SlHKGXTkWuhmn6WiyhIyHJPVwvNjSr6e9SB4TjTH1IR44p4vvfMExJHYnXM5g5Z7cYuCUGF2qgjIn5ixPWJC1zRVkJ1QFqSKnI%2F4XYdgQWxDwcpJxoupjSKgHQJ7n9q1maosJsz9nrt45Am7Lfm2a%2BCzAX2CmS9%2BSMQYpoQCFunox2rpR3BOFfxOkfdOELOA9%2B%2FxeZnTnGojaBl5YqBDbTF93%2BswtwVfdMFx0RltmZL70w40LDUneO55htwjAANgLgyZ%2BK7z%2F%2BvmCHDOaEt%2FYVraAj5RQOhjqavUhJJpNcySnQsNT9BjwKBxS%2BU8nnOAGZAqS%2FFPwNXEE6TkuvvrvodzW8zLuQbNE5kmHllKrrm2%2BrunainzQgwZDoG%2BMeYhiRlii4hQ2SvPjNuhYsiTmuQQ5kEvCYc6KqoYTqx69A%2BOy9igxesbYF7xv%2FuYJ%2BImfUpMFs4osSn59vMPTuOQST%2Bjx1r2GwcX4KZOgINQo55Ni48L6zPAXa4aZ56S%2BslJsy66oefs9x3oQc%2FlrllmaNyWvcMMqf0acFlIG%2B4wJUtEX6jGkOtcpJonSmepg1LBYmZLzCLYDcwC%2Fpm%2FsWI8wM8T8xdf2n%2FvX6%2BXuS66oeOq0FAPu6iSNefd%2BltbsAJzEP%2FDLPBlwRb6nfu%2FcZ62LIG%2BS2ztCCPm09Y0aTv4cAf7ikXftSu5w2q%2Bx1UG%2BzBbeskF7FDBIZa0iWyDAgb4sw9iljl%2BLro0eoDVXazieKlDqwE7wx3wLjVgDZpSHaW2pd7tiSfQx6UONpewiwGj1V8aefGugN%2F7N5fuoD9n%2BK%2F%2Blcdf%2Fi%2FsmjvAP2H8oVWLDq3zDoH%2B4CrXePeNm0HO7iB7mUqNIstMpcYntIxilQXLHhGYhXJTtyJc5idwFSG2S1XyHitj%2FAd2r%2FmF339hg6qlx2zspqrpw76BXcYo9Axmghd%2BQN099AEww7RFZ4UCeV6VYyTfd0THG3fNnfBxiLdq4LgDdjINS92d4d0hFp0%2Fo9pP3ClrqO4%2F9wT4A0wg48p3i4826%2BwV%2FK8dYgTvX6fBj5EIpI%2FGhvePUMMNPnPrpPtauPYneBGs0xd64%2Ff2o%2FiA947XPth9mNX%2BdNsH0xdR9o51vH81ldrX6cfPfUfbg%2FPVFG91UmTzxKzL2%2Ft79XJ3NavaHPv9jyY7f%2FzwG0JP%2FLQ3xtdi5fPA2hv9P2%2BP%2B01NsnO7Yfxa%2Fow6l2%2FWiXH9FHicWrbvZi%2FYFJnlszgaLe3YfKTKyfFS1L7O1WvXCI%2BXcXXsNWNf7tiqKNyaXMll1Z3uTX9bpW%2FxeUZM2bZRaX9uxYPfAyfdVNP0oDrfm7kRBqj9bHwSbKcsym911z1a%2F3DYRbhRXRyZ%2Be6rNu2XfHM9KsPL59mpyn35lb0L9T5T6%2Bun%2BbLGpdus0zLyuk70U%2FRycq6ZkhyH%2FuHNF3O3%2B0Jbf5fbeXz6L1TVCpKECwAA";
    
    private String server;
    private String service;
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    public UnirestRequestWithHystrix() throws IOException {
        server = "http://localhost:8080";
        service = "/api/v1/test";
    }
    
    public static UnirestRequestWithHystrix getInstance() throws IOException {
        return new UnirestRequestWithHystrix();
    }
    
    public Object sendRequestWithHystrix(Long id) {
        return new RunnerExecuteCommand(id, this).execute();
    }
    
    public Object sendRequest(Long id) {
        try {
            String url = server.concat(service);
            
            HttpResponse<JsonNode> httpResponse = Unirest.put(getUrl(url, id))
                    .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(writeValueAsString(id))
                    .asJson();
            
            if (!httpResponse.isSuccess()) {
                System.out.println("Failed getting subscription payment");
            }
            
            return handleObject(httpResponse.getBody().toString());
            
        } catch (UnirestException e) {
            System.out.println("### UnirestException Failed " + id);
        }
        return null;
    }
    
    private String getUrl(String apiURL, Object ... parameters) {
        if (parameters != null && parameters.length > 0) {
            apiURL = MessageFormat.format(apiURL, parameters);
            apiURL = apiURL.replaceAll(",","");
            apiURL = apiURL.replaceAll("\\.","");
        }
        return apiURL;
    }
    
    public static String writeValueAsString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Object handleObject(String json) {
        try {
            return mapper.readValue(json, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private class RunnerExecuteCommand extends HystrixCommand<Object> {
        public static final String DEFAULT_EXECUTION_ISOLATION_THREAD_TIMEOUT = "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds";
        private final Long TIMEOUT = 3000l;
        private UnirestRequestWithHystrix unirestRequestWithHystrix;
        private Long id;
        
        RunnerExecuteCommand(Long id, UnirestRequestWithHystrix unirestRequestWithHystrix) {
            super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(RunnerExecuteCommand.class.getName()))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(true)
                            .withCircuitBreakerRequestVolumeThreshold(1)
                            .withCircuitBreakerSleepWindowInMilliseconds(10)
                            .withCircuitBreakerErrorThresholdPercentage(100)));
            ConfigurationManager.getConfigInstance().setProperty(DEFAULT_EXECUTION_ISOLATION_THREAD_TIMEOUT, TIMEOUT);
            this.unirestRequestWithHystrix = unirestRequestWithHystrix;
            this.id = id;
        }
        
        @Override
        protected Object run() {
            return unirestRequestWithHystrix.sendRequest(id);
        }
        
        @Override
        protected Object getFallback() {
            System.out.println("Error: " + executionResult.getExecutionException() + " " + id);
            return null; // defaults NULL in case of error
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        UnirestRequestWithHystrix request = UnirestRequestWithHystrix.getInstance();
        Object object = request.sendRequest(1L);
        System.out.println(object);
    }
}
