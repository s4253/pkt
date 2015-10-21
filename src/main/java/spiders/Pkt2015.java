package spiders;


import exception.NextPageNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author jacek.malolepszy@hp.com
 * @since 2015-10-20
 * 3h
 */
public class Pkt2015 {

    public static void main(String[] args) throws IOException {

        String baseUrl = "http://www.pkt.pl/";

        String initialUrl = "http://www.pkt.pl/artykuly-papiernicze-1/20-1/";

        Document mainPage = Jsoup.connect(initialUrl).get();
        Elements categoryLinkList = mainPage.select(".box-category_items a");

        File file = new File("/home/magik/mailList.txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        try {

            for (Element item : categoryLinkList) {
                String categoryUrl = baseUrl + "/" + item.attr("href");

                try {

                    Document categoryPage = Jsoup.connect(categoryUrl).get();
                    Elements subCategoryLinkList = categoryPage.select(".box-categories li a");
                    System.out.println(item.attr("href"));

                    for (Element element : subCategoryLinkList) {
                        String subCategoryUrl = baseUrl + element.attr("href");

                        boolean continueOnPage = true;

                        while (continueOnPage) {

                            Document concretePage = Jsoup.connect(subCategoryUrl).get();
                            Elements emailElements = concretePage.select(".box-content .call--email span");

                            for (Element emailElement : emailElements) {
                                String emailItem = emailElement.attr("title");
                                System.out.println(emailItem);
                                bw.append(emailItem);
                                bw.newLine();
                            }

                            Elements nextPageLink = concretePage.select(".pagination li a[rel=next]");

                            if (nextPageLink.size() == 1) {

                                subCategoryUrl = baseUrl + nextPageLink.get(0).attr("href");
                            } else {
                                continueOnPage = false;
                                throw new NextPageNotFoundException();  //To remove later time
                            }
                        }
                    }
                } catch (NextPageNotFoundException e) {
                    bw.close();
                }
            }
        } catch (Exception e) {
            bw.close();
        }
    }
}
