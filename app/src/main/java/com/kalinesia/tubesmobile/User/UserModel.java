package com.kalinesia.tubesmobile.User;


import java.io.Serializable;

public class UserModel implements Serializable {
    String emailPetugas,namaPetugas,passwordPetugas;

    public UserModel() {
    }

    public String getEmailPetugas() {
        return emailPetugas;
    }

    public void setEmailPetugas(String emailPetugas) {
        this.emailPetugas = emailPetugas.replace(",", ".");
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getPasswordPetugas() {
        return passwordPetugas;
    }

    public void setPasswordPetugas(String passwordPetugas) {
        this.passwordPetugas = passwordPetugas;
    }
}
