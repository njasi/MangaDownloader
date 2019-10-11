import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
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

    void downloadChapter(Chapter chap) throws IOException{
        
    }

    /**
     * stitches all of the chapter images together into one tall image
     * @param chap the chapter to be compiled.
     */
    public static void compileChapterImages(Chapter chap) throws IOException{
        ArrayList<String> imageUrls = chap.getImages();

        BufferedImage[] images = new BufferedImage[imageUrls.size()];
        int maxWidth = 0;
        for(int i = 0; i < imageUrls.size(); i++){
            URL currentUrl = new URL(imageUrls.get(i));
            BufferedImage currentImage =ImageIO.read(currentUrl);
            if(maxWidth < currentImage.getWidth()){
                maxWidth = currentImage.getWidth();
            }
            images[i] = currentImage;
        }

        double totalHeight = 0;
        for(int i = 0; i < images.length; i++){
            totalHeight += ((double)maxWidth / images[i].getWidth()) * images[i].getHeight();
        }

        BufferedImage tallBoi = new BufferedImage(maxWidth, (int)Math.ceil(totalHeight), BufferedImage.TYPE_INT_RGB);
        Graphics2D canvas = tallBoi.createGraphics();
        int currentHeight = 0;
        for(int i = 0; i< images.length; i++){
            Image currentImage = images[i].getScaledInstance(maxWidth,
                    (int)(((double)maxWidth / images[i].getWidth()) * images[i].getHeight()),0);
            canvas.drawImage(currentImage,0, currentHeight, null);
            currentHeight += currentImage.getHeight(null);
        }
        ImageIO.write(tallBoi,"png",new File("test3.png"));
    }

    void chapterToPdf(Chapter chap) throws IOException{
        compileChapterImages(chap);
    }
}
