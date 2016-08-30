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
 * Created by malolepj on 2016-08-30.
 * Hewlett Packard Enterprise (HPE)
 */
public class E_angielski {

    public static void main(String[] args) throws IOException
    {
        String baseUrl = "http://www.e-angielski.com/";

        String initialUrl = "http://www.e-angielski.com/szkoly-jezykowe?page=";

        File file = new File("C:\\Users\\malolepj\\xxl\\e_angielski.txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            String currentPageUrl = initialUrl;

            boolean continueOnPage = true;

            for (int i = 0; i <= 10; ++i) {
                initialUrl += i;
                Document concretePage = Jsoup.connect(currentPageUrl).get();
                Elements elements = concretePage.select("table.views-table td.views-field a ");

                for (Element companyItem : elements) {
                    String itemUrl = baseUrl + companyItem.attr("href");

                    System.out.println(itemUrl);

//                    Document itemContent = Jsoup.connect(itemUrl).get();
//
//                    Elements itemEmail = itemContent.select("div.field-field-email");
//
//                    String csvItem = itemEmail.html();
//
//                    System.out.println(csvItem);
//                    bw.append(csvItem);
//                    bw.newLine();
                }
            }
        } catch (Exception e) {
            bw.close();
        }

        bw.close();
    }
}
