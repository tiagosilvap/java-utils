package mytests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileTest {
    
    public static void escreverNoArquivo(String path, List<String> stringList) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        for (String string : stringList) {
            // Com aspas
            // String line = new StringBuilder().append("'").append(string).append("',").toString();
            
            // Sem aspas
            String line = new StringBuilder().append(string).append(",").toString();
            
            buffWrite.append(line + "\n");
        }
        buffWrite.close();
    }
    
    public static List<String> lerArquivo(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        List<String> stringList = new ArrayList<>();
        int count = 0;
        
        String linha = buffRead.readLine();
        while (true) {
            if (linha != null) {
                System.out.println(linha);
                if(!stringList.contains(linha)) {
                    stringList.add(linha);
                }
                count++;
            } else
                break;
            linha = buffRead.readLine();
        }
        buffRead.close();
        
        System.out.println("Total de linhas do arquivo: " + count);
        System.out.println("Total de linhas sem os repetidos: " + stringList.size());
        return stringList;
    }
    
    public static void main(String[] args) throws IOException {
        List<String> text = lerArquivo("/Users/tiago.pereira/Desktop/teste");
        escreverNoArquivo("/Users/tiago.pereira/Desktop/teste2", text);
    }
}
