/**
 * all the data represented by a chapter
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Chapter {
    private String name;
    private String url;
    private String number; // not an int because sometimes they can be 1 - 2 or 1.4 etc
    private String seriesName;
    private ArrayList<String> images;

    Chapter(String url){
        this.url = url;
        this.images = new ArrayList<>();
    }

    Chapter(String url, String name) {
        this(url);
        this.name = name;
    }

    Chapter(String url, String name, int numPages) {
        this(url);
        this.name = name;
        this.images = new ArrayList<>(numPages);
    }

    public void addImage(String url){
        images.add(url);
    }

    public void setNumber(String num) {
        number = num.replace("-",".");
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getNumber(){
        return number;
    }

    public double getNumberValue(){
        return Double.parseDouble(number);
    }

    public ArrayList<String> getImages(){
        return images;
    }

    public ArrayList<BufferedImage> urlsToImages() throws MalformedURLException, IOException {
        ArrayList<BufferedImage> bImgs = new ArrayList<>(0);
        for(String image: images){
            bImgs.add(ImageIO.read(new URL(image)));
        }
        return bImgs;
    }

//    public

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", number='" + number + '\'' +
                ", images=" + images +
                '}';
    }
}
