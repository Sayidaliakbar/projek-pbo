/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Model Barang - merepresentasikan data dari tabel 'barang' di database toko ATK.
 */
public class Barang {
    private int idBarang;
    private String nama;
    private String jenis;
    private double harga;
    private int stok;

    // Konstruktor tanpa parameter (dibutuhkan oleh JTable dan framework lain)
    public Barang() {}

    // Konstruktor lengkap (digunakan saat mengambil data dari database)
    public Barang(int idBarang, String nama, String jenis, double harga, int stok) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.jenis = jenis;
        this.harga = harga;
        this.stok = stok;
    }

    // Konstruktor tanpa idBarang (digunakan saat insert data baru)
    public Barang(String nama, String jenis, double harga, int stok) {
        this.nama = nama;
        this.jenis = jenis;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter dan Setter
    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    /**
     * Menampilkan nama barang saat digunakan dalam JComboBox
     */
    @Override
    public String toString() {
        return nama;
    }
}




