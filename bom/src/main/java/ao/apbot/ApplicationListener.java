package ao.apbot;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

public class ApplicationListener implements ServletContextListener {

    private static Logger log = LoggerFactory.getLogger(ApplicationListener.class);

    private static final String CONFIG = "config.properties";

    private static final String CONFIG_BOT_NAMES = "bot.names";
    private static final String CONFIG_USERNAME = ".character.username";
    private static final String CONFIG_PASSWORD = ".character.password";

    private static AoChatBot botFactory = new AoChatBot();

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            Properties prop = new Properties();
            try (InputStream file = new FileInputStream(CONFIG)) {
                prop.load(file);
                for (String handle : Splitter.on(',').split(prop.getProperty(CONFIG_BOT_NAMES))) {
                    handle = handle.trim();
                    botFactory.spawn(handle, prop.getProperty(handle + CONFIG_USERNAME).trim(), prop.getProperty(handle + CONFIG_PASSWORD).trim());
                }
            }
        } catch (Exception x) {
            log.error("Failed to start apbot", x);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
    }
}
