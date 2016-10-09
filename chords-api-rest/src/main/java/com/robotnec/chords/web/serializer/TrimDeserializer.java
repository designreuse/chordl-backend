package com.robotnec.chords.web.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

class TrimDeserializer extends JsonDeserializer<String> {

     @Override
     public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
         return parser.readValueAs(String.class).trim();
     }
 }