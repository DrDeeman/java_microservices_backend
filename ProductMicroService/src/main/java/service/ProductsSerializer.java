package service;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import entity.eProducts;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ProductsSerializer extends JsonSerializer<eProducts> {

    @Value("${app.images}")
    private String path;


    @Override
    public void serialize(eProducts product, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(product.getId()));
        jsonGenerator.writeStringField("name", product.getName());
        jsonGenerator.writeStringField("price",String.valueOf(product.getPrice()));
        jsonGenerator.writeStringField("year_issue",new SimpleDateFormat("dd-MM-yyyy").format(product.getYear_issue()));
        jsonGenerator.writeStringField("raiting",String.valueOf(product.getRaiting()));

        String path_to_image = this.getClass().getResource(path + product.getPath_image()).getPath();
        String encodedString = Base64.getEncoder().encodeToString(
        FileUtils.readFileToByteArray(new File(path_to_image))
        );

        jsonGenerator.writeStringField("image",encodedString);
        jsonGenerator.writeEndObject();
    }

}
