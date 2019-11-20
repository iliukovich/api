package vk.tests;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.logger.Logger;
import helpers.CloudinaryHelper;
import helpers.FileHelpers;
import helpers.ImageUtils;
import helpers.PropertiesResourceManager;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import testrail.api.TestrailApi;
import testrail.helpers.TestInfo;

import java.io.File;
import java.util.Map;

public class BaseTest {

    private static String VK_URL = new PropertiesResourceManager("vk.properties").getProperty("vk.url");
    private static String urlForImageDownload = "";
    private static Long runId;
    private static final String EXPECTED_FILE_NAME = "target/expectedFileCloudinary.jpg";
    private static final String DOWNLOADED_FILE_NAME = "target/actualFileCloudinary.jpg";
    protected final Logger logger = Logger.getInstance();
    private Browser browser = BrowserManager.getBrowser();
    protected TestInfo testInfo;


    @BeforeSuite
    public void createRun() {
        runId = new TestrailApi().createRun();
    }

    @BeforeMethod
    public void setUp() {
        testInfo = getClass().getAnnotation(TestInfo.class);
        browser.maximize();
        browser.goTo(VK_URL);
        browser.waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        publishToCloudinary();
        publishToTestRail(testResult);
        browser.quit();
    }

    private void publishToTestRail(ITestResult testResult) {
        TestrailApi testrailApi = new TestrailApi();
        testrailApi.postTestResults(runId, testInfo.id(), testResult, urlForImageDownload);
    }

    private void publishToCloudinary() {
        logger.info("Get screenshot after test");
        byte[] screenshot = BrowserManager.getBrowser().getScreenshot();
        CloudinaryHelper cloudinaryHelper = new CloudinaryHelper();

        logger.info("Upload screenshot on Cloudinary");
        Map response = cloudinaryHelper.uploadScreenshot(screenshot);
        urlForImageDownload = cloudinaryHelper.getUploadedFileUrl(response);

        logger.info("Check that screenshot uploaded on Cloudinary is the same as uploaded one");
        File expectedFile = FileHelpers.convertByteArrayIntoTheFile(screenshot, EXPECTED_FILE_NAME);
        File actualFile = new File(FileHelpers.downloadFileByUrl(urlForImageDownload, DOWNLOADED_FILE_NAME));
        Assert.assertTrue(ImageUtils.isSimilarToBaseImage(actualFile, expectedFile), "Files are not equal");
    }
}
