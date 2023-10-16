package org.graphic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConfig {
    private static final String CONFIG_FILE = "src/main/resources/org/graphic/config.properties";

    private final Properties properties;

    public AppConfig() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(AppConfig.class.getName());
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    public String getAppName() {
        return properties.getProperty("app.name");
    }

    public String getAppVersion() {
        return properties.getProperty("app.version");
    }

    public String getAppAuthor() {
        return properties.getProperty("app.author");
    }

    public String getDBHost() {
        return properties.getProperty("db.host");
    }

    public String getDBName() {
        return properties.getProperty("db.name");
    }

    public String getDBUser() {
        return properties.getProperty("db.user");
    }

    public String getDBPass() {
        return properties.getProperty("db.pass");
    }

    public String getDBPort() {
        return properties.getProperty("db.port");
    }

    public int getUIWidth() {
        return Integer.parseInt(properties.getProperty("ui.width"));
    }

    public int getUIHeight() {
        return Integer.parseInt(properties.getProperty("ui.height"));
    }

    public boolean getUIFullscreen() {
        return Boolean.parseBoolean(properties.getProperty("ui.fullscreen"));
    }
}
