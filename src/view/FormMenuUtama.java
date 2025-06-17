/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;

public class FormMenuUtama extends JFrame {

    private JButton btnBarang, btnPetugas, btnPenjualan, btnPelanggan, btnLogout, btnDetailPenjualan;

    public FormMenuUtama() {
        setTitle("Menu Utama - Toko ATK");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Panel Kiri: Gambar ===
        JPanel panelKiri = new JPanel();
        panelKiri.setBackground(Color.WHITE);
        panelKiri.setPreferredSize(new Dimension(250, 350));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/gambar/pngegg.png"));
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel lblGambar = new JLabel(new ImageIcon(img));
            panelKiri.add(lblGambar);
        } catch (Exception e) {
            panelKiri.add(new JLabel("Gambar tidak ditemukan"));
        }

        // === Panel Kanan: Tombol ===
        JPanel panelKanan = new JPanel();
        panelKanan.setBackground(new Color(173, 216, 230)); // biru lembut
        panelKanan.setLayout(null);

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        btnBarang = new JButton("Daftar Barang");
        btnBarang.setFont(font);
        btnBarang.setBounds(100, 20, 180, 30);
        btnBarang.setFocusPainted(false); // menghilangkan garis fokus
        panelKanan.add(btnBarang);

        btnPetugas = new JButton("Daftar Petugas");
        btnPetugas.setFont(font);
        btnPetugas.setBounds(100, 60, 180, 30);
        btnPetugas.setFocusPainted(false);
        panelKanan.add(btnPetugas);

        btnPenjualan = new JButton("Transaksi");
        btnPenjualan.setFont(font);
        btnPenjualan.setBounds(100, 100, 180, 30);
        btnPenjualan.setFocusPainted(false);
        panelKanan.add(btnPenjualan);

        btnPelanggan = new JButton("Daftar Pelanggan");
        btnPelanggan.setFont(font);
        btnPelanggan.setBounds(100, 140, 180, 30);
        btnPelanggan.setFocusPainted(false);
        panelKanan.add(btnPelanggan);

        btnDetailPenjualan = new JButton("Detail Penjualan");
        btnDetailPenjualan.setFont(font);
        btnDetailPenjualan.setBounds(100, 180, 180, 30);
        btnDetailPenjualan.setFocusPainted(false);
        panelKanan.add(btnDetailPenjualan);

        btnLogout = new JButton("Logout");
        btnLogout.setFont(font);
        btnLogout.setBounds(100, 220, 180, 30);
        btnLogout.setFocusPainted(false);
        panelKanan.add(btnLogout);

        // Tambahkan ke frame
        add(panelKiri, BorderLayout.WEST);
        add(panelKanan, BorderLayout.CENTER);

        // Aksi tombol
        btnBarang.addActionListener(e -> {
            new FormBarang().setVisible(true);
            dispose();
        });

        btnPetugas.addActionListener(e -> {
            new FormPetugas().setVisible(true);
            dispose();
        });

        btnPenjualan.addActionListener(e -> {
            new FormPenjualan().setVisible(true);
            dispose();
        });

        btnPelanggan.addActionListener(e -> {
            new FormPelanggan().setVisible(true);
            dispose();
        });

        btnDetailPenjualan.addActionListener(e -> {
            new FormDetailPenjualan().setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah kamu yakin ingin logout?",
                    "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (konfirmasi == JOptionPane.YES_OPTION) {
                new FormLogin().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormMenuUtama().setVisible(true));
    }
}










