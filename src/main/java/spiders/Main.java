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

public class Main {
    public static String[][] data = new String[8000][6];

    public static void main(String[] arg) throws IOException {
        int counter = 0;

        try {
            for (int i = 1; i <= 1; i++) {
                URL url = new URL("http://www.pkt.pl/firmy/warszawa/q_szko%C5%82a+j%C4%99zykowa/" + (i));
                URLConnection urlConnection = url.openConnection();
                BufferedReader htmlPage = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

                String text = "";
                String line = "";
                while ((line = htmlPage.readLine()) != null) {
                    text += line;
                }

                String test = "openPreview";

                String tmp;
                Pattern pattern = Pattern.compile(test);
                Matcher matcher = pattern.matcher(text);
                String nazwa = "";
                String zip = "";
                String miasto = "";
                String ulica = "";
                String telefon = "";
                String email = "";
                while (matcher.find()) {

                    tmp = text.substring(matcher.start(), matcher.end() + 3000);
//                    System.out.println(tmp);
                    tmp = tmp.substring(tmp.indexOf("\'>") + 2);
                    try {
                        try {
                        tmp = tmp.substring(0, tmp.indexOf("openPreview") );
                        }catch(Exception e) {}
                        nazwa = tmp.substring(0, tmp.indexOf("</a>"));
                        tmp = tmp.substring(tmp.indexOf("postal-code'>") + 13);
                        zip = tmp.substring(0, tmp.indexOf("</span>"));
                        tmp = tmp.substring(tmp.indexOf("'locality'>") + 11);
                        miasto = tmp.substring(0, tmp.indexOf("</span"));
                        tmp = tmp.substring(tmp.indexOf("address'>") + 9);
                        ulica = tmp.substring(0, tmp.indexOf("</span"));
                        tmp = tmp.substring(tmp.indexOf("tel\">") + 5);
                        telefon = tmp.substring(0, tmp.indexOf("</p>"));
                    } catch (Exception e) {
                    }

                    try {
                        if (tmp.indexOf("'emailResultsLink nofollow'>") != -1) {
                            tmp = tmp.substring(tmp.indexOf("'emailResultsLink nofollow'>") + 28);
                            email = tmp.substring(0, tmp.indexOf("</a>"));
                        } else {
                            email = "";
                        }
                    } catch (Exception e) {
                        email = "";
                    }
                    data[counter][0] = nazwa;
                    data[counter][1] = telefon;
                    data[counter][2] = email;
                    data[counter][3] = ulica;
                    data[counter][4] = miasto;
                    data[counter][5] = zip;
                    System.out.println(align(nazwa, 80) + align(telefon, 30) + align(email, 40) + align(ulica, 50) + align(miasto, 20) + zip);
                    counter++;
                }

                htmlPage.close();
                urlConnection = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveToExcel();
    }

    public static String align(String word, int length) {
        int counter = length - word.length();
        for (int i = 0; i < counter; i++)
            word += " ";
        return word;
    }

    public static void saveToExcel() {
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