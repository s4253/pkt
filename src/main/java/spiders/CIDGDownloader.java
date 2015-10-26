package spiders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * User: Jacek Malolepszy
 * Date: 25.10.15
 * Time: 22:00
 */
public class CIDGDownloader {
    public static void main(String[] args) throws IOException {
        String baseUrl = "https://prod.ceidg.gov.pl/ceidg/ceidg.public.ui/search.aspx";

        Document mainPage = Jsoup.connect(baseUrl).get();
        Elements categoryLinkList = mainPage.select(".box-category_items a");
    }
}
