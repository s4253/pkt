package spiders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jacek Malolepszy
 * Date: 25.10.15
 * Time: 21:26
 */
public class Pkt2015SM {

    public static void main(String[] args) throws IOException {

        String baseUrl = "http://www.pkt.pl/";

        String initialUrl = "http://www.pkt.pl/artykuly-papiernicze-1/20-1/";

        Document mainPage = Jsoup.connect(initialUrl).get();
        Elements categoryLinkList = mainPage.select(".box-category_items a");

        File file = new File("/home/magik/mailList.txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        List<String> smLinkList = new ArrayList<String>();

        smLinkList.add("http://www.pkt.pl/j%C4%99zyki-obce-nauka/4-1/");
//        smLinkList.add("http://www.pkt.pl/j%C4%99zyki-obce-nauka/4-1/");
//        smLinkList.add("http://www.pkt.pl/szko%C5%82a-j%C4%99zykowa/4-1/?oWhat=szko%C5%82a+j%C4%99zykowa");
//        smLinkList.add("http://www.pkt.pl/szko%C5%82a-ta%C5%84ca/4-1/?oWhat=szko%C5%82a+ta%C5%84ca");
//        smLinkList.add("http://www.pkt.pl/nauka-j%C4%99zyka/4-1/?oWhat=nauka+j%C4%99zyka");
//        smLinkList.add("http://www.pkt.pl/nauka-ta%C5%84ca/4-1/?oWhat=nauka+ta%C5%84ca");
//        smLinkList.add("http://www.pkt.pl/nauka-j%C4%99zyka-obcego/4-1/?oWhat=nauka+j%C4%99zyka+obcego");
//        smLinkList.add("http://www.pkt.pl/taniec/4-1/?oWhat=taniec");
//        smLinkList.add("http://www.pkt.pl/kursy-j%C4%99zykowe/4-1/?oWhat=kursy+j%C4%99zykowe");
//        smLinkList.add("http://www.pkt.pl/szko%C5%82a-muzyczna/4-1/?oWhat=szko%C5%82a+muzyczna");
//        smLinkList.add("http://www.pkt.pl/szko%C5%82a-j%C4%99zyka/4-1/?oWhat=szko%C5%82a+j%C4%99zyka");

        try {

            for (String item : smLinkList) {

                bw.append(item).append(" ; empty ; empty ; empty");
                bw.newLine();

                try {

                    String subCategoryUrl = item;

                    boolean continueOnPage = true;

                    while (continueOnPage) {

                        Document concretePage = Jsoup.connect(subCategoryUrl).get();
                        Elements elements = concretePage.select(".box-content");

                        for (Element companyItem : elements) {

                            Elements companyElements = companyItem.select(".company-name a");
                            Elements companyPhoneElements = companyItem.select(".call--phone .call-number meta");
                            Elements companyPageElements = companyItem.select(".call--www span");
                            Elements companyEmailElements = companyItem.select(".call--email span");

                            String companyNameItem = "";
                            if (companyElements != null && companyElements.size() == 1) {
                                companyNameItem = companyElements.get(0).html();
                            }
                            String phoneItem = "";
                            if (companyPhoneElements != null && companyPhoneElements.size() == 1) {
                                phoneItem = companyPhoneElements.get(0).attr("content");
                            }
                            String wwwItem = "";
                            if (companyPageElements != null && companyPageElements.size() == 1) {
                                wwwItem = companyPageElements.get(0).attr("title");
                            }
                            String emailItem = "";
                            if (companyEmailElements != null && companyEmailElements.size() == 1) {
                                emailItem = companyEmailElements.get(0).attr("title");
                            }

                            String csvItem = companyNameItem + " ; " + phoneItem + " ; " + wwwItem + " ; " + emailItem;

                            System.out.println(csvItem);
                            bw.append(csvItem);
                            bw.newLine();
                        }

                        Elements nextPageLink = concretePage.select(".pagination li a[rel=next]");

                        if (nextPageLink.size() == 1) {

                            subCategoryUrl = baseUrl + nextPageLink.get(0).attr("href");
                        } else {
                            continueOnPage = false;
                            try {

                                Thread.sleep(1000);
                            } catch (Exception e) {
                                System.out.println("thread sleep");
                            }
                        }
                    }
                } catch (Exception e) {
                    bw.close();
                }
            }
        } catch (Exception e) {
            bw.close();
        }
    }
}
