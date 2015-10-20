package spiders;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author jacek.malolepszy@hp.com
 * @since 2015-10-20
 */
public class Pkt2015 {

    public static void main(String[] args) throws IOException {

        String baseUrl = "http://www.pkt.pl/";

        String initialUrl = "http://www.pkt.pl/artykuly-papiernicze-1/20-1/";

        Document doc = Jsoup.connect(initialUrl).get();
        Elements categoryLinkList = doc.select(".box-category_items a");

        for (Element item : categoryLinkList) {
            String subCategoryUrl = baseUrl + "/" + item.attr("href");

            Document subDoc = Jsoup.connect(subCategoryUrl).get();

            Elements subCategoryLinkList = subDoc.select(".box-categories li a");

            System.out.println(item.attr("href"));
            for (Element element : subCategoryLinkList) {
                System.out.println("\t" + element.attr("href"));
            }
        }

    }
}
