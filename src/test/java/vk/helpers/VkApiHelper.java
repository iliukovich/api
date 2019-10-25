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
    private List<NameValuePair> listOfDefaultParameters = getListOfDefaultParameters();

    public WallPostResponse sendWallPostRequest(String postText) {
        listOfDefaultParameters.add(new BasicNameValuePair("message", postText));
        return mapToObject(sendPostRequest(WALL_POST_REQUEST, listOfDefaultParameters), WallPostResponse.class);
    }

    public void sendWallEditRequest(String postText, int postId, SavedPictureResponse savedPicture) {
        listOfDefaultParameters.add(new BasicNameValuePair("message", postText));
        listOfDefaultParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        listOfDefaultParameters.add(new BasicNameValuePair("attachments", savedPicture.toString()));
        sendPostRequest(WALL_EDIT_REQUEST, listOfDefaultParameters);
    }

    public PhotosGetWallUploadServerResponse sendGetWallUploadServerRequest() {
        return mapToObject(sendPostRequest(PHOTOS_GET_WALL_UPLOAD_SERVER_REQUEST, listOfDefaultParameters), PhotosGetWallUploadServerResponse.class);
    }

    public WallCommentResponse sendWallCommentRequest(int postId, String commentText) {
        listOfDefaultParameters.add(new BasicNameValuePair("message", commentText));
        listOfDefaultParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        return mapToObject(sendPostRequest(WALL_COMMENT_REQUEST, listOfDefaultParameters), WallCommentResponse.class);
    }

    public void sendWallDelete(int postId) {
        listOfDefaultParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        sendPostRequest(WALL_DELETE_REQUEST, listOfDefaultParameters);
    }

    public LikesResponse sendPostLikesResponse(int postId) {
        listOfDefaultParameters.add(new BasicNameValuePair("type", "post"));
        listOfDefaultParameters.add(new BasicNameValuePair("item_id", String.valueOf(postId)));
        return mapToObject(sendPostRequest(LIKES_GET_REQUEST, listOfDefaultParameters), LikesResponse.class);
    }

    public UsersResponse sendGetUserId() {
        return mapToObject(sendPostRequest(USERS_GET_REQUEST, listOfDefaultParameters), UsersResponse.class);
    }

    public UploadedPictureResponse sendPostUploadPicture(String uploadUrl, String filePath) {
        return mapToObject(postUploadPhoto(uploadUrl, filePath), UploadedPictureResponse.class);
    }

    public SavedPictureResponse sendSavePhotoRequest(UploadedPictureResponse uploadedPicture) {
        listOfDefaultParameters.add(new BasicNameValuePair("photo", uploadedPicture.getPhoto()));
        listOfDefaultParameters.add(new BasicNameValuePair("hash", uploadedPicture.getHash()));
        listOfDefaultParameters.add(new BasicNameValuePair("server", String.valueOf(uploadedPicture.getServer())));
        return mapToObject(sendPostRequest(PHOTOS_SAVE_REQUEST, listOfDefaultParameters), SavedPictureResponse.class);
    }

    private List<NameValuePair> getListOfDefaultParameters() {
        List<NameValuePair> listOfParameters = new ArrayList<>();
        listOfParameters.add(new BasicNameValuePair("access_token", "3e45b6cfb21cf8e9f3b2c67f50c16d69f1fc16b7f06f33763051fd662c923498f6bb07ad7ee865e4c0fa2"));
        listOfParameters.add(new BasicNameValuePair("v", "5.102"));
        return listOfParameters;
    }
}
