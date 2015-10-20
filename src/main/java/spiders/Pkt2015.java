package spiders;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author jacek.malolepszy@hp.com
 * @since 2015-10-20
 * 1h
 */
public class Pkt2015 {

    public static void main(String[] args) throws IOException {

        String baseUrl = "http://www.pkt.pl/";

        String initialUrl = "http://www.pkt.pl/artykuly-papiernicze-1/20-1/";

        Document mainPage = Jsoup.connect(initialUrl).get();
        Elements categoryLinkList = mainPage.select(".box-category_items a");

        for (Element item : categoryLinkList) {
            String categoryUrl = baseUrl + "/" + item.attr("href");

            Document categoryPage = Jsoup.connect(categoryUrl).get();

            Elements subCategoryLinkList = categoryPage.select(".box-categories li a");

            System.out.println(item.attr("href"));
            for (Element element : subCategoryLinkList) {
                String subCategoryUrl = baseUrl + element.attr("href");

                Document concretePage = Jsoup.connect(subCategoryUrl).get();
                Elements emailElements = concretePage.select(".box-content .call--email span");

                for (Element emailElement : emailElements) {
                    System.out.println(emailElement.attr("title"));
                }
            }
        }

    }
}
