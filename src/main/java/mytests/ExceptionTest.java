package mytests;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ExceptionTest {
    
    public static void main(String[] args) throws Exception {
        ExceptionTest exceptionTest = new ExceptionTest();
        exceptionTest.functionTest();
    }

    public BigDecimal function() throws Exception {
        try {
            if(Boolean.TRUE) throw new MyException();
            return new BigDecimal(BigInteger.ZERO);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public BigDecimal functionTest() throws Exception {
        try {
            ExceptionTest exceptionTest = new ExceptionTest();
            BigDecimal decimal = exceptionTest.function();
            System.out.println(decimal);
        } catch (MyException e) {
            System.out.println("exception 1");
            throw e;
        } catch (Exception e) {
            System.out.println("exception 2");
            throw e;
        } finally {
            System.out.println("obrigatorio");
        }
        return null;
    }
    
    public class MyException extends Exception {
        
        public MyException() {
        }
        
        public MyException(String message, Throwable cause) {
            super(message, cause);
        }
        
    }
}
