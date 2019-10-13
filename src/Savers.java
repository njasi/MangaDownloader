import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Savers {
    final static private double LETTER_PAGE_PROPORTION = 11 / 8.5;
    final static private String AUTHOR = "mangadownloader";

    /**
     * stitches all of the chapter images together into one tall image
     * @param chap the chapter to be compiled.
     * @throws IOException
     */
    private static BufferedImage compileChapterToTallImage(Chapter chap) throws IOException{
        ArrayList<String> imageUrls = chap.getImages();

        BufferedImage[] images = new BufferedImage[imageUrls.size()];
        int maxWidth = 0;
        for(int i = 0; i < imageUrls.size(); i++){
            URL currentUrl = new URL(imageUrls.get(i));
            BufferedImage currentImage = ImageIO.read(currentUrl);
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
        return tallBoi;
    }

    /**
     * resizes each image to fit on a standard page
     * @param chap the chapter with the images
     * @param path the path it will be saved at
     */
    public static void chapterToPdfPages(Chapter chap, String path) throws IOException, COSVisitorException{
        imageUrlsToPdfPages(chap.getImages(), path);
    }

    /**
     * resizes each image to fit on a standard page
     * @param images the images (urls) to be written to the pdf
     * @param path the path it will be saved at
     */
    private static void imageUrlsToPdfPages(ArrayList<String> imageUrls, String path) throws IOException, COSVisitorException{
        ArrayList<BufferedImage> images = new ArrayList<>(0);
        for(String image: imageUrls){
            images.add(ImageIO.read(new URL(image)));
        }
        imagesToPdfPages(images, path);
    }

    private static void imagesToPdfPages(ArrayList<BufferedImage> images,String path) throws IOException, COSVisitorException{
        PDDocument document = new PDDocument();

        for(BufferedImage image: images){
            int w = image.getWidth();
            int h = image.getHeight();
            double scale;

            PDPage page = new PDPage(PDPage.PAGE_SIZE_LETTER);
            document.addPage(page);

            if(w * LETTER_PAGE_PROPORTION > h / LETTER_PAGE_PROPORTION){ // wider than tall
                scale = PDPage.PAGE_SIZE_LETTER.getWidth() / w;
            }else{ // taller than wide
                scale = PDPage.PAGE_SIZE_LETTER.getHeight() / h;
            }

            BufferedImage resized = imageTOBufferedImage(image.getScaledInstance(
                    (int)(w * scale),(int)(h * scale),0));
            PDXObjectImage img = new PDJpeg(document, resized);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(img, 0, 0);

            contentStream.close();
        }

        document.save(path);
        document.close();
    }

    /**
     * Makes one tall image and splits it up into standard page sized images (which is then saved with chapterImagesToPdfPages)
     * @param chap
     * @param path
     * @throws IOException
     */
    public static void stitchAndUnstitchChapterToPDF(Chapter chap, String path) throws IOException, COSVisitorException{
        BufferedImage stitched = compileChapterToTallImage(chap);
        ImageIO.write(stitched,"png",new File("Stitched.png"));
        int height = stitched.getHeight();
        int width = stitched.getWidth();
        int step = (int)(LETTER_PAGE_PROPORTION * width);

        ArrayList<BufferedImage> unstitched = new ArrayList<>(0);
        for(int i = 0; i < (int)Math.ceil(height/step) + 1; i++){
            unstitched.add(stitched.getSubimage(0, i * step, width,
                    step * (i + 1) > height ? height - step * i : step));
        }

        imagesToPdfPages(unstitched, path);
    }

    /**\
     * Makes a PDF where each image has its own page
     * @param chap the chapter containing the images
     * @param path the path the pdf will be saved at
     * @throws IOException
     * @throws COSVisitorException
     */
    public static void chapterToResizedPdfPages(Chapter chap, String path) throws IOException, COSVisitorException {
        PDDocument document = new PDDocument();
        ArrayList<String> imageUrls = chap.getImages();
        for(int i = 0 ; i < imageUrls.size(); i++) {
            URL imageSource = new URL(imageUrls.get(i));
            BufferedImage pageImg = ImageIO.read(imageSource);

            float width = pageImg.getWidth();
            float height = pageImg.getHeight();
            PDPage page = new PDPage(new PDRectangle(width, height));
            document.addPage(page);

            PDXObjectImage img = new PDJpeg(document, pageImg);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(img, 0, 0);

            contentStream.close();
        }
        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setAuthor(AUTHOR);

        document.save(path);
        document.close();
    }

    private static BufferedImage imageTOBufferedImage(Image img){
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimg;
    }

    public static boolean mkDir(String path){
        File folder = new File(path);
        if(!folder.exists()){
            return folder.mkdir();
        }
        return true;
    }
}
