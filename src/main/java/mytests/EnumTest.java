package mytests;

public class EnumTest {
    
    public static void main(String[] args) {
        Enum test = Enum.valueOf("FREE");
        System.out.println(test);
    }
    
    public enum Enum {
        FREE ("free"),
        PAID ("paid"),
        TASTING ("tasting");
        
        String value;
        
        Enum (String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
}
