/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.PelangganDAO;
import model.Pelanggan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormPelanggan extends JFrame {
    private JTextField tfNama, tfAlamat, tfKontak;
    private JButton btnSimpan, btnReset, btnKembali;
    private JTable table;
    private DefaultTableModel tableModel;

    private PelangganDAO dao = new PelangganDAO();
    private int selectedId = -1;

    public FormPelanggan() {
        setTitle("Form Pelanggan - Toko ATK");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color biruLembut = new Color(173, 216, 230);
        Font font = new Font("Segoe UI", Font.PLAIN, 13);

        // === Panel Form Input ===
        JPanel panelForm = new JPanel(new GridLayout(3, 1, 5, 5));
        panelForm.setBackground(biruLembut);
        panelForm.setBorder(BorderFactory.createTitledBorder("Input Data Pelanggan"));

        tfNama = new JTextField(20);
        tfAlamat = new JTextField(20);
        tfKontak = new JTextField(20);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.setBackground(biruLembut);
        row1.add(new JLabel("Nama:"));
        row1.add(tfNama);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.setBackground(biruLembut);
        row2.add(new JLabel("Alamat:"));
        row2.add(tfAlamat);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.setBackground(biruLembut);
        row3.add(new JLabel("Kontak:"));
        row3.add(tfKontak);

        tfNama.setFont(font);
        tfAlamat.setFont(font);
        tfKontak.setFont(font);

        panelForm.add(row1);
        panelForm.add(row2);
        panelForm.add(row3);

        // === Panel Tombol ===
        JPanel panelButton = new JPanel();
        panelButton.setBackground(biruLembut);

        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");

        panelButton.add(btnSimpan);
        panelButton.add(btnReset);
        panelButton.add(btnKembali);

        // === Tabel Data Pelanggan ===
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Alamat", "Kontak"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Tambah ke Frame ===
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // === Event ===
        btnSimpan.addActionListener(e -> simpanPelanggan());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            new FormMenuUtama().setVisible(true);
            dispose();
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedId = (int) tableModel.getValueAt(row, 0);
                    tfNama.setText(tableModel.getValueAt(row, 1).toString());
                    tfAlamat.setText(tableModel.getValueAt(row, 2).toString());
                    tfKontak.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        tampilkanData();
    }

    private void tampilkanData() {
        tableModel.setRowCount(0);
        List<Pelanggan> list = dao.getAllPelanggan();
        for (Pelanggan p : list) {
            Object[] row = {
                    p.getIdPelanggan(), p.getNama(), p.getAlamat(), p.getKontak()
            };
            tableModel.addRow(row);
        }
    }

    private void simpanPelanggan() {
        String nama = tfNama.getText();
        String alamat = tfAlamat.getText();
        String kontak = tfKontak.getText();

        if (!kontak.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Kontak hanya boleh berisi angka!");
        return;
}


        Pelanggan p = new Pelanggan();
        p.setNama(nama);
        p.setAlamat(alamat);
        p.setKontak(kontak);

        if (selectedId == -1) {
            dao.insertPelanggan(p);
            JOptionPane.showMessageDialog(this, "Pelanggan ditambahkan!");
        } else {
            p.setIdPelanggan(selectedId);
            dao.updatePelanggan(p);
            JOptionPane.showMessageDialog(this, "Pelanggan diperbarui!");
        }

        tampilkanData();
        resetForm();
    }

    private void resetForm() {
        tfNama.setText("");
        tfAlamat.setText("");
        tfKontak.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPelanggan().setVisible(true));
    }
}



