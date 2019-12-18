import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;

import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import java.util.ArrayList;

public class MangaReaderDownloader extends MangaDownloader {
    @Override
    public MangaSearchResult[] search(String term, int page) throws IOException {
        page--; // pagination starts at 0 for this one
        page *= 30; // and counts by series not pages (30 series per page)
        Document doc = Jsoup.connect("https://www.mangareader.net/search/?w=" + term + "&p=" + page).get();
        Elements results = doc.getElementsByClass("mangaresultinner");
        MangaSearchResult[] out = new MangaSearchResult[results.size()];
        int i = 0;
        for (Element result : results) {
            Element image = result.getElementsByClass("imgsearchresults").first();
            Element name = result.getElementsByTag("h3").first();
            Element url = result.getElementsByTag("a").first();
            Element chapter = result.getElementsByClass("chapter_count").first();

            String imageUrl = image.attr("style")
                    .replace("background-image:url('", "")
                    .replace("')", "");
            String seriesName = name.text();
            String mangaUrl = "https://www.mangareader.net" + url.attr("href");
            String chapterNum = chapter.text();
            MangaSearchResult temp = new MangaSearchResult(mangaUrl, imageUrl, seriesName, chapterNum);
            out[i] = temp;
            i++;
        }
        return out;
    }

    @Override
    public MangaPage getManga(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element image = doc.getElementsByTag("img").first();
        Element name = doc.getElementsByClass("aname").first();

        String imageUrl = image.attr("src");
        String seriesName = name.text();

        MangaPage scrapedManga = new MangaPage(url, imageUrl, seriesName);

        Element info = doc.getElementById("mangaproperties");
        Elements table = info.getElementsByTag("td");

        scrapedManga.setAlternateName(table.get(3).text());
        scrapedManga.setReleaseDate(table.get(5).text());
        scrapedManga.setStatus(table.get(7).text());
        scrapedManga.setAuthor(table.get(9).text());
        scrapedManga.setArtist(table.get(11).text());

        Elements tds = doc.getElementById("listing").getElementsByTag("td");
        Chapter[] mangaChapters = new Chapter[tds.size() / 2];
        for (int i = 0; i < tds.size(); i += 2) {
            Element currentData = tds.get(i);
            Element a = currentData.child(1);
            String chapterName = currentData.text().replace(a.text() + " : ", "");
            Chapter temp = new Chapter("https://www.mangareader.net" + a.attr("href"), chapterName);
            temp.setNumber(a.attr("href").split("/")[2]);
            mangaChapters[i / 2] = temp;
        }
        scrapedManga.setChapterNum(mangaChapters.length);
        scrapedManga.setChapters(mangaChapters);
        return scrapedManga;
    }

    @Override
    public Chapter getChapter(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        String name = doc.getElementsByTag("h1").first().text();
        String allText = doc.getElementById("selectpage").text().replace(" of ", "");
        int numPages = Integer.parseInt(allText.replace(doc.getElementById("pageMenu").text(), ""));

        Chapter chap = new Chapter(url, name, numPages);

        String[] split = url.split("/");
        chap.setNumber(split[4]);
        chap.setSeriesName(split[3].replace("-", "_"));

        for (int i = 1; i < numPages + 1; i++) {
            Document page = Jsoup.connect(url + "/" + i).get();
            chap.addImage(page.getElementById("img").attr("src"));
        }

        // note that these images follow a pattern (the end number is counting up)
        // page 1 : https://i4.mangareader.net/naruto/2/naruto-1564826.jpg
        // page 2 : https://i6.mangareader.net/naruto/2/naruto-1564827.jpg
        // page 3 : https://i6.mangareader.net/naruto/2/naruto-1564828.jpg
        // I haven't yet seen any chapters that don't follow this pattern
        // but I still wouldn't trust the pattern...
        // ig this could be made faster with threading

        return chap;
    }
}
