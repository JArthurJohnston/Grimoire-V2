package com.paratussoftware.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {

    private static Properties properties;

    public static String get(String propertyName){
        return loadProperties().getProperty(propertyName);
    }

    public static void set(String propertyName, String value){
        loadProperties().setProperty(propertyName, value);
    }

    private static Properties loadProperties(){
        if(properties == null){
            properties = new Properties();
            try {
                FileInputStream fileInputStream = new FileInputStream("./res/aether.properties");
                properties.load(fileInputStream);
            } catch (IOException e) {
            }
        }
        return properties;
    }

    public int imageWidth(){
        return 0;
    }
}
