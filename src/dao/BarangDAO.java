/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.Koneksi;
import model.Barang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    private Connection conn;

    public BarangDAO() {
        conn = Koneksi.getConnection();
    }

    // Ambil semua data barang
    public List<Barang> getAllBarang() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Barang b = new Barang();
                b.setIdBarang(rs.getInt("id_barang"));
                b.setNama(rs.getString("nama"));
                b.setJenis(rs.getString("jenis"));
                b.setHarga(rs.getDouble("harga"));
                b.setStok(rs.getInt("stok"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data barang: " + e.getMessage());
        }

        return list;
    }

    // Tambah barang
    public void insertBarang(Barang b) {
        String sql = "INSERT INTO barang (nama, jenis, harga, stok) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getNama());
            ps.setString(2, b.getJenis());
            ps.setDouble(3, b.getHarga());
            ps.setInt(4, b.getStok());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan barang: " + e.getMessage());
        }
    }

    // Update barang
    public void updateBarang(Barang b) {
        String sql = "UPDATE barang SET nama=?, jenis=?, harga=?, stok=? WHERE id_barang=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getNama());
            ps.setString(2, b.getJenis());
            ps.setDouble(3, b.getHarga());
            ps.setInt(4, b.getStok());
            ps.setInt(5, b.getIdBarang());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal mengubah data barang: " + e.getMessage());
        }
    }

    // Hapus barang
    public void deleteBarang(int id) {
        String sql = "DELETE FROM barang WHERE id_barang=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus barang: " + e.getMessage());
        }
    }

    // Ambil data barang berdasarkan ID
    public Barang getBarangById(int id) {
        String sql = "SELECT * FROM barang WHERE id_barang=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Barang b = new Barang();
                b.setIdBarang(rs.getInt("id_barang"));
                b.setNama(rs.getString("nama"));
                b.setJenis(rs.getString("jenis"));
                b.setHarga(rs.getDouble("harga"));
                b.setStok(rs.getInt("stok"));
                return b;
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil barang by ID: " + e.getMessage());
        }

        return null;
    }

    // Update stok barang
    public void updateStok(int idBarang, int stokBaru) {
        String sql = "UPDATE barang SET stok = ? WHERE id_barang = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stokBaru);
            ps.setInt(2, idBarang);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal update stok: " + e.getMessage());
        }
    }

    // Kurangi stok barang
    public void kurangiStok(int idBarang, int jumlahDikurangi) {
        Barang b = getBarangById(idBarang);
        if (b != null) {
            int stokBaru = b.getStok() - jumlahDikurangi;
            if (stokBaru < 0) stokBaru = 0;
            updateStok(idBarang, stokBaru);
        }
    }
}




