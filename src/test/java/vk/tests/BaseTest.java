package vk.tests;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.logger.Logger;
import helpers.CloudinaryHelper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import helpers.PropertiesResourceManager;

import java.util.Map;

public class BaseTest {

    private static String VK_URL = new PropertiesResourceManager("vk.properties").getProperty("vk.url");
    private Browser browser = BrowserManager.getBrowser();
    protected final Logger logger = Logger.getInstance();
    private static final String EXPECTED_FILE_NAME = "target/expectedFileCloudinary.jpg";
    private static final String DOWNLOADED_FILE_NAME = "target/actualFileCloudinary.jpg";

    @BeforeMethod
    public void setUp() {
        browser.maximize();
        browser.goTo(VK_URL);
        browser.waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown() {
        byte[] screenshot = BrowserManager.getBrowser().getScreenshot();
        CloudinaryHelper cloudinaryHelper = new CloudinaryHelper();

        Map response = cloudinaryHelper.uploadScreenshot(screenshot);
        String urlForImageDownload = cloudinaryHelper.getUploadedFileUrl(response);
        Assert.assertTrue(cloudinaryHelper.assertFileIsTheSameAsUploaded(EXPECTED_FILE_NAME, screenshot, urlForImageDownload, DOWNLOADED_FILE_NAME));
        browser.quit();
    }
}
