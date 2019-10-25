package vk.steps;

import vk.pages.LoginPage;

public class LoginPageSteps {

    private static LoginPage loginPage = new LoginPage();

    public static void login(String email, String passwd) {
        loginPage.getLoginForm().fillEmail(email);
        loginPage.getLoginForm().fillPasswd(passwd);
        loginPage.getLoginForm().clickLogin();
    }
}
