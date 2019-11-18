package vk.enums;

public enum ApiMethod {
    WALL_POST("wall.post"),
    WALL_EDIT("wall.edit"),
    PHOTOS_GET_WALL_UPLOAD_SERVER("photos.getWallUploadServer"),
    PHOTOS_SAVE_WALL_PHOTO("photos.saveWallPhoto"),
    WALL_CREATE_COMMENT("wall.createComment"),
    LIKES_GET_LIST("likes.getList"),
    WALL_DELETE("wall.delete"),
    USERS_GET("users.get");

    private static final String VK_API_REQUEST_COMMON = "https://api.vk.com/method/";
    private String apiMethod;

    ApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getApiRequest() {
        return VK_API_REQUEST_COMMON + apiMethod;
    }
}
