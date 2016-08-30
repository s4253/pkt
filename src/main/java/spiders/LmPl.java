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
public class LmPl {

    public static void main(String[] args) throws IOException
    {
        String baseUrl = "http://www.lm.pl/firmy/lista/123/";

        File file = new File("C:\\Users\\malolepj\\xxl\\e_angielski.txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        try {
            String currentPageUrl = baseUrl;

            for (int i = 1; i <= 6; ++i) {
                Document concretePage = Jsoup.connect(currentPageUrl + i).get();
                Elements elements = concretePage.select("strong.fl_wyroznienie");

                for (Element element : elements) {
                    System.out.println(element.html());
                }
            }
        } catch (Exception e) {
            bw.close();
        }

        bw.close();
    }
}
