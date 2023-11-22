package mytests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class Java8 {
    
    public static void main(String[] args) {
        Java8 java8 = new Java8();
        java8.flatMapTest();
        java8.convertArrayToMapTest();
        java8.sortedTest();
        java8.intersectionOfListsTest();
        java8.removeDuplicatedElements();
        java8.stringJoinTest();
        java8.sortAndConcatenateString();
        java8.sumArrayElementsByReduce();
        java8.sumBigDecimalsByReduce();
    }
    
    public void flatMapTest() {
        System.out.println("\u001B[34m \nFlatMap \u001B[0m");
        List<List<String>> names = Arrays.asList(
                Arrays.asList("Tiago", "Brito", "Arnold"),
                Arrays.asList("Adrienne", "Neide", "Juliana")
        );
        
        List<String> namesList = names.stream()
                .flatMap(n -> n.stream())
                .filter(n -> n.equals("Tiago") || n.equals("Adrienne"))
                .collect(Collectors.toList());
        System.out.println("Namelist: " + namesList);
    }
    
    public void convertArrayToMapTest() {
        System.out.println("\u001B[34m \nConvert Array to Map \u001B[0m");
        Collection<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno(1L, "Tiago"));
        alunos.add(new Aluno( "Adrienne"));
        alunos.add(new Aluno("Brito"));
        alunos.add(new Aluno("Neide"));
        alunos.add(new Aluno(5L, "Juliana"));
        Map<Boolean, List<Aluno>> classifiedElements = alunos.stream().collect(Collectors.partitioningBy(e -> e.getId() != null));
        System.out.println("Alunos com ID: " + classifiedElements.get(true) + "\nAlunos sem ID: " + classifiedElements.get(false));
    }
    
    public void sortedTest() {
        System.out.println("\u001B[34m \nSorted Test \u001B[0m");
        List<Aluno> users = Arrays.asList(
                new Aluno(3L, "Tiago"),
                new Aluno(1L, "Adrienne"),
                new Aluno(2L, "Brito")
        );
        
        List<Aluno> sortedUsers = users.stream()
                .sorted((Comparator.comparing(Aluno::getId)))
                .collect(Collectors.toList());
        
        System.out.println(sortedUsers);
    }
    
    public void intersectionOfListsTest() {
        System.out.println("\u001B[34m \nIntersection Of Lists Test \u001B[0m");
        List<String> list1 = new ArrayList<String>(Arrays.asList("1","2","3","4","5"));
        List<String> list2 = new ArrayList<String>(Arrays.asList("1","2","3"));
        System.out.println(list1.stream().filter(list2 :: contains).collect(Collectors.toList()));
    }
    
    public void removeDuplicatedElements() {
        System.out.println("\u001B[34m \nRemove Duplicated Elements\u001B[0m");
        List<Aluno> list = Arrays.asList(
                new Aluno(1L, "Tiago"),
                new Aluno(1L, "Tiago"),
                new Aluno(2L, "Neide"),
                new Aluno(2L, "Neide"),
                new Aluno(3L, "Juliana")
        );
        list = list.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Aluno::getId))), ArrayList::new));
        System.out.println(list);
    }
    
    public void stringJoinTest() {
        System.out.println("\u001B[34m \nString Join Test \u001B[0m");
        
        String SPLIT_CHAR = ", ";
        List<Aluno> users = Arrays.asList(
                new Aluno(3L, "Tiago, Silva, Pereira"),
                new Aluno(1L, "Adrienne, Nogueira"),
                new Aluno(2L, "Brito, Leopoldo")
        );
        
        Set<String> setList = users.stream()
                .map(user -> String.join(SPLIT_CHAR, user.getNome()))
                .collect(Collectors.toSet());
        
        System.out.println(setList);
    }
    
    public void sortAndConcatenateString() {
        System.out.println("\u001B[34m \nSort and concatenate string \u001B[0m");
        String[] array = "2,1,3".split(",");
        Arrays.sort(array, (s1, s2) -> Long.valueOf(s1).compareTo(Long.valueOf(s2)));
        System.out.println(Arrays.stream(array).collect(Collectors.joining("ª, ")).concat("ª"));
    }
    
    public void sumArrayElementsByReduce() {
        System.out.println("\u001B[34m \nSum Array Elements By Reduce \u001B[0m");
        String[] names = {"Tiago", "Drica", "Juliana"};
        Stream<String> stream = Stream.of("Tiago", "Tiago", "Drica", "Neide");
        System.out.println(stream.filter(t -> Arrays.asList(names).contains(t)).collect(Collectors.reducing(0, e -> 1, Integer::sum)));
    }
    
    public void sumBigDecimalsByReduce() {
        System.out.println("\u001B[34m \nSum BigDecimals By Reduce \u001B[0m");
        List<BigDecimal> values = Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(50));
        System.out.println(values.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }
    
    public class Aluno {
        private Long id;
        private String nome;
        
        public Aluno(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }
        
        public Aluno(Long id) {
            this.id = id;
        }
        
        public Aluno(String nome) {
            this.nome = nome;
        }
        
        public Long getId() {
            return id;
        }
        
        public String getNome() {
            return nome;
        }
        
        @Override
        public String toString() {
            return "Aluno{" +
                    "id=" + id +
                    ", nome='" + nome + '\'' +
                    '}';
        }
    }
}
