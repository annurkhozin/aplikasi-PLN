package com.kalinesia.tubesmobile.Maps;


import java.io.Serializable;

public class HomeModel implements Serializable {
    String idPelanggan;
    String namaPelanggan;
    String latitude;
    String longitude;
    String alamatPelanggan;
    String isiLaporan;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;


    int statusLaporan;

    public HomeModel() {

    }

    public HomeModel(String idPelanggan, String namaPelanggan, String latitude, String longitude, String alamatPelanggan, String isiLaporan,int statusLaporan,String key) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alamatPelanggan = alamatPelanggan;
        this.isiLaporan = isiLaporan;
        this.statusLaporan = statusLaporan;
        this.key = key;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
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

    public String getAlamatPelanggan() {
        return alamatPelanggan;
    }

    public void setAlamatPelanggan(String alamatPelanggan) {
        this.alamatPelanggan = alamatPelanggan;
    }
    public String getIsiLaporan() {
        return isiLaporan;
    }

    public void setIsiLaporan(String isiLaporan) {
        this.isiLaporan = isiLaporan;
    }
    public int getStatusLaporan() {
        return statusLaporan;
    }

    public void setStatusLaporan(int statusLaporan) {
        this.statusLaporan = statusLaporan;
    }


}
