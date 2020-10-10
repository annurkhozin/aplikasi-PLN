package com.kalinesia.tubesmobile.Customer;


import java.io.Serializable;

public class CustomerModel implements Serializable {
    String idPelanggan, namaPelanggan, latitude, longitude, alamatPelanggan;

    public CustomerModel() {
    }

    public CustomerModel(String idPelanggan, String namaPelanggan, String latitude, String longitude, String alamatPelanggan) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alamatPelanggan = alamatPelanggan;
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
}
