/**
 * all the data represented by a chapter
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Chapter {
    private String name;
    private String url;
    private String number; // not an int because sometimes they can be 1 - 2 or 1.4 etc
    private String seriesName;
    private ArrayList<String> images;
    private boolean referFromUrl = false;

    Chapter(String url) {
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

    public void addImage(String url) {
        images.add(url);
    }

    public void setNumber(String num) {
        number = num.replace("-", ".");
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public void setReferFromUrl(boolean referFromUrl) {
        this.referFromUrl = referFromUrl;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getNumber() {
        return number;
    }

    public double getNumberValue() {
        return Double.parseDouble(number);
    }

    public boolean getReferFromUrl() {
        return referFromUrl;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ArrayList<BufferedImage> urlsToImages() throws MalformedURLException, IOException {
        ArrayList<BufferedImage> bImgs = new ArrayList<>(0);
        for (String image : images) {
            URL url = new URL(image);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.addRequestProperty("REFERER", this.url);
            InputStream in = connect.getInputStream();
            bImgs.add(ImageIO.read(in));
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
