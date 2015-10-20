package spiders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;

public class YellowComSelenium {
    public static void main(String[] args) throws IOException {

        String url = "http://www.yell.com/ucs/UcsSearchAction.do?keywords=language+courses+%26+schools&location=&scrambleSeed=33462222&searchType=&M=&bandedclarifyResults=&ssm=1";

        WebDriver webDriver = new HtmlUnitDriver();
//        WebDriver webDriver = new FirefoxDriver();
        webDriver.get(url);

        System.out.println(webDriver.getPageSource());

        WebElement el = webDriver.findElement(By.className("l-header"));
        WebElement link = el.findElement(By.tagName("a"));

        link.click();


        String source = webDriver.getPageSource();
        System.out.println(source.contains("last"));

        System.out.println(source);

        webDriver.findElement(By.className("last")).click();

        webDriver.findElement(By.id("subject")).sendKeys("job offer");

        System.out.println(webDriver.findElement(By.id("subject")).getText());


        System.out.println(webDriver.getPageSource());


    }
}
