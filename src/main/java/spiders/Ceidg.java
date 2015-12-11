package spiders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * User: Jacek Malolepszy
 * Date: 11.01.15
 * Time: 20:51
 */
public class Ceidg {

    public static void main(String[] args) throws IOException
    {
        File resultFile = new File("/home/magik/mailList.txt");

        FileWriter fw = new FileWriter(resultFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        String baseDir = "/home/magik/Desktop/ceidg";
        File file = new File(baseDir);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName)
            {
                return fileName.endsWith(".htm");
            }
        };


        File[] fileHtmList = file.listFiles(filter);

        String baseUrl = "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/";

        for (File fileItem : fileHtmList) {

            System.out.println(fileItem.getName());

            Document mainPage = Jsoup.parse(fileItem, "UTF-8");

            Elements itemList = mainPage.select("#searchResults .searchITDivDetails a:first-child");

            for (Element element : itemList) {
                String itemDetailsUrl = element.attr("href");
                Document itemDetails = Jsoup.connect(itemDetailsUrl).get();

                Elements name = itemDetails.select("#MainContent_lblName");
                Elements email = itemDetails.select("#MainContent_lblEmail a");
                Elements www = itemDetails.select("#MainContent_lblWebstite a");

                String line = " ";

                if (name.size() == 1) {
                    System.out.print(name.get(0).html());
                    line += name.get(0).html();
                }

                line += ";";
                System.out.print(";");

                if (email.size() == 1) {
                    System.out.print(email.get(0).html());
                    line += email.get(0).html();
                }

                line += ";";
                System.out.print(";");

                if (www.size() == 1) {
                    System.out.print(www.get(0).html());
                    line += www.get(0).html();
                }
                line += "\n";

                bw.append(line);
                bw.newLine();
                System.out.println();
            }
        }

        bw.close();
    }
}
