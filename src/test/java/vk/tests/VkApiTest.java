package vk.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import vk.enums.MainMenuItem;
import vk.models.photos.saveWallPhoto.SavedPictureResponse;
import vk.steps.LoginPageSteps;
import vk.steps.MyProfilePageSteps;
import testrail.helpers.TestInfo;

@TestInfo(id = 1)
public class VkApiTest extends BaseTest {

    private static final String RANDOM_TEXT = RandomStringUtils.randomAlphanumeric(10);
    private static final String RANDOM_TEXT_FOR_EDIT = RandomStringUtils.randomAlphanumeric(15);
    private static final String RANDOM_TEXT_FOR_COMMENT = RandomStringUtils.randomAlphanumeric(5);
    private static final String PICTURE_PATH = "src/test/resources/ZPo7dpYPS-g.jpg";
    private static final String DOWNLOADED_PICTURE_NAME = "target/actualImage.jpg";
    private MyProfilePageSteps profilePageSteps = new MyProfilePageSteps();

    @Test(dataProvider = "userData", dataProviderClass = DataDrivenLogIn.class)
    public void checkLogin(String email, String passwd) {
        logger.info("Login on site");
        LoginPageSteps.login(email, passwd);

        logger.info(String.format("Open %s page", MainMenuItem.MY_PROFILE.getMenuItem()));
        profilePageSteps.openMyProfilePage(MainMenuItem.MY_PROFILE);

        logger.info(String.format("Create post with random text %s", RANDOM_TEXT));
        int postId = profilePageSteps.createPost(RANDOM_TEXT);

        logger.info(String.format("Check that post %s is displayed", postId));
        profilePageSteps.assertCreatedPostIsDisplayed(postId);

        logger.info(String.format("Check that post text is the same as expected %s", RANDOM_TEXT));
        profilePageSteps.assertText(postId, RANDOM_TEXT);

        logger.info(String.format("Check that author of post %s the same as expected", postId));
        profilePageSteps.assertAuthor(postId);

        logger.info(String.format("Add attachment and edit %s post text", postId));
        SavedPictureResponse savedPicture = profilePageSteps.editPostTextAndAttachment(RANDOM_TEXT_FOR_EDIT, postId, PICTURE_PATH);

        logger.info(String.format("Check that text is the same as expected %s", RANDOM_TEXT_FOR_EDIT));
        profilePageSteps.assertText(postId, RANDOM_TEXT_FOR_EDIT);

        logger.info(String.format("Check that attached picture is displayed on post %s", postId));
        profilePageSteps.assertPictureIsDisplayed(postId, savedPicture);

        logger.info(String.format("Check that picture in post %s is the same as uploaded one", postId));
        profilePageSteps.assertPictureIsTheSameAsUploaded(savedPicture, PICTURE_PATH, DOWNLOADED_PICTURE_NAME);

        logger.info(String.format("Add comment with text %s", RANDOM_TEXT_FOR_COMMENT));
        int commentId = profilePageSteps.addCommentToPost(postId, RANDOM_TEXT_FOR_COMMENT);

        logger.info(String.format("Extend comments under the post %s", postId));
        profilePageSteps.extendComments(postId);

        logger.info(String.format("Check that comment text is the same as expected %s", RANDOM_TEXT_FOR_COMMENT));
        profilePageSteps.assertText(commentId, RANDOM_TEXT_FOR_COMMENT);

        logger.info(String.format("Check that author of post %s the same as expected", commentId));
        profilePageSteps.assertAuthor(commentId);

        logger.info(String.format("Click like button for %s post", postId));
        profilePageSteps.likePost(postId);

        logger.info(String.format("Check that author of comment %s the same as expected", commentId));
        profilePageSteps.assertLikeAuthor(postId);

        logger.info(String.format("Delete post %s", postId));
        profilePageSteps.deletePost(postId);

        logger.info(String.format("Check that post %s is deleted", postId));
        profilePageSteps.assertPostDeletion(postId);
    }
}
