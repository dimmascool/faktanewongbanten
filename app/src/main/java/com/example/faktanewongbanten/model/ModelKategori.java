package com.example.faktanewongbanten.model;

public class ModelKategori {
    public String id_kategori;
    public String kategori;
    public String url_gambar_kategori;
    public String deskripsi;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUrl_gambar_kategori() {
        return url_gambar_kategori;
    }

    public void setUrl_gambar_kategori(String url_gambar_kategori) {
        this.url_gambar_kategori = url_gambar_kategori;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
