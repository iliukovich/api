package vk.helpers;

import helpers.ApiHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import vk.models.photos.getWallUploadServer.PhotosGetWallUploadServerResponse;
import vk.models.photos.saveWallPhoto.SavedPictureResponse;
import vk.models.photos.uploadPicture.UploadedPictureResponse;
import vk.models.wall.comment.WallCommentResponse;
import vk.models.wall.likes.LikesResponse;
import vk.models.wall.post.WallPostResponse;
import vk.models.wall.user.UsersResponse;

import java.util.ArrayList;
import java.util.List;

public class VkApiHelper extends ApiHelper {

    private static final String WALL_POST_REQUEST = "https://api.vk.com/method/wall.post";
    private static final String WALL_EDIT_REQUEST = "https://api.vk.com/method/wall.edit";
    private static final String PHOTOS_GET_WALL_UPLOAD_SERVER_REQUEST = "https://api.vk.com/method/photos.getWallUploadServer";
    private static final String PHOTOS_SAVE_REQUEST = "https://api.vk.com/method/photos.saveWallPhoto";
    private static final String WALL_COMMENT_REQUEST = "https://api.vk.com/method/wall.createComment";
    private static final String LIKES_GET_REQUEST = "https://api.vk.com/method/likes.getList";
    private static final String WALL_DELETE_REQUEST = "https://api.vk.com/method/wall.delete";
    private static final String USERS_GET_REQUEST = "https://api.vk.com/method/users.get";

    public WallPostResponse sendWallPostRequest(String postText) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("message", postText));
        return mapToObject(sendPostRequest(WALL_POST_REQUEST, listOfParameters), WallPostResponse.class);
    }

    public void sendWallEditRequest(String postText, int postId, SavedPictureResponse savedPicture) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("message", postText));
        listOfParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        listOfParameters.add(new BasicNameValuePair("attachments", savedPicture.toString()));
        sendPostRequest(WALL_EDIT_REQUEST, listOfParameters);
    }

    public PhotosGetWallUploadServerResponse sendGetWallUploadServerRequest() {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        return mapToObject(sendPostRequest(PHOTOS_GET_WALL_UPLOAD_SERVER_REQUEST, listOfParameters), PhotosGetWallUploadServerResponse.class);
    }

    public WallCommentResponse sendWallCommentRequest(int postId, String commentText) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("message", commentText));
        listOfParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        return mapToObject(sendPostRequest(WALL_COMMENT_REQUEST, listOfParameters), WallCommentResponse.class);
    }

    public void sendWallDelete(int postId) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        sendPostRequest(WALL_DELETE_REQUEST, listOfParameters);
    }

    public LikesResponse sendPostLikesResponse(int postId) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("type", "post"));
        listOfParameters.add(new BasicNameValuePair("item_id", String.valueOf(postId)));
        return mapToObject(sendPostRequest(LIKES_GET_REQUEST, listOfParameters), LikesResponse.class);
    }

    public UsersResponse sendGetUserId() {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        return mapToObject(sendPostRequest(USERS_GET_REQUEST, listOfParameters), UsersResponse.class);
    }

    public UploadedPictureResponse sendPostUploadPicture(String uploadUrl, String filePath) {
        return mapToObject(postUploadPhoto(uploadUrl, filePath), UploadedPictureResponse.class);
    }

    public SavedPictureResponse sendSavePhotoRequest(UploadedPictureResponse uploadedPicture) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("photo", uploadedPicture.getPhoto()));
        listOfParameters.add(new BasicNameValuePair("hash", uploadedPicture.getHash()));
        listOfParameters.add(new BasicNameValuePair("server", String.valueOf(uploadedPicture.getServer())));
        return mapToObject(sendPostRequest(PHOTOS_SAVE_REQUEST, listOfParameters), SavedPictureResponse.class);
    }

    private List<NameValuePair> getListOfDefaultParameters() {
        List<NameValuePair> listOfParameters = new ArrayList<>();
        listOfParameters.add(new BasicNameValuePair("access_token", System.getProperty("token")));
        listOfParameters.add(new BasicNameValuePair("v", System.getProperty("apiVersion")));
        return listOfParameters;
    }
}
