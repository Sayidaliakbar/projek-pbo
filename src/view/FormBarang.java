/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view;

import dao.BarangDAO;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormBarang extends JFrame {
    private JTextField tfNama, tfJenis, tfHarga, tfStok;
    private JButton btnSimpan, btnHapus, btnReset, btnKembali;
    private JTable table;
    private DefaultTableModel tableModel;

    private BarangDAO dao = new BarangDAO();
    private int selectedId = -1;

    public FormBarang() {
        setTitle("Form Barang - Toko ATK");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Warna dan font
        Color bgColor = new Color(173, 216, 230);
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 13);

        // Panel form (input data) - pakai GridBagLayout horizontal
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(bgColor);
        panelForm.setBorder(BorderFactory.createTitledBorder("Input Data Barang"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        tfNama = new JTextField(20);
        tfJenis = new JTextField(20);
        tfHarga = new JTextField(20);
        tfStok = new JTextField(20);
        tfNama.setFont(fontInput);
        tfJenis.setFont(fontInput);
        tfHarga.setFont(fontInput);
        tfStok.setFont(fontInput);

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Nama Barang:"), gbc);
        gbc.gridx = 1;
        panelForm.add(tfNama, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Jenis:"), gbc);
        gbc.gridx = 1;
        panelForm.add(tfJenis, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Harga:"), gbc);
        gbc.gridx = 1;
        panelForm.add(tfHarga, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Stok:"), gbc);
        gbc.gridx = 1;
        panelForm.add(tfStok, gbc);

        // Panel tombol
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelButton.setBackground(bgColor);
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");

        panelButton.add(btnSimpan);
        panelButton.add(btnHapus);
        panelButton.add(btnReset);
        panelButton.add(btnKembali);

        // Panel gabungan atas
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setBackground(bgColor);
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelButton, BorderLayout.SOUTH);

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Jenis", "Harga", "Stok"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Layout ke frame
        add(panelAtas, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Event
        btnSimpan.addActionListener(e -> simpanBarang());
        btnHapus.addActionListener(e -> hapusBarang());
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
                    tfJenis.setText(tableModel.getValueAt(row, 2).toString());
                    tfHarga.setText(tableModel.getValueAt(row, 3).toString());
                    tfStok.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        tampilkanData();
    }

    private void tampilkanData() {
        tableModel.setRowCount(0);
        List<Barang> list = dao.getAllBarang();
        for (Barang b : list) {
            tableModel.addRow(new Object[]{
                b.getIdBarang(),
                b.getNama(),
                b.getJenis(),
                b.getHarga(),
                b.getStok()
            });
        }
    }

    private void simpanBarang() {
        try {
            String nama = tfNama.getText();
            String jenis = tfJenis.getText();
            double harga = Double.parseDouble(tfHarga.getText());
            int stok = Integer.parseInt(tfStok.getText());

            Barang b = new Barang(nama, jenis, harga, stok);
            if (selectedId == -1) {
                dao.insertBarang(b);
                JOptionPane.showMessageDialog(this, "Barang ditambahkan!");
            } else {
                b.setIdBarang(selectedId);
                dao.updateBarang(b);
                JOptionPane.showMessageDialog(this, "Barang diperbarui!");
            }

            tampilkanData();
            resetForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus angka!");
        }
    }

    private void hapusBarang() {
        if (selectedId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteBarang(selectedId);
                tampilkanData();
                resetForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!");
        }
    }

    private void resetForm() {
        tfNama.setText("");
        tfJenis.setText("");
        tfHarga.setText("");
        tfStok.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormBarang().setVisible(true));
    }
}







