package spiders;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Jacek Malolepszy
 * Date: 11.01.15
 * Time: 20:51
 */
public class Ceidg {

    public static void main(String[] args) throws IOException {

        String baseDir = "/home/magik/java/ptk/pages";

        String linkBegins = "class=\"searchITSpanBold\"><a";

        File file = new File(baseDir);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".htm");
            }
        };



        File[] fileHtmList = file.listFiles(filter);

        String baseUrl = "https://prod.ceidg.gov.pl/CEIDG/ceidg.public.ui/";

        for (File fileItem : fileHtmList) {

            FileReader in = new FileReader(fileItem.getAbsolutePath());
            BufferedReader br = new BufferedReader(in);

            String text = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                text += line + "\n";
            }

//            System.out.println(text);

            String linkPattern = linkBegins;
            System.out.println(linkPattern);

            Pattern pattern = Pattern.compile(linkPattern);
            Matcher matcher = pattern.matcher(text);


            while (matcher.find()) {
                System.out.println(text.substring(matcher.start()+34, matcher.end()+113));
            }

        }

    }
}
