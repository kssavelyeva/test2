import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseRunner {
    WebDriver driver;
    public String browserName = System.getProperty("browser");
    String baseUrl;

    @Before
    public void setUp() {
        driver = BrowsersFactory.chrome.create();
        driver.manage().window().maximize();
        baseUrl = "https://www.tinkoff.ru/mobile-operator/tariffs/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    private WebDriver getDriver(){
        BrowsersFactory.valueOf(System.getProperty("browser"));
        return BrowsersFactory.valueOf(browserName).create();

    }

}
