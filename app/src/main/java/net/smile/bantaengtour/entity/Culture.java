package net.smile.bantaengtour.entity;

/**
 * Created by GROUNDMOS on 4/26/2017.
 */

public class Culture {

    int id;
    String namaCilture, detailCulture, cover;

    public Culture(int id, String namaCilture, String detailCulture, String cover) {
        this.id = id;
        this.namaCilture = namaCilture;
        this.detailCulture = detailCulture;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaCilture() {
        return namaCilture;
    }

    public void setNamaCilture(String namaCilture) {
        this.namaCilture = namaCilture;
    }

    public String getDetailCulture() {
        return detailCulture;
    }

    public void setDetailCulture(String detailCulture) {
        this.detailCulture = detailCulture;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
