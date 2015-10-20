package spiders;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewParser {

    public static String[][] data = new String[8000][6];

    public static void main(String[] arg) throws IOException
    {
        int counter = 0;

        for (int i = 1; i <= 25; i++) {
//            URL url = new URL("http://www.pkt.pl/firmy/-/q_bran%C5%BCa%3A%22Szko%C5%82y+Ta%C5%84ca%22/" + i + "/");// + (i));
            URL url = new URL("http://www.pkt.pl/firmy/Województwo%3A\"Mazowieckie\"/q_branża%3A\"Szkoły+Języków+Obcych\"/" + i + "/");// + (i));
            URLConnection urlConnection = url.openConnection();
            BufferedReader htmlPage = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String link = "<a id=\"previe";

            String text = "";
            String line = "";

            while ((line = htmlPage.readLine()) != null) {
                text += line;
            }

            Pattern pattern = Pattern.compile(link);
            Matcher matcher = pattern.matcher(text);

            String tmp;
            int end = 0;
            while (matcher.find()) {
                try {
                    tmp = text.substring(matcher.start(), matcher.end() + 100);
                    end = tmp.indexOf(">");
                    tmp = tmp.substring(0, end + 1);
                    tmp = tmp.substring(tmp.indexOf("href") + 6);
                    tmp = tmp.substring(0, tmp.length() - 2);
//                System.out.println(matcher.start() + " " + matcher.end()+ 300);
//                System.out.println(tmp);

                    URL url2 = new URL("http://www.pkt.pl" + tmp);// + (i));
                    URLConnection urlConnection2 = url2.openConnection();
                    BufferedReader htmlPage2 = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));

                    String text2 = "";
                    while ((line = htmlPage2.readLine()) != null) {
                        text2 += line;
                    }
//                    System.out.println(text2);

                    String match = "repname_email repkey";

                    Pattern pattern2 = Pattern.compile(match);
                    Matcher matcher2 = pattern2.matcher(text2);
                    String tmp2 = "";
                    while (matcher2.find()) {
                        tmp2 = text2.substring(matcher2.start(), matcher2.end() + 70);
                        tmp2 = tmp2.substring(tmp2.indexOf("'>") + 2, tmp2.indexOf("</"));
                        System.out.println(tmp2);
                        data[counter][0] = tmp2;
                        counter++;
                    }
                } catch (Exception ex) {
                    System.out.println("exception");
                }
            }
        }

        saveToExcel();
    }

    public static String align(String word, int length)
    {
        int counter = length - word.length();
        for (int i = 0; i < counter; i++) {
            word += " ";
        }
        return word;
    }

    public static void saveToExcel()
    {
        try {
            System.out.println("Saving started");
            WritableWorkbook workbook = Workbook.createWorkbook(new File("/home/magik/Desktop/Kontakty.xls"));
            WritableSheet sheet = workbook.createSheet("Kontakty", 0);

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    sheet.addCell(new jxl.write.Label(j, i, data[i][j]));
                }
            }

            workbook.write();
            workbook.close();
            System.out.println("File [Kontakty.xsl] saved sucefully.");
        } catch (Exception exc) {
            System.out.println("Unable to save file!");
            System.err.println("Fatal error while saving file:");
            exc.printStackTrace();
        }
    }
}
