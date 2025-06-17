/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Penjualan {
    // ===== Field =====
    private int idPenjualan;   // ID transaksi penjualan
    private int idPetugas;     // ID petugas yang menangani
    private int idPelanggan;   // ID pelanggan
    private Date tanggal;      // Tanggal transaksi
    private double total;      // Total transaksi

    // ===== Constructor =====
    public Penjualan() {
    }

    public Penjualan(int idPenjualan, int idPetugas, int idPelanggan, Date tanggal, double total) {
        this.idPenjualan = idPenjualan;
        this.idPetugas = idPetugas;
        this.idPelanggan = idPelanggan;
        this.tanggal = tanggal;
        this.total = total;
    }

    // ===== Getter & Setter =====
    public int getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public int getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(int idPetugas) {
        this.idPetugas = idPetugas;
    }

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // Optional - berguna jika ingin ditampilkan di combo box atau log
    @Override
    public String toString() {
        return "ID: " + idPenjualan + ", Total: Rp" + total;
    }
}




