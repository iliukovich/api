package vk.enums;

public enum ImageType {

    ORIGINAL_SIZE("w");

    private String imageSize;

    ImageType(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageSize() {
        return imageSize;
    }
}
