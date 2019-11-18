package vk.helpers;

import aquality.selenium.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static boolean isSimilarToBaseImage(File firstFile, File secondFile) {
        BufferedImage firstImage = getImageFromFile(firstFile);
        BufferedImage secondImage = getImageFromFile(secondFile);

        if ((firstImage.getWidth() != secondImage.getWidth()) || (firstImage.getHeight() != secondImage.getHeight())) {
            return false;
        } else {
            long maxDifference = 3L * 255 * firstImage.getWidth() * firstImage.getHeight();
            double p = 100.0 * comparePictures(firstImage, secondImage) / maxDifference;
            return p <= maxDifference;
        }
    }

    private static BufferedImage getImageFromFile(File imageFile) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            Logger.getInstance().warn("IOException: " + e.getMessage());
        }
        return image;
    }

    private static double comparePictures(BufferedImage firstImage, BufferedImage secondImage) {
        long difference = 0;
        for (int y = 0; y < firstImage.getHeight(); y++) {
            for (int x = 0; x < firstImage.getWidth(); x++) {
                int rgbA = firstImage.getRGB(x, y);
                int rgbB = secondImage.getRGB(x, y);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;
                difference += Math.abs(redA - redB);
                difference += Math.abs(greenA - greenB);
                difference += Math.abs(blueA - blueB);
            }
        }
        return difference;
    }
}