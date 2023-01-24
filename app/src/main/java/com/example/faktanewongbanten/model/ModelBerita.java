package com.example.faktanewongbanten.model;

public class ModelBerita {
    public String id_berita;
    public String url_thumbnail;
    public String judul;
    public String isi;
    public String author;
    public String kategori;
    public String tanggal_dibuat;
    public String tanggal_diupdate;

    public String getId_berita() {
        return id_berita;
    }

    public void setId_berita(String id_berita) {
        this.id_berita = id_berita;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal_dibuat() {
        return tanggal_dibuat;
    }

    public void setTanggal_dibuat(String tanggal_dibuat) {
        this.tanggal_dibuat = tanggal_dibuat;
    }

    public String getTanggal_diupdate() {
        return tanggal_diupdate;
    }

    public void setTanggal_diupdate(String tanggal_diupdate) {
        this.tanggal_diupdate = tanggal_diupdate;
    }
}
