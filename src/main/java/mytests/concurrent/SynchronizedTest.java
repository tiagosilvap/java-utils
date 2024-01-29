package mytests.concurrent;

public class SynchronizedTest {
    
    public static void main(String[] args) {
        Contador contador = new Contador();
        
        // Criando duas threads que incrementam o contador
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                contador.incrementar();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                contador.incrementar();
            }
        });
        
        // Iniciando as threads
        thread1.start();
        thread2.start();
        
        // Aguardando as threads terminarem
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Exibindo o valor final do contador
        System.out.println("Valor final do contador: " + contador.getValor());
    }
    
    public static class Contador {
        private int valor = 0;
        
        public int getValor() {
            return valor;
        }
        
        public synchronized void incrementar() {
            // Código crítico que precisa ser executado por uma única thread por vez
            valor++;
        }
    }
    
}
