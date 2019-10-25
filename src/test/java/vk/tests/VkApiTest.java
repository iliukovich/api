package vk.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import vk.models.photos.saveWallPhoto.SavedPictureResponse;
import vk.steps.LoginPageSteps;
import vk.steps.MyProfilePageSteps;

public class VkApiTest extends BaseTest {

    private static final String RANDOM_TEXT = RandomStringUtils.randomAlphanumeric(10);
    private static final String RANDOM_TEXT_FOR_EDIT = RandomStringUtils.randomAlphanumeric(15);
    private static final String RANDOM_TEXT_FOR_COMMENT = RandomStringUtils.randomAlphanumeric(5);
    private static final String PICTURE_PATH = "src/test/resources/ZPo7dpYPS-g.jpg";
    private static final String DOWNLOADED_PICTURE_NAME = "target/actualImage.jpg";
    private MyProfilePageSteps profilePageSteps = new MyProfilePageSteps();

    @Test(dataProvider = "userData", dataProviderClass = DataDrivenLogIn.class)
    public void checkLogin(String email, String passwd) {
        LoginPageSteps.login(email, passwd);

        profilePageSteps.openMyProfilePage();

        int postId = profilePageSteps.createPost(RANDOM_TEXT);
        profilePageSteps.assertCreatedPostIsDisplayed(postId);
        profilePageSteps.assertText(postId, RANDOM_TEXT);
        profilePageSteps.assertAuthor(postId);

        SavedPictureResponse savedPicture = profilePageSteps.editPostTextAndAttachment(RANDOM_TEXT_FOR_EDIT, postId, PICTURE_PATH);
        profilePageSteps.assertText(postId, RANDOM_TEXT_FOR_EDIT);
        profilePageSteps.assertPictureIsDisplayed(postId, savedPicture);
        profilePageSteps.assertPictureIsTheSameAsUploaded(savedPicture, PICTURE_PATH, DOWNLOADED_PICTURE_NAME);

        int commentId = profilePageSteps.addCommentToPost(postId, RANDOM_TEXT_FOR_COMMENT);
        profilePageSteps.extendComments(postId);
        profilePageSteps.assertText(commentId, RANDOM_TEXT_FOR_COMMENT);
        profilePageSteps.assertAuthor(commentId);

        profilePageSteps.likePost(postId);
        profilePageSteps.assertLikeAuthor(postId);

        profilePageSteps.deletePost(postId);
        profilePageSteps.assertPostDeletion(postId);
    }
}
