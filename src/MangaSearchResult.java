/**
 * You get an array of these when you call a manga downloaders search function
 */
public class MangaSearchResult {
    private String url;
    private String imageUrl;
    private String chapterNum;
    private String title;
    private String author;


    MangaSearchResult(String url, String imageUrl, String title) {
        this.url = url;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    MangaSearchResult(String url, String imageUrl, String title, String chapterNum) {
        this(url, imageUrl, title);
        this.chapterNum = chapterNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getChapterCount() {
        return chapterNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    @Override
    public String toString() {
        return "MangaSearchResult{" +
                "url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", chapterNum='" + chapterNum + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
