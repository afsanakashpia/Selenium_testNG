package Utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class utils {
    public static int generateRandomNumber(int min, int max){
        double random= Math.random()*(max-min)+min;
        return (int)random;
    }
    public  static void setEnvVar(String key,Object value) throws ConfigurationException, IOException {
        File file = new File("src/test/resources/config.properties").getCanonicalFile();
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        config.setProperty(key,value);
        config.save();
    }
    public static void saveData(String filePath, JSONObject jsonObject) throws IOException, ParseException  {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader(filePath));
        jsonArray.add(jsonObject);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonArray.toJSONString()); // Properly formatted JSON string
        fileWriter.flush();
        fileWriter.close();

    }
}
