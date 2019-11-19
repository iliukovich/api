package vk.tests;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.logger.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import helpers.PropertiesResourceManager;

public class BaseTest {

    private static String VK_URL = new PropertiesResourceManager("vk.properties").getProperty("vk.url");
    private Browser browser = BrowserManager.getBrowser();
    protected final Logger logger = Logger.getInstance();

    @BeforeMethod
    public void setUp() {
        browser.maximize();
        browser.goTo(VK_URL);
        browser.waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown() {
        browser.quit();
    }
}
