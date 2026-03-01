package id.web.saka.fountation.util.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Named("stringToJson")
    public JsonNode stringToJson(String json) {
        try {
            return json == null || json.isEmpty() ? objectMapper.createObjectNode() : objectMapper.readTree(json);
        } catch (IOException e) {
            return objectMapper.createObjectNode();
        }
    }

    @Named("jsonToString")
    public String jsonToString(JsonNode node) {
        return node == null ? "{}" : node.toString();
    }
}
