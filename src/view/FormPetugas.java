/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.PetugasDAO;
import model.Petugas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormPetugas extends JFrame {
    private JTextField tfNama, tfUsername, tfPassword;
    private JButton btnSimpan, btnHapus, btnReset, btnKembali;
    private JTable table;
    private DefaultTableModel tableModel;

    private PetugasDAO dao = new PetugasDAO();
    private int selectedId = -1;

    public FormPetugas() {
        setTitle("Form Petugas - Toko ATK");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color biruLembut = new Color(173, 216, 230);
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 13);

        // === Panel Form Input (Horizontal layout) ===
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(3, 1, 5, 5));
        panelForm.setBackground(biruLembut);

        tfNama = new JTextField(20);
        tfUsername = new JTextField(20);
        tfPassword = new JTextField(20);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.setBackground(biruLembut);
        row1.add(new JLabel("Nama:")).setFont(fontLabel);
        row1.add(tfNama);
        tfNama.setFont(fontInput);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.setBackground(biruLembut);
        row2.add(new JLabel("Username:")).setFont(fontLabel);
        row2.add(tfUsername);
        tfUsername.setFont(fontInput);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.setBackground(biruLembut);
        row3.add(new JLabel("Password:")).setFont(fontLabel);
        row3.add(tfPassword);
        tfPassword.setFont(fontInput);

        panelForm.add(row1);
        panelForm.add(row2);
        panelForm.add(row3);

        // === Panel Tombol ===
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");

        JPanel panelButton = new JPanel();
        panelButton.setBackground(biruLembut);
        panelButton.add(btnSimpan);
        panelButton.add(btnHapus);
        panelButton.add(btnReset);
        panelButton.add(btnKembali);

        // === Tabel Data ===
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Username", "Password"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Tambahkan ke Frame ===
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // === Event Handler ===
        btnSimpan.addActionListener(e -> simpanPetugas());
        btnHapus.addActionListener(e -> hapusPetugas());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            new FormMenuUtama().setVisible(true);
            dispose();
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int baris = table.getSelectedRow();
                if (baris != -1) {
                    selectedId = (int) tableModel.getValueAt(baris, 0);
                    tfNama.setText(tableModel.getValueAt(baris, 1).toString());
                    tfUsername.setText(tableModel.getValueAt(baris, 2).toString());
                    tfPassword.setText(tableModel.getValueAt(baris, 3).toString());
                }
            }
        });

        tampilkanData();
    }

    private void tampilkanData() {
        tableModel.setRowCount(0);
        List<Petugas> list = dao.getAllPetugas();
        for (Petugas p : list) {
            tableModel.addRow(new Object[]{
                    p.getIdPetugas(),
                    p.getNama(),
                    p.getUsername(),
                    p.getPassword()
            });
        }
    }

    private void simpanPetugas() {
        String nama = tfNama.getText();
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi semua kolom!");
            return;
        }

        Petugas p = new Petugas();
        p.setNama(nama);
        p.setUsername(username);
        p.setPassword(password);

        if (selectedId == -1) {
            dao.insertPetugas(p);
            JOptionPane.showMessageDialog(this, "Petugas ditambahkan.");
        } else {
            p.setIdPetugas(selectedId);
            dao.updatePetugas(p);
            JOptionPane.showMessageDialog(this, "Petugas diperbarui.");
        }

        tampilkanData();
        resetForm();
    }

    private void hapusPetugas() {
        if (selectedId != -1) {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                dao.deletePetugas(selectedId);
                tampilkanData();
                resetForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!");
        }
    }

    private void resetForm() {
        tfNama.setText("");
        tfUsername.setText("");
        tfPassword.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPetugas().setVisible(true));
    }
}



