package hotmart.requestwrongsubspayment;

import java.util.ArrayList;
import java.util.List;

public class Response {
    
    private List<String> success = new ArrayList<>();
    
    private List<String> error = new ArrayList<>();
    
    public List<String> getSuccess() {
        return success;
    }
    
    public void setSuccess(List<String> success) {
        this.success = success;
    }
    
    public List<String> getError() {
        return error;
    }
    
    public void setError(List<String> error) {
        this.error = error;
    }
}
