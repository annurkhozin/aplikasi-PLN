package com.kalinesia.tubesmobile.complaint;


import java.io.Serializable;

public class ComplaintModel implements Serializable {
    String idPelanggan, namaPelanggan, latitude, longitude, alamatPelanggan,isiLaporan;


    int statusLaporan;

    public ComplaintModel() {

    }

    public ComplaintModel(String idPelanggan, String namaPelanggan, String latitude, String longitude, String alamatPelanggan, String isiLaporan,int statusLaporan) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alamatPelanggan = alamatPelanggan;
        this.isiLaporan = isiLaporan;
        this.statusLaporan = statusLaporan;
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
