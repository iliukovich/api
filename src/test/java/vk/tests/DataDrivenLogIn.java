package vk.tests;

import org.testng.annotations.DataProvider;

public class DataDrivenLogIn {

    @DataProvider(name = "userData")
    public Object[][] provideUserData() {

        return new Object[][]{{System.getProperty("username"), System.getProperty("password")}};
    }
}
