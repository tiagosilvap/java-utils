package hotmart.requestwrongsubspayment;

import com.hotmart.checkoutvo.checkout.ProductResponseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderChecker implements Serializable {
    
    private static final long serialVersionUID = -8119416986865361502L;
    
    private List<ProductResponseVO> products;
}
