package vk.menus;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import vk.enums.MainMenuItem;

public class MainMenu extends Form {

    public MainMenu() {
        super(By.id("side_bar_inner"), "Side bar");
    }

    public void clickItem(MainMenuItem mainMenuItem) {
        getMainMenuItem(mainMenuItem).clickAndWait();
    }

    private ILabel getMainMenuItem(MainMenuItem mainMenuItem) {
        return getElementFactory().getLabel(By.id(mainMenuItem.getMenuItem()), "My profile");
    }
}
