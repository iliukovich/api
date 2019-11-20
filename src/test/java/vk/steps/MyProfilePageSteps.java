package vk.steps;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import vk.enums.ImageType;
import vk.enums.MainMenuItem;
import vk.forms.ProfileWall;
import helpers.ImageUtils;
import vk.helpers.VkApiHelper;
import vk.menus.MainMenu;
import vk.models.photos.getWallUploadServer.PhotosGetWallUploadServerResponse;
import vk.models.photos.saveWallPhoto.SavedPictureResponse;
import vk.models.photos.saveWallPhoto.Size;
import vk.models.photos.uploadPicture.UploadedPictureResponse;
import vk.models.wall.comment.WallCommentResponse;
import vk.models.wall.likes.LikesResponse;
import vk.models.wall.post.WallPostResponse;
import vk.models.wall.user.UsersResponse;

import java.io.File;

import static helpers.FileHelpers.downloadFileByUrl;

public class MyProfilePageSteps {

    private ProfileWall profileWall = new ProfileWall();
    private MainMenu mainMenu = new MainMenu();
    private VkApiHelper vkApiHelper = new VkApiHelper();

    public void openMyProfilePage(MainMenuItem mainMenuItem) {
        mainMenu.clickItem(mainMenuItem);
    }

    public int createPost(String randomText) {
        WallPostResponse wallPostResponse = vkApiHelper.sendWallPostRequest(randomText);
        return wallPostResponse.getResponse().getPostId();
    }

    public void assertCreatedPostIsDisplayed(int postId) {
        Assert.assertTrue(profileWall.isWallPostDisplayed(postId), String.format("Profile post %s is not displayed", postId));
    }

    public void assertText(int itemId, String randomText) {
        SoftAssert assertText = new SoftAssert();
        assertText.assertEquals(profileWall.getTextFromPostContent(itemId), randomText, "Text isn't equal to expected");
    }

    public void assertAuthor(int itemId) {
        SoftAssert assertAuthor = new SoftAssert();
        assertAuthor.assertEquals(profileWall.getWallPostAuthorId(itemId), profileWall.getUser(), "Author is not equal to expected");
    }

    public SavedPictureResponse editPostTextAndAttachment(String randomText, int postId, String picturePath) {
        SavedPictureResponse savedPicture = savePicture(picturePath);
        vkApiHelper.sendWallEditRequest(randomText, postId, savedPicture);
        return savedPicture;
    }

    public void assertPictureIsDisplayed(int postId, SavedPictureResponse savedPicture) {
        Assert.assertTrue(profileWall.isPhotoDisplayed(postId, savedPicture), "Photo isn't displayed");
    }

    public void assertPictureIsTheSameAsUploaded(SavedPictureResponse savedPicture, String picturePath, String fileName) {
        SoftAssert assertImages = new SoftAssert();
        assertImages.assertTrue(ImageUtils.isSimilarToBaseImage(new File(picturePath), new File(downloadFileByUrl(getImageUrl(savedPicture), fileName))), "Images are not equal");
        assertImages.assertAll("Images are not equal");
    }

    public int addCommentToPost(int postId, String randomText) {
        WallCommentResponse wallCommentResponse = vkApiHelper.sendWallCommentRequest(postId, randomText);
        return wallCommentResponse.getResponse().getCommentId();
    }

    public void extendComments(int postId) {
        profileWall.extendComments(postId);
    }

    public void likePost(int postId) {
        profileWall.clickLikeButton(postId);
    }

    public void assertLikeAuthor(int postId) {
        LikesResponse likesResponse = vkApiHelper.sendPostLikesResponse(postId);
        SoftAssert assertLikeAuthor = new SoftAssert();
        assertLikeAuthor.assertTrue(likesResponse.getResponse().getItems().contains(getCurrentUserId()), "User isn't included into the list of likers");
    }

    public void deletePost(int postId) {
        vkApiHelper.sendWallDelete(postId);
    }

    public void assertPostDeletion(int postId) {
        Assert.assertTrue(profileWall.isWallPostNotDisplayed(postId), "Post is still displayed");
    }

    private int getCurrentUserId() {
        UsersResponse usersResponse = vkApiHelper.sendGetUserId();
        return usersResponse.getResponse().get(0).getId();
    }

    private String getPictureUploadUrl() {
        PhotosGetWallUploadServerResponse photosGetWallUploadServerResponse = vkApiHelper.sendGetWallUploadServerRequest();
        return photosGetWallUploadServerResponse.getResponse().getUploadUrl();
    }

    private UploadedPictureResponse uploadPictureOnServer(String picturePath) {
        return vkApiHelper.sendPostUploadPicture(getPictureUploadUrl(), picturePath);
    }

    private SavedPictureResponse savePicture(String picturePath) {
        return vkApiHelper.sendSavePhotoRequest(uploadPictureOnServer(picturePath));
    }

    private String getImageUrl(SavedPictureResponse savedPicture) {
        for (Size size : savedPicture.getResponse().get(0).getSizes()) {
            if (size.getType().equals(ImageType.ORIGINAL_SIZE.getImageSize())) {
                return size.getUrl();
            }
        }
        throw new AssertionError(String.format("Image with type %s isn't found in response", ImageType.ORIGINAL_SIZE.getImageSize()));
    }
}
