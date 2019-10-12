import org.apache.pdfbox.exceptions.COSVisitorException;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public abstract class MangaDownloader {
    abstract  MangaSearchResult[] search(String  term) throws IOException;

    public MangaPage getManga(MangaSearchResult manga) throws IOException {
        return getManga(manga.getUrl());
    }

    abstract MangaPage getManga(String url) throws IOException;

    public Chapter getChapter(Chapter chap) throws IOException{
        return getChapter(chap.getUrl());
    }

    abstract Chapter getChapter(String url) throws IOException;

    void downloadManga(MangaPage manga) throws IOException{
//        will call download chapter in a loop / thread pattern
//        ie it will not need to be downloader specific
    }

    void downloadChapter(Chapter chap) throws IOException, COSVisitorException{
        // TODO
    }

    void chapterToPdf(Chapter chap) throws IOException{
        // TODO
    }
}
