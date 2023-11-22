package mytests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    public static void main(String[] args) throws Exception {
        ThreadTest threadTest = new ThreadTest();
//        threadTest.scheduledExecutorServiceTest();
        threadTest.parallelStreamTest();
        
    }
    
    public void scheduledExecutorServiceTest() {
        System.out.println("\u001B[34m \nScheduled Executor Service Test \u001B[0m");
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(3);
        execService.scheduleAtFixedRate(()-> {
            //The repetitive task
            System.out.println(new java.util.Date() + " Thread: " + Thread.currentThread().getName());
        }, 0, 500L, TimeUnit.MILLISECONDS);
    }
    
    public void parallelStreamTest() throws ExecutionException, InterruptedException {
        
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        
        System.out.println("\u001B[34m \nParallelStream with ForkJoinPool \u001B[0m");
        ForkJoinPool customThreadPool = new ForkJoinPool(3);
        customThreadPool.submit(() ->
                listOfNumbers.parallelStream().forEach(number -> System.out.println("Parallel Stream " + number + " " + Thread.currentThread().getName()))
        ).get();
        customThreadPool.shutdown();
        
        
        
        System.out.println("\u001B[34m \nParallelStream without ForkJoinPool \u001B[0m");
        listOfNumbers.parallelStream().forEach(number ->
                System.out.println("Parallel Stream " + number + " " + Thread.currentThread().getName())
        );
        
        
        
        System.out.println("\u001B[34m \nDefault stream \u001B[0m");
        listOfNumbers.stream().forEach(number ->
                System.out.println(number + " " + Thread.currentThread().getName())
        );
        
        
        
        System.out.println("\u001B[34m \nReduce with parallelStream \u001B[0m");
        int sum = listOfNumbers.parallelStream().reduce(5, Integer::sum);
        System.out.println(sum);
        
        
        
        System.out.println("\u001B[34m \nReduce with default stream \u001B[0m");
        sum = listOfNumbers.stream().reduce(5, Integer::sum);
        System.out.println(sum);
    }
}
