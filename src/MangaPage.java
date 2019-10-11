import java.util.Arrays;

/**
 * data from the page on which you can view all the chapters of a manga
 * this is more specific than a MangaSearchResult
 */
public class MangaPage {
    private String title;
    private String alternateName;
    private String url;
    private String imageUrl;
    private String releaseDate;
    private String status;
    private String author;
    private String artist;
    private int chapterNum;
    private Chapter[] chapters;

    MangaPage(String url, String imageUrl, String title){
        this.url = url;
        this.imageUrl = imageUrl;
        this.title = title;
        this.chapters = new Chapter[0];
    }

    public String getTitle(){
        return title;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public String getUrl(){
        return url;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthor() {
        return author;
    }

    public String getArtist() {
        return artist;
    }

    public int getChapterNum(){
        return chapterNum;
    }

    public Chapter[] getChapters(){
        return chapters;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setChapters(Chapter[] chaps){
        this.chapters = chaps;
    }

    public void setChapterNum(int chapterNum){
        this.chapterNum = chapterNum;
    }

    @Override
    public String toString() {
        return "MangaPage{" +
                "title='" + title + '\'' +
                ", alternateName='" + alternateName + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", status='" + status + '\'' +
                ", author='" + author + '\'' +
                ", artist='" + artist + '\'' +
                ", chapterNum=" + chapterNum +
                ", chapters=" + Arrays.toString(chapters) +
                '}';
    }
}
