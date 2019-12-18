import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WebtoonDownloader extends MangaDownloader {
    public static String SITE_URL = "https://www.webtoons.com";

    @Override
    public MangaSearchResult[] search(String term, int page) throws IOException {
        Document doc = Jsoup.connect("https://www.webtoons.com/search?keyword=" + term + "&searchType=WEBTOON&page=" + page).get();
        Elements results = doc.getElementsByClass("card_item");
        MangaSearchResult[] out = new MangaSearchResult[results.size()];
        int i = 0;
        for (Element result : results) {
            Element image = result.getElementsByTag("img").first();
            Element name = result.getElementsByClass("subj").first();
            Element author = result.getElementsByClass("author").first();

            String imageUrl = image.attr("src");
            String seriesName = name.text();
            String mangaUrl = SITE_URL + result.attr("href");
            String authorName = author.text();


            MangaSearchResult temp = new MangaSearchResult(mangaUrl, imageUrl, seriesName);
            temp.setAuthor(authorName);
            out[i] = temp;
            i++;
        }
        return out;
    }

    @Override
    public MangaPage getManga(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String imageBanner = doc.getElementsByClass("thmb").first().children().first().attr("src");

        MangaPage scrapedManga = new MangaPage("", imageBanner, "");
        scrapedManga.setChapters(parseMangaChapters(url));
        return scrapedManga;
    }

    private Chapter[] parseMangaChapters(String url) throws IOException {
        ArrayList<Chapter> total = new ArrayList<>();
        int currentPage = 0;
        Document doc;
        Element last;
        Elements possible = Jsoup.connect(url + "&page=" + currentPage).get()
                .getElementsByClass("paginate")
                .first().children();
        // small toon for testing:    https://www.webtoons.com/en/challenge/hourly-comic-day-2019/list?title_no=266207
        if (possible.size() > 1) { // only one page no flipping
            String href = possible.get(1).attr("href"); // get second link because the starter is = #
            url = SITE_URL + href.substring(0, href.length() - 1);
        }
        do {
            currentPage++;
            doc = Jsoup.connect(url + currentPage).get();
            last = doc.getElementsByClass("paginate").first().children().last();
            total.addAll(parseChapterPage(doc));
        } while (last.className().equals("pg_next") || Integer.parseInt(last.text()) > currentPage);
        Chapter[] out = new Chapter[total.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = total.get(i);
        }
        return out;
    }

    private ArrayList<Chapter> parseChapterPage(Element page) throws IOException {
        ArrayList<Chapter> chaps = new ArrayList<>();
        Elements list = page.getElementById("_listUl").children();
        for (Element chap : list) {
            Element a = chap.children().first();
            Elements aC = a.children();
            String name = aC.get(1).text();
            Chapter temp = new Chapter(a.attr("href"), name);
            temp.setNumber(aC.last().text().replace("#", ""));
            chaps.add(temp);
        }
        return chaps;
    }

    @Override
    public Chapter getChapter(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        String seriesName = doc.getElementsByClass("subj_info").first().children().first().text();
        String epName = doc.getElementsByClass("subj_episode").first().text();
        String cNumber = url.substring(url.indexOf("episode_no=") + 11);

        Chapter chap = new Chapter(url, epName);
        chap.setSeriesName(seriesName);
        chap.setNumber(cNumber);

        Elements images = doc.getElementsByClass("_images");
        for (int i = 0; i < images.size(); i++) {
            System.out.println(images.get(i).toString());
            chap.addImage(images.get(i).attr("data-url"));
        }
        chap.setReferFromUrl(true);

        return chap;
    }
}
