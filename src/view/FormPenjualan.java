/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class FormPenjualan extends JFrame {

    private JTextField tfNama, tfKontak, tfAlamat, tfJumlah;
    private JComboBox<Barang> cbBarang;
    private JButton btnBeli, btnCheckOut, btnReset, btnKembali;
    private JTable tableItem;
    private DefaultTableModel tableModel;

    private List<Barang> daftarBarang;
    private BarangDAO barangDAO = new BarangDAO();
    private PelangganDAO pelangganDAO = new PelangganDAO();
    private PenjualanDAO penjualanDAO = new PenjualanDAO();
    private DetailPenjualanDAO detailDAO = new DetailPenjualanDAO();

    public FormPenjualan() {
        setTitle("Form Penjualan - Toko ATK");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color biruLembut = new Color(173, 216, 230);
        Font font = new Font("Segoe UI", Font.PLAIN, 13);

        // === Panel Form Pelanggan ===
        JPanel panelPelanggan = new JPanel(new GridLayout(3, 1, 5, 5));
        panelPelanggan.setBorder(BorderFactory.createTitledBorder("Data Pelanggan"));
        panelPelanggan.setBackground(biruLembut);

        tfNama = new JTextField(20);
        tfKontak = new JTextField(20);
        tfAlamat = new JTextField(20);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.setBackground(biruLembut);
        row1.add(new JLabel("Nama:"));
        row1.add(tfNama);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.setBackground(biruLembut);
        row2.add(new JLabel("Kontak:"));
        row2.add(tfKontak);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.setBackground(biruLembut);
        row3.add(new JLabel("Alamat:"));
        row3.add(tfAlamat);

        panelPelanggan.add(row1);
        panelPelanggan.add(row2);
        panelPelanggan.add(row3);

        tfNama.setFont(font);
        tfKontak.setFont(font);
        tfAlamat.setFont(font);

        // === Panel Tambah Barang ===
        JPanel panelBarang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBarang.setBorder(BorderFactory.createTitledBorder("Tambah Barang"));
        panelBarang.setBackground(biruLembut);

        daftarBarang = barangDAO.getAllBarang();
        cbBarang = new JComboBox<>(daftarBarang.toArray(new Barang[0]));
        tfJumlah = new JTextField(5);
        btnBeli = new JButton("Beli");

        panelBarang.add(new JLabel("Barang:"));
        panelBarang.add(cbBarang);
        panelBarang.add(new JLabel("Jumlah:"));
        panelBarang.add(tfJumlah);
        panelBarang.add(btnBeli);

        cbBarang.setFont(font);
        tfJumlah.setFont(font);

        // === Tabel Keranjang Penjualan ===
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Jumlah", "Harga", "Subtotal"}, 0);
        tableItem = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableItem);

        // === Panel Tombol ===
        JPanel panelButton = new JPanel();
        panelButton.setBackground(biruLembut);
        btnCheckOut = new JButton("Check Out");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");
        panelButton.add(btnCheckOut);
        panelButton.add(btnReset);
        panelButton.add(btnKembali);

        // === Tambahkan Komponen ke Frame ===
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelPelanggan, BorderLayout.NORTH);
        panelAtas.add(panelBarang, BorderLayout.SOUTH);

        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // === Event ===
        btnBeli.addActionListener(e -> tambahItem());
        btnCheckOut.addActionListener(e -> simpanPenjualan());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            dispose();
            new FormMenuUtama().setVisible(true);
        });
    }

    private void tambahItem() {
        Barang barang = (Barang) cbBarang.getSelectedItem();
        int jumlah;

        try {
            jumlah = Integer.parseInt(tfJumlah.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
            return;
        }

        if (jumlah <= 0) {
            JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!");
            return;
        }

        if (jumlah > barang.getStok()) {
            JOptionPane.showMessageDialog(this, "Stok tidak mencukupi!");
            return;
        }

        double subtotal = jumlah * barang.getHarga();
        tableModel.addRow(new Object[]{
                barang.getIdBarang(),
                barang.getNama(),
                jumlah,
                barang.getHarga(),
                subtotal
        });

        tfJumlah.setText("");
    }

    private void simpanPenjualan() {
        if (tfNama.getText().isEmpty() || tfKontak.getText().isEmpty() || tfAlamat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data pelanggan wajib diisi!");
            return;
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tambahkan minimal satu barang ke tabel!");
            return;
        }

        // Simpan pelanggan
        Pelanggan pelanggan = new Pelanggan();
        pelanggan.setNama(tfNama.getText());
        pelanggan.setKontak(tfKontak.getText());
        pelanggan.setAlamat(tfAlamat.getText());
        int idPelanggan = pelangganDAO.insertAndReturnId(pelanggan);

        // Simpan penjualan
        Penjualan penjualan = new Penjualan();
        penjualan.setIdPelanggan(idPelanggan);
        penjualan.setTanggal(new Date());
        penjualan.setIdPetugas(1); // hardcode sementara
        double total = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (double) tableModel.getValueAt(i, 4);
        }

        penjualan.setTotal(total);
        int idPenjualan = penjualanDAO.insertPenjualan(penjualan);

        // Detail dan update stok
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int idBarang = (int) tableModel.getValueAt(i, 0);
            int jumlah = (int) tableModel.getValueAt(i, 2);
            double subtotal = (double) tableModel.getValueAt(i, 4);

            DetailPenjualan detail = new DetailPenjualan();
            detail.setIdPenjualan(idPenjualan);
            detail.setIdBarang(idBarang);
            detail.setJumlah(jumlah);
            detail.setSubtotal(subtotal);
            detailDAO.insert(detail);

            // Kurangi stok
            Barang barang = barangDAO.getBarangById(idBarang);
            barangDAO.updateStok(idBarang, barang.getStok() - jumlah);
        }

        JOptionPane.showMessageDialog(this, "Penjualan berhasil disimpan!");
        resetForm();
    }

    private void resetForm() {
        tfNama.setText("");
        tfKontak.setText("");
        tfAlamat.setText("");
        tfJumlah.setText("");
        tableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPenjualan().setVisible(true));
    }
}





