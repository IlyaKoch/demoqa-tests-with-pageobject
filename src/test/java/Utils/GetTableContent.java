package Utils;

import com.codeborne.selenide.Selenide;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetTableContent {
    public static LinkedHashMap<String, String> getTableContentWithExecuteScript() {
        String jsCode = readTextFromFile("./src/test/resources/javascript/get_table_data.js");
        String browserResponse = Selenide.executeJavaScript(jsCode);
        System.out.println("browserResponse:\n" + browserResponse);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = null;
        try {
            data = mapper.readValue(browserResponse,
                    new TypeReference<>() {
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Actual data:\n" + StringUtils.join(data));

        return (LinkedHashMap<String, String>) data;
    }

    public static String readTextFromFile(String filePath) {
        String fileContent = null;
        try {
            fileContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File content:\n" + fileContent);

        return fileContent;
    }
}

//Original code
/*
    public static Map<String, String> getTableContentWithExecuteScript() {
        String jsCode = readTextFromFile("./src/test/resources/javascript/get_table_data.js");
        String browserResponse = Selenide.executeJavaScript(jsCode);
        System.out.println("browserResponse:\n" + browserResponse);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = null;
        try {
            data = mapper.readValue(browserResponse,
                    new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Actual data:\n" + StringUtils.join(data));

        return data;
    }

    public static String readTextFromFile(String filePath) {
        String fileContent = null;
        try {
            fileContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File content:\n" + fileContent);

        return fileContent;
    }
 */