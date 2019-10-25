package vk.tests;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static String VK_URL = "https://vk.com/";
    private Browser browser = BrowserManager.getBrowser();

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
