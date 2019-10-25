package vk.menus;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainMenu extends Form {

    private ILabel myProfile = getElementFactory().getLabel(By.id("l_pr"), "My profile");

    public void clickMyProfile() {
        myProfile.clickAndWait();
    }

    public MainMenu() {
        super(By.id("side_bar_inner"), "Side bar");
    }
}
