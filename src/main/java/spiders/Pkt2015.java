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

        String baseUrl = "http://www.pkt.pl/artykuly-papiernicze-1/20-1/";

        Document doc = Jsoup.connect(baseUrl).get();
        Elements linkList = doc.select(".box-category_items a");

        for (Element item : linkList)
            System.out.println(item.attr("href"));

    }
}
