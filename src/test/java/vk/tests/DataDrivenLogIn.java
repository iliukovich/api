package vk.tests;

import org.testng.annotations.DataProvider;

public class DataDrivenLogIn {

    @DataProvider(name = "userData")
    public Object[][] provideUserData() {

//        return new Object[][]{{System.getProperty("username"), System.getProperty("password")}};
        return new Object[][]{{"autoperftester@gmail.com", "PuV6j_.2&$m9h?UYY"}};
    }
}
