package mytests;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTest {
    
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<String>(Arrays.asList("1","2","3","4","5"));
        List<String> list2 = new ArrayList<String>(Arrays.asList("1","2","3"));
        
        System.out.println("\u001B[34m \nRemoveAll Test \u001B[0m");
        list1.removeAll(list2);
        System.out.println(list1);
        
        
        System.out.println("\u001B[34m \nRetainAll Test \u001B[0m");
        list1 = new ArrayList<String>(Arrays.asList("1","2","3","4","5"));
        list1.retainAll(list2);
        System.out.println(list1);
        
        
        System.out.println("\u001B[34m \nCollectionUtils.subtract Test \u001B[0m");
        list1 = new ArrayList<String>(Arrays.asList("1","2","3","4","5"));
        System.out.println(CollectionUtils.subtract(list1, list2));
        
        
        System.out.println("\u001B[34m \nCollectionUtils.intersection Test \u001B[0m");
        list1 = new ArrayList<String>(Arrays.asList("1","2","3","4","5"));
        System.out.println(CollectionUtils.intersection(list1, list2));
    }
    
}
