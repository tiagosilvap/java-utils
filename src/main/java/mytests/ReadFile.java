package mytests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public static List<String> removeElementosRepetidos(List<String> rows) {
        List<String> aux = new ArrayList<String>();

        for(int i = 0;  i < rows.size(); i++) {

            if(aux.isEmpty()) {
                aux.add(rows.get(i));
            } else {
                int count = 0;
                for(String a :aux) {
                    if(rows.get(i).split(",")[0].equalsIgnoreCase(a.split(",")[0])) {
//                    if(rows.get(i).equalsIgnoreCase(a)) {
                        count++;
                    }
                }
                if(count == 0){
                    aux.add(rows.get(i));
                }
            }
        }
        return aux;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("/Users/tiago.pereira/backend/teste/src/mytests/text.txt"));
        List<String> rows = new ArrayList<>();

        while(br.ready()){
            String linha = br.readLine();
            rows.add(linha);
        }
        br.close();

        List<String> semRepetidos = removeElementosRepetidos(rows);

        semRepetidos.forEach(System.out::println);

    }
}
