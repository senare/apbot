package ao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import ao.apbot.AoChatBot;

import com.google.common.base.Splitter;

public class Main {

    private static final String CONFIG = "config.properties";

    private static final String CONFIG_BOT_NAMES = "bot.names";
    private static final String CONFIG_USERNAME = ".character.username";
    private static final String CONFIG_PASSWORD = ".character.password";

    private static AoChatBot botFactory = new AoChatBot();

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        try (InputStream file = new FileInputStream(CONFIG)) {
            prop.load(file);
            for (String handle : Splitter.on(',').split(prop.getProperty(CONFIG_BOT_NAMES))) {
                handle = handle.trim();
                botFactory.spawn(handle, prop.getProperty(handle + CONFIG_USERNAME).trim(), prop.getProperty(handle + CONFIG_PASSWORD).trim());
            }
        }
    }
}