package helpers;

import aquality.selenium.logger.Logger;
import org.apache.commons.io.FileUtils;

import java.io.*;
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

    public static File convertByteArrayIntoTheFile(byte[] array, String path) {
        File file = new File(path);
        try {
            FileUtils.writeByteArrayToFile(file, array);
            return file;
        } catch (IOException e) {
            Logger.getInstance().warn("IOException: " + e.getMessage());
            return null;
        }
    }
}
