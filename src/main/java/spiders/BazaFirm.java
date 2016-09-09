package spiders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * Created by malolepj on 2016-08-31.
 * Hewlett Packard Enterprise (HPE)
 */
public class BazaFirm {

    public static void main(String[] args) throws IOException {

        Locale.setDefault(new Locale("pl", "PL"));
        String baseUrl = "http://www.baza-firm.com.pl/Szko%C5%82y-j%C4%99zykowe/strona-";

        File file = new File("C:\\Users\\malolepj\\xxl\\baza_firm.txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());

        int imageName = 0;

        for (int i = 90; i <= 92; ++i) {

            System.out.printf("page " + i);
            System.out.println(baseUrl + i);

            try {
                Document concretePage = Jsoup.connect(baseUrl + i).get();
                Elements elements = concretePage.select("ul.wizResBox div.divSMV_daneNazwabold a");
                Elements elements2 = concretePage.select("ul.wizResBox div.divSMV_daneNazwa a");

                Elements suma = new Elements();
                suma.addAll(elements);
                suma.addAll(elements2);

                for (Element companyItem : suma) {
                    String itemUrl = companyItem.attr("href");

                    itemUrl = itemUrl.replace("ł", "%C5%82");
                    itemUrl = itemUrl.replace("ę", "%C4%99");
                    itemUrl = itemUrl.replace("ó", "%C3%B3");
                    itemUrl = itemUrl.replace("ą", "%C4%85");
                    itemUrl = itemUrl.replace("ç", "%C3%A7");
                    itemUrl = itemUrl.replace("ś", "%C5%9B");
                    itemUrl = itemUrl.replace("ź", "%C5%BA");
                    itemUrl = itemUrl.replace("ń", "%C5%84");
                    itemUrl = itemUrl.replace("ż", "%C5%BC");
                    itemUrl = itemUrl.replace("®", "%C2%AE");
                    itemUrl = itemUrl.replace("é", "%C3%A9");
                    itemUrl = itemUrl.replace("á", "%c3%a1");

                    System.out.println(itemUrl);

                    Document itemContent = Jsoup.connect(itemUrl).get();
                    Elements itemEmail = itemContent.select("img.emlImg");
                    String emailImgUrl = itemEmail.attr("src");

                    if (emailImgUrl != null && emailImgUrl != "") {
                        System.out.println(emailImgUrl);

                        InputStream in = new URL(emailImgUrl).openStream();
                        Files.copy(in, Paths.get("C:/Users/malolepj/xxl/sm/nowa_baza_sm/image" + imageName + ".png"));
                        imageName++;
                        in.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
