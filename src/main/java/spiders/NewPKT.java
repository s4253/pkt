package spiders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPKT {
    public static String[][] data = new String[8000][6];

    public static void main(String[] arg) throws IOException {

        Locale locale = new Locale("pl", "PL");

        System.out.println(Locale.getDefault());

        URL url = new URL("http://pkt.pl/Szko%C5%82a%20j%C4%99zykowa/4-1/?&splitType=regular&sortBy=relevance&collapsing=true&debug=0&page=1");

        URLConnection urlConnection = url.openConnection();

        BufferedReader htmlPage = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        String text = "";
        String line = "";

        while ((line = htmlPage.readLine()) != null) {
            text += line+"\n";
        }

        System.out.println(text);

        String linkPattern = "<a class=\"trkname_resultsclick";
        System.out.println(linkPattern);

        Pattern pattern = Pattern.compile(linkPattern);
        Matcher matcher = pattern.matcher(text);


        while(matcher.find())  {
            System.out.println(text.substring(matcher.start(), matcher.end()) );
        }

    }
}
