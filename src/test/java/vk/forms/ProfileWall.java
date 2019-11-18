package vk.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import vk.models.photos.saveWallPhoto.SavedPictureResponse;

public class ProfileWall extends Form {

    private static final String POST_LOCATOR = "//div[contains(@id, '%s')][.//div[contains(@class, 'content')]]";
    private static final String TEXT_FROM_POST_CONTENT_LOCATOR = "//div[contains(@id, '%s')]/div[contains(@class, 'text')]";
    private static final String WALL_POST_AUTHOR_ID_LOCATOR = "//div[contains(@id, '%s')]//a[@class='author']";
    private static final String LIKE_BUTTON_LOCATOR = "//div[contains(@class, '%s')]//a[contains(@class, '_like')]";
    private static final String PHOTO_LOCATOR = "//div[contains(@id, '%s')]//a[contains(@href, '%s')]";
    private static final String COMMENT_LOCATOR = "//a[contains(@href, '%s') and contains(@class, 'replies')]";

    public ProfileWall() {
        super(By.id("profile_wall"), "Profile wall");
    }

    private ILabel getPost(Integer postId) {
        return getElementFactory().getLabel(By.xpath(String.format(POST_LOCATOR, postId)), "post");
    }

    public boolean isWallPostDisplayed(Integer postId) {
        ILabel post = getPost(postId);
        return post.state().waitForDisplayed();
    }

    public boolean isWallPostNotDisplayed(Integer postId) {
        ILabel post = getPost(postId);
        return post.state().waitForNotDisplayed();
    }

    public String getTextFromPostContent(Integer postId) {
        ILabel postText = getElementFactory().getLabel(By.xpath(String.format(TEXT_FROM_POST_CONTENT_LOCATOR, postId)), "post text");
        return postText.getText();
    }

    public String getUser() {
        ILabel userId = getElementFactory().getLabel(By.id("top_profile_link"), "userId");
        return userId.getAttribute("href");
    }

    public String getWallPostAuthorId(Integer postId) {
        ILabel authorId = getElementFactory().getLabel(By.xpath(String.format(WALL_POST_AUTHOR_ID_LOCATOR, postId)), "author");
        return authorId.getAttribute("href");
    }

    public void clickLikeButton(Integer postId) {
        IButton likeButton = getElementFactory().getButton(By.xpath(String.format(LIKE_BUTTON_LOCATOR, postId)), "like");
        likeButton.clickAndWait();
    }

    public boolean isPhotoDisplayed(Integer postId, SavedPictureResponse savedPicture) {
        ILabel photoFromPost = getElementFactory().getLabel(By.xpath(String.format(PHOTO_LOCATOR, postId, savedPicture.getMediaId())), "posted photo");
        return photoFromPost.state().waitForDisplayed();
    }

    public void extendComments(Integer postId) {
        IButton extendButton = getElementFactory().getButton(By.xpath(String.format(COMMENT_LOCATOR, postId)), "extend comments");
        extendButton.clickAndWait();
    }
}

