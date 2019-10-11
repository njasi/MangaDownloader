/**
 * all the data represented by a chapter
 */

import java.util.ArrayList;

public class Chapter {
    private String name;
    private String url;
    private String number; // not an int because sometimes they can be 1 - 2 or 1.4 etc
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
        number = num;
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

    public ArrayList<String> getImages(){
        return images;
    }

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
