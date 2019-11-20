package helpers;

import aquality.selenium.logger.Logger;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

public class CloudinaryHelper {

    public Map uploadScreenshot(byte[] image) {
        PropertiesResourceManager property = new PropertiesResourceManager("cloudinary.properties");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name" , property.getProperty("cloud.name"),
                "api_key", property.getProperty("api.key"),
                "api_secret", property.getProperty("api.secret")));

        return uploadFileToCloudinary(cloudinary, image);
    }

    public String getUploadedFileUrl(Map response) {
        return response.get("url").toString();
    }

    private Map uploadFileToCloudinary(Cloudinary cloudinary, byte[] image) {
        try {
            return cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
        } catch (IOException e) {
            Logger.getInstance().warn("IOException: " + e.getMessage());
            return null;
        }
    }
}
