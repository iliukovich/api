package vk.helpers;

import helpers.ApiHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import vk.enums.ApiMethod;
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

    public WallPostResponse sendWallPostRequest(String postText) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("message", postText));
        return mapToObject(sendPostRequest(ApiMethod.WALL_POST.getApiRequest(), listOfParameters), WallPostResponse.class);
    }

    public void sendWallEditRequest(String postText, int postId, SavedPictureResponse savedPicture) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("message", postText));
        listOfParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        listOfParameters.add(new BasicNameValuePair("attachments", savedPicture.toString()));
        sendPostRequest(ApiMethod.WALL_EDIT.getApiRequest(), listOfParameters);
    }

    public PhotosGetWallUploadServerResponse sendGetWallUploadServerRequest() {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        return mapToObject(sendPostRequest(ApiMethod.PHOTOS_GET_WALL_UPLOAD_SERVER.getApiRequest(), listOfParameters), PhotosGetWallUploadServerResponse.class);
    }

    public WallCommentResponse sendWallCommentRequest(int postId, String commentText) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("message", commentText));
        listOfParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        return mapToObject(sendPostRequest(ApiMethod.WALL_CREATE_COMMENT.getApiRequest(), listOfParameters), WallCommentResponse.class);
    }

    public void sendWallDelete(int postId) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        sendPostRequest(ApiMethod.WALL_DELETE.getApiRequest(), listOfParameters);
    }

    public LikesResponse sendPostLikesResponse(int postId) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("type", "post"));
        listOfParameters.add(new BasicNameValuePair("item_id", String.valueOf(postId)));
        return mapToObject(sendPostRequest(ApiMethod.LIKES_GET_LIST.getApiRequest(), listOfParameters), LikesResponse.class);
    }

    public UsersResponse sendGetUserId() {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        return mapToObject(sendPostRequest(ApiMethod.USERS_GET.getApiRequest(), listOfParameters), UsersResponse.class);
    }

    public UploadedPictureResponse sendPostUploadPicture(String uploadUrl, String filePath) {
        return mapToObject(postUploadPhoto(uploadUrl, filePath), UploadedPictureResponse.class);
    }

    public SavedPictureResponse sendSavePhotoRequest(UploadedPictureResponse uploadedPicture) {
        List<NameValuePair> listOfParameters = getListOfDefaultParameters();
        listOfParameters.add(new BasicNameValuePair("photo", uploadedPicture.getPhoto()));
        listOfParameters.add(new BasicNameValuePair("hash", uploadedPicture.getHash()));
        listOfParameters.add(new BasicNameValuePair("server", String.valueOf(uploadedPicture.getServer())));
        return mapToObject(sendPostRequest(ApiMethod.PHOTOS_SAVE_WALL_PHOTO.getApiRequest(), listOfParameters), SavedPictureResponse.class);
    }

    private List<NameValuePair> getListOfDefaultParameters() {
        List<NameValuePair> listOfParameters = new ArrayList<>();
//        listOfParameters.add(new BasicNameValuePair("access_token", System.getProperty("token")));
//        listOfParameters.add(new BasicNameValuePair("v", System.getProperty("apiVersion")));
        listOfParameters.add(new BasicNameValuePair("access_token", "f6c54131bd9266a002bab94282e627094cbfe68e6976d33d53ed79af2960b77289ea3abb4978faddf73af"));
        listOfParameters.add(new BasicNameValuePair("v", "5.102"));
        return listOfParameters;
    }
}
