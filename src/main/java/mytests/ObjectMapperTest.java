package mytests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.io.IOException;

public class ObjectMapperTest {

    public static void main(String[] args) throws IOException {
        ObjectMapperTest objectMapperTest = new ObjectMapperTest();
        objectMapperTest.generateJsonSchema();
    }

    private void generateJsonSchema() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        com.fasterxml.jackson.module.jsonSchema.JsonSchema schema = schemaGen.generateSchema(Java8.Aluno.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
    }
}
