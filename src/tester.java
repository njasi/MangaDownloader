import java.awt.desktop.SystemEventListener;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class tester {

    private static long start;

    public static void main(String[] args) {
        try {
            MangaDownloader test = new WebtoonDownloader();
            startTimer("SEARCH");
            MangaSearchResult firstResult = testSearch(test);
            endTimer();
            startTimer("GET MANGA");
            MangaPage page = testGetManga(test, firstResult);
            endTimer();
            startTimer("GET CHAPTER");
            Chapter chap = testGetChapter(test, page);
            endTimer();
            startTimer("DOWNLOAD");
            testDownloadChapter(test, chap);
            endTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startTimer(String test) {
        long starter = System.currentTimeMillis();
        start = System.currentTimeMillis();
//        timer(starter, test);
    }

    private static void endTimer() {
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F;
        System.out.println("\nThe test took " + sec + " seconds! (this does not account for threads finish time)");
    }

//    private static void timer(Long startTime, String test) {
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                long end = System.currentTimeMillis();
//                float sec = (end - startTime) / 1000F;
//                System.out.println("The " + test + " test took " + sec + " seconds! (including threads)");
//            }
//        });
//    }

    private static void printTitle(String title) {
        System.out.println("\n=========    " + title + "    =========");
    }

    private static MangaSearchResult testSearch(MangaDownloader test) throws Exception {
        printTitle("SEARCH TEST");
        MangaSearchResult[] pages = test.searchAll("god ");
        for (MangaSearchResult res : pages) {
            System.out.println(res.toString());
        }
        return pages[1];
    }

    private static MangaPage testGetManga(MangaDownloader test, MangaSearchResult res) throws Exception {
        printTitle("GET MANGA TEST");
        MangaPage page = test.getManga(res);
        System.out.println(page.toString());
        return page;
    }

    private static Chapter testGetChapter(MangaDownloader test, MangaPage page) throws Exception {
        printTitle("GET CHAPTER TEST");
        Chapter chap = test.getChapter(page.getChapters()[0]);
        System.out.println(chap.toString());
        return chap;
    }

    private static void testDownloadChapter(MangaDownloader test, Chapter chap) throws Exception {
        printTitle("DOWNLOAD CHAPTER TEST");
        test.downloadChapter(chap, MangaDownloader.FIT_PAGES_TO_IMAGES);
    }


    /**
     * This is mainly for testing purposes
     *
     * @param url the url of the page you want the html from
     * @return html of  the requested page
     * @throws Exception
     */
    public static String getHtml(String url) throws Exception {
        try {
            URLConnection connection = new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            return content;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
