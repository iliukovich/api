package vk.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Login extends Form {

    private ITextBox email = getElementFactory().getTextBox(By.id("index_email"), "email");
    private ITextBox passwd = getElementFactory().getTextBox(By.id("index_pass"), "password");
    private IButton loginButton = getElementFactory().getButton(By.id("index_login_button"), "login button");

    public Login() {
        super(By.id("index_login"), "Login page");
    }

    public void fillEmail(String emailValue) {
        email.clearAndType(emailValue);
    }

    public void fillPasswd(String passwdValue) {
        passwd.clearAndType(passwdValue);
    }

    public void clickLogin() {
        loginButton.clickAndWait();
    }
}
