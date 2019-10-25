package vk.tests;

import org.testng.annotations.DataProvider;

public class DataDrivenLogIn {

    @DataProvider(name = "userData")
    public Object[][] provideUserData() {

        return new Object[][]{{"autoperftester@gmail.com", "PuV6j_.2&$m9h?UYY"}};
    }

    @DataProvider(name = "userDataOne")
    public Object[][] provideUserDataOne() {

        return new Object[][]{{"375295130163", "Testika1qa"}};
    }
}
