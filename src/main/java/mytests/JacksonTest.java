package mytests;

import com.amazonaws.util.json.Jackson;
import org.apache.commons.codec.binary.Base64;

public class JacksonTest {
    
    public static void main(String[] args) {
        Aluno aluno = new Aluno(1L, "Tiago");
        final String rawObjectBase64 = buildRawObjectProperty(aluno);
        System.out.println(rawObjectBase64);
        final String rawObject = new String(Base64.decodeBase64(rawObjectBase64));
        System.out.println(rawObject);
        aluno = (Aluno) toObject(rawObject, aluno);
        System.out.println(aluno);
    }
    
    public static String buildRawObjectProperty(Object object) {
        try {
            return Base64.encodeBase64String(Jackson.toJsonString(object).getBytes());
        } catch (Exception var4) {
            System.err.println("Error to generate transaction log raw object. " + var4);
        }
        return null;
    }
    
    public static Object toObject(String rawObject, Object eventObject) {
        try {
            return Jackson.fromJsonString(rawObject, eventObject.getClass());
        } catch (Exception e) {
            return eventObject;
        }
    }
    
    public static class Aluno {
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
