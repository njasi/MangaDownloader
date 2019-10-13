import java.awt.desktop.SystemEventListener;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class tester {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        timer(start);
        try {
            test();
        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F;
        System.out.println("The test took " + sec + " seconds! (this does not account for threads finish time)");
    }


    private static void test() throws Exception{
        MangaDownloader readerTest = new MangaReaderDownloader();
        MangaPage res = readerTest.getManga("https://www.mangareader.net/naruto");
        readerTest.downloadManga(res,1,100, MangaDownloader.FIT_PAGES_TO_IMAGES);
    }

    private static void timer(Long startTime){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                long end = System.currentTimeMillis();
                float sec = (end - startTime) / 1000F;
                System.out.println("The test took " + sec + " seconds! (including threads)");
            }
        });
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
