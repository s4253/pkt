package spiders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: Jacek Malolepszy
 * Date: 11.12.15
 * Time: 14:37
 */
public class PolkiPL {

    public static void main(String[] args) throws IOException
    {

        String baseUrl = "http://www.pkt.pl/";

        String initialUrl = "http://polki.pl/szkoly-tanca,we-dwoje-baza-firm-kategoria,1000148.html";

        File file = new File("/home/magik/polki__pl.txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            String currentPageUrl = initialUrl;

            boolean continueOnPage = true;

            while (continueOnPage) {
                System.out.println("Current page url : " + currentPageUrl);

                Document concretePage = Jsoup.connect(currentPageUrl).get();
                Elements elements = concretePage.select("li a[class=ttl]");

                for (Element companyItem : elements) {

                    String itemUrl = companyItem.attr("href");
                    Document itemContent = Jsoup.connect(itemUrl).get();

                    Elements itemEmail = itemContent.select(".bf_content .contact .i_email #id_email1 a");

                    String csvItem = itemEmail.html();

                    System.out.println(csvItem);
                    bw.append(csvItem);
                    bw.newLine();
                }

                Elements nextPageLink = concretePage.select("#pager_0 li[class=next] a");

                if (nextPageLink.size() == 1) {

                    currentPageUrl = nextPageLink.get(0).attr("href");
                    System.out.println("Next page :" + currentPageUrl);

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        System.out.println("thread sleep");
                    }
                } else {
                    continueOnPage = false;
                }
            }
        } catch (Exception e) {
            bw.close();
        }

        bw.close();
    }
}
