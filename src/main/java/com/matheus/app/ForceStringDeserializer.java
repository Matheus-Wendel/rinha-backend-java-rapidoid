package com.matheus.app;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ForceStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!JsonToken.VALUE_STRING.equals(jsonParser.getCurrentToken())) {

            throw new IllegalArgumentException("erro-string");
            // throw new JsonMappingException(jsonParser, "Erro ao converter, valor numerico em campo de string");
        } else {
            return jsonParser.getValueAsString();
        }
    }
}