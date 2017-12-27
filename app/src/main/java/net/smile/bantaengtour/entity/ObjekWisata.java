package net.smile.bantaengtour.entity;

/**
 * Created by GROUNDMOS on 4/24/2017.
 */

public class ObjekWisata {
    int id;
    String namaWisata,infoDetail,cover,latitude,longitude, alamat;

    public ObjekWisata() {
    }

    public ObjekWisata(int id, String namaWisata, String infoDetail, String cover, String latitude, String longitude, String alamat) {
        this.id = id;
        this.namaWisata = namaWisata;
        this.infoDetail = infoDetail;
        this.cover = cover;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
    }

    public String getInfoDetail() {
        return infoDetail;
    }

    public void setInfoDetail(String infoDetail) {
        this.infoDetail = infoDetail;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
