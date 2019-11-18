package vk.enums;

public enum MainMenuItem {
    MY_PROFILE("l_pr");

    private String  menuItem;

    MainMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuItem() {
        return menuItem;
    }
}
