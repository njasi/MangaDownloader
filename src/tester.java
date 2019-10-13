import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class tester {
    public static void main(String[] args){
        try {
//            MangaDownloader readerTest = new MangaReaderDownloader();
//////            readerTest.search("naruto");
////            MangaPage res = readerTest.getManga("https://www.mangareader.net/naruto");
//////            System.out.println(res.getChapters()[0].getUrl());
//            Chapter chap = readerTest.getChapter("https://www.mangareader.net/naruto/1");
////            System.out.println(chap.toString());
////            MangaDownloader.compileChapterImages(chap);
//            Savers.stitchAndUnstitchChapterToPDF(chap, "stitchAndUnstitchTest1.pdf");
            Savers.mkDir("Downloaded");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This is mainly for testing purposes
     * @param url the url of the page you want the html from
     * @return  html of  the requested page
     * @throws Exception
     */
    public static String getHtml(String url) throws Exception{
        try {
            URLConnection connection =  new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            return content;
        }catch ( Exception ex ) {
            throw ex;
        }
    }
}
