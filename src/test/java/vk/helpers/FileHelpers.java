package vk.helpers;

import aquality.selenium.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHelpers {
    public static String downloadFileByUrl(String url, String fileName) {
        try (InputStream in = new URL(url).openStream()) {
            Path path = Paths.get(fileName);
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e) {
            Logger.getInstance().warn("IOException: " + e.getMessage());
            return null;
        }
    }
}
