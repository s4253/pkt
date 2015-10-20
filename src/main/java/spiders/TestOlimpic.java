package spiders;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class TestOlimpic {

    private WebDriver driver;

    private String baseUrl;

    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception
    {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testGoogle()
    {
        driver.get("http://www.google.com");

        for (int i = 0; i < 10000; i++) {
            driver.findElement(By.id("hplogo"))
                .sendKeys(Keys.ARROW_LEFT,
                    Keys.ARROW_RIGHT,
                    Keys.ARROW_LEFT,
                    Keys.ARROW_RIGHT,
                    Keys.ARROW_LEFT,
                    Keys.ARROW_RIGHT,
                    Keys.ARROW_LEFT,
                    Keys.ARROW_RIGHT,
                    Keys.ARROW_LEFT,
                    Keys.ARROW_RIGHT);
        }

        driver.findElement(By.className("a"));
    }

    @Test
    public void basketball() throws InterruptedException
    {
        long threadTime = 300;
        driver.get("http://www.google.com");

        driver.findElement(By.id("hplogo")).click();
        driver.findElement(By.id("hplogo")).click();
        for (int i = 0; i < 10000; i++) {
            driver.findElement(By.id("hplogo")).sendKeys(Keys.SPACE);
            Thread.sleep(threadTime);

            if (i > 1000) {
                threadTime = 500;
            }
        }
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by)
    {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}