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
 * @author jacek.malolepszy@hp.com
 * @since 2015-10-20
 * 3h
 */
public class Pkt2015 {

    public static void main(String[] args) throws IOException {

        String baseUrl = "http://www.pkt.pl/";

        String initialUrl = "http://www.pkt.pl/szko%C5%82y-ta%C5%84ca/4-1/";

        Document mainPage = Jsoup.connect(initialUrl).get();
        Elements categoryLinkList = mainPage.select(".box-category_items a");

        File file = new File("/home/magik/szkoly_tanca.txt");

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
                        int whichPageDeep = 0;

                        while (continueOnPage && whichPageDeep < 5) {

                            Document concretePage = Jsoup.connect(subCategoryUrl).get();
                            Elements elements = concretePage.select(".box-content");

                            for (Element companyItem : elements) {

                                Elements companyNameElements = companyItem.select(".company-name a");
                                Elements companyAddressElements = companyItem.select(".street--name a span[itemprop=addressRegion]");
                                Elements companyPhoneElements = companyItem.select(".call--phone .call-number meta");
                                Elements companyPageElements = companyItem.select(".call--www span");
                                Elements companyEmailElements = companyItem.select(".call--email span");

                                String companyNameItem = "";
                                if (companyNameElements != null && companyNameElements.size() == 1) {
                                    companyNameItem = companyNameElements.get(0).html();
                                }
                                String companyAddress = "";
                                if (companyAddressElements != null && companyAddressElements.size() == 1) {
                                    companyAddress = companyAddressElements.get(0).html();
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

                                String csvItem = companyNameItem + " ; " + companyAddress + " ; " + phoneItem + " ; " + wwwItem + " ; " + emailItem;

                                System.out.println(csvItem);
                                bw.append(csvItem);
                                bw.newLine();
                            }

                            Elements nextPageLink = concretePage.select(".pagination li a[rel=next]");

                            if (nextPageLink.size() == 1) {
                                whichPageDeep++;

                                subCategoryUrl = baseUrl + nextPageLink.get(0).attr("href");

                                try {
                                    Thread.sleep(1500);
                                } catch (Exception e) {
                                    System.out.println("thread sleep");
                                }

                            } else {
                                continueOnPage = false;
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
