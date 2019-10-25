package vk.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import vk.models.photos.saveWallPhoto.SavedPictureResponse;

public class ProfileWall extends Form {

    private ILabel getPost(Integer postId) {
        return getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')][.//div[contains(@class, 'content')]]", postId)), "post");
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
        ILabel postText = getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')]/div[contains(@class, 'text')]", postId)), "post text");
        return postText.getText();
    }

    public String getUser() {
        ILabel userId = getElementFactory().getLabel(By.id("top_profile_link"), "userId");
        return userId.getAttribute("href");
    }

    public String getWallPostAuthorId(Integer postId) {
        ILabel authorId = getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')]//a[@class='author']", postId)), "author");
        return authorId.getAttribute("href");
    }

    public void clickLikeButton(Integer postId) {
        IButton likeButton = getElementFactory().getButton(By.xpath(String.format("//div[contains(@class, '%s')]//a[contains(@class, '_like')]", postId)), "like");
        likeButton.clickAndWait();
    }

    public boolean isPhotoDisplayed(Integer postId, SavedPictureResponse savedPicture) {
        ILabel photoFromPost = getElementFactory().getLabel(By.xpath(String.format("//div[contains(@id, '%s')]//a[contains(@href, '%s')]", postId, savedPicture.getMediaId())), "posted photo");
        return photoFromPost.state().waitForDisplayed();
    }

    public void extendComments(Integer postId) {
        IButton extendButton = getElementFactory().getButton(By.xpath(String.format("//a[contains(@href, '%s') and contains(@class, 'replies')]", postId)), "extend comments");
        extendButton.clickAndWait();
    }

    public ProfileWall() {
        super(By.id("profile_wall"), "Profile wall");
    }
}

