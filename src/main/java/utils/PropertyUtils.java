package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
    public static String getProperty(String key) {
        Properties props = new Properties();

        InputStream is = ClassLoader.getSystemResourceAsStream("project.properties");
        try {
            props.load(is);
        }
        catch (IOException e) {
            // Handle exception here
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String envReplacementKey) {

        String value = getProperty(key);

        if (value.equals("ENV_VARIABLE")) {
            return System.getProperty(envReplacementKey);
        } else {
            return value;
        }
    }
}


