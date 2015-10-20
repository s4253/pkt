package spiders;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewPKTSelenium {

    public static void main(String[] args) throws IOException {

        List<String> emailList = new ArrayList<String>();

        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();
        int pageNumber = 1;

//        String pktLink = "http://pkt.pl/Szko%C5%82a%20j%C4%99zykowa/4-1/?&splitType=regular&sortBy=relevance&collapsing=true&debug=0&page=";
        String pktLink = "http://www.pkt.pl/Szko%C5%82y%20ta%C5%84ca/4-1/?&splitType=regular&sortBy=relevance&collapsing=true&debug=0&page=";

//        for (int i = 1; i <= 228; ++i) {
        for (int i = 1; i <= 42; ++i) {

            driver.get(pktLink + i);


            List<String> linkCollections = new ArrayList<String>();

            for (WebElement element : driver.findElements(By.className("trkname_resultsclick"))) {
                String url = element.getAttribute("href");
                if (url.contains("local")) {
                    linkCollections.add(url);


                }
            }

            for (String url : linkCollections) {
                driver.get(url);
                WebElement el = null;
                try {
                    el = driver.findElement(By.className("address_email"));
                } catch (NoSuchElementException ex) {
                    continue;
                }
                el = el.findElement(By.tagName("a"));
                emailList.add(el.getText());

            }
        }

        driver.quit();

        FileWriter fileWriter = new FileWriter("/home/magik/Desktop/szkoly_tanca.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String emailItem : emailList) {
            System.out.println(emailItem);
            bufferedWriter.write(emailItem + "\n");
        }

        bufferedWriter.close();
    }
}
