package net.smile.bantaengtour.entity;

/**
 * Created by GROUNDMOS on 4/28/2017.
 */

public class CultureDetail {
    int id;
    String url;

    public CultureDetail(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
