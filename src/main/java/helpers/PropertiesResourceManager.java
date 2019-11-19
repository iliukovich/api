package helpers;

import aquality.selenium.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesResourceManager {
    private Properties properties;

    public PropertiesResourceManager(String resourceName) {
        this.properties = new Properties();
        this.properties = this.appendFromResource(this.properties, resourceName);
    }

    private Properties appendFromResource(Properties objProperties, String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                Logger.getInstance().warn("IOException: " + e.getMessage());
            }
        } else {
            System.out.println(String.format("Resource \"%1$s\" could not be found", resourceName));
        }

        return objProperties;
    }

    public String getProperty(String key) {
        return System.getProperty(key, this.properties.getProperty(key));
    }
}
