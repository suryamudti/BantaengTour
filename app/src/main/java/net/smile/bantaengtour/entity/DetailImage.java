package net.smile.bantaengtour.entity;

/**
 * Created by GROUNDMOS on 4/24/2017.
 */

public class DetailImage {
    int id;
    String urlGambar;

    public DetailImage(int id, String urlGambar) {
        this.id = id;
        this.urlGambar = urlGambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }
}
