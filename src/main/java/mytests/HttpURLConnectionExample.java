package mytests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {

    public static final String TOKEN = "H4sIAAAAAAAAAB2PybKqMABEvyivEBlkeYUgQQZFIISNxSQEjIjMfP3z3m1X9%2BnuYjWr9JRRl5q3YEM7h6IevTwxU5GEmncUqqbyr1jNjfDKHN%2BQhFdzjbHeIDrTfG%2B%2B81NAo9tM46iaUd0ujl%2FO9oZE2w92lmpWeeS16Z%2Fv%2BczWvzzyd57mc6LrBU6AnlyP2JtLvmzEoBjXULC%2FbZZvNjELBkcjS0x3lYuhYPlh5WqZQLC9OhtaHfU7ljl9gsMx15V3%2FDu6hivB1729HWsLE845wS%2Bj4WyVE0lNuK82E9%2BjcY0Em10F5%2Fegs3Vb2Z27MdCtZ7AqkJhGmhY7VQlVSbU%2BTNWtT899eAYMjnS%2B6R1u4wD4dyy7hXifmKwj%2BQX1V8fLQdXlaOi8YlTAPMHLHD14JxNrbuGrZh9%2BpP0lLtVTed2GPSbw%2FMPw6dBvyug3%2FMESBwHYbjJnksHcWjCuy2HJ3XEKh0n0krQtFEMix1GGnKjBu4tY0vVdg9uiNWr7gYfQ21sauMVOaY6pCI7GUW%2BwDdTw3MrT2xIFAaRPmjhTBrJDQA3ehUarnLJgkhXwuIByuzREzpcmffaHQShkOr5e0WuWAAIll9TQWzT8E17t1XAFPr1fL4%2BspX2pRxnaCSavVUeDWfGRPYKf%2Fysgv2ZcAgAA";

    public static void main(String[] args) throws Exception {
        
        URL url = new URL("https://api-hot-connect.hotmart.com/subscriber/rest/v2?rows=10&status=CANCELLED_BY_CUSTOMER&status=CANCELLED_BY_SELLER&status=CANCELLED_BY_ADMIN");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization","Bearer "+ TOKEN);
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println("Response:-" + response.toString());
    }
}
