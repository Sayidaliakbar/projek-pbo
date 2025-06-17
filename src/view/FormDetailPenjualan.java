/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.BarangDAO;
import dao.DetailPenjualanDAO;
import dao.PenjualanDAO;
import model.Barang;
import model.DetailPenjualan;
import model.Penjualan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FormDetailPenjualan extends JFrame {
    private JComboBox<Integer> comboIdPenjualan;
    private JTable tableDetail;
    private DefaultTableModel tableModel;
    private JLabel lblTotal, lblTanggal, lblIdPenjualan;

    private DetailPenjualanDAO detailDAO = new DetailPenjualanDAO();
    private BarangDAO barangDAO = new BarangDAO();
    private PenjualanDAO penjualanDAO = new PenjualanDAO();

    public FormDetailPenjualan() {
        setTitle("Detail Penjualan Toko ATK");
        setSize(750, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color biruLembut = new Color(173, 216, 230);
        Font font = new Font("Segoe UI", Font.PLAIN, 13);

        // Panel atas: Pilih ID Penjualan
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(biruLembut);
        panelTop.setBorder(BorderFactory.createTitledBorder("Pilih ID Penjualan"));
        comboIdPenjualan = new JComboBox<>();
        JButton btnTampilkan = new JButton("Tampilkan");
        panelTop.add(new JLabel("ID Penjualan:"));
        panelTop.add(comboIdPenjualan);
        panelTop.add(btnTampilkan);
        add(panelTop, BorderLayout.NORTH);

        // Panel kiri: Info Penjualan
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(biruLembut);
        panelInfo.setPreferredSize(new Dimension(200, 100));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Informasi Penjualan"));

        lblIdPenjualan = new JLabel();
        lblTanggal = new JLabel();
        lblTotal = new JLabel();

        lblIdPenjualan.setFont(font);
        lblTanggal.setFont(font);
        lblTotal.setFont(font);

        lblIdPenjualan.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTanggal.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelInfo.add(lblIdPenjualan);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(lblTanggal);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(lblTotal);

        add(panelInfo, BorderLayout.WEST);

        // Tabel detail
        tableModel = new DefaultTableModel(new Object[]{"Nama Barang", "Jenis", "Harga", "Jumlah", "Subtotal"}, 0);
        tableDetail = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableDetail);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah: tombol kembali
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBawah.setBackground(biruLembut);
        JButton btnKembali = new JButton("Kembali");
        panelBawah.add(btnKembali);
        add(panelBawah, BorderLayout.SOUTH);

        // Event tombol tampilkan
        btnTampilkan.addActionListener((ActionEvent e) -> {
            Integer selectedId = (Integer) comboIdPenjualan.getSelectedItem();
            if (selectedId != null) {
                tampilkanDetail(selectedId);
            }
        });

        // Event tombol kembali
        btnKembali.addActionListener(e -> {
            new FormMenuUtama().setVisible(true);
            dispose();
        });

        loadIdPenjualan();
    }

    private void loadIdPenjualan() {
        comboIdPenjualan.removeAllItems();
        List<Penjualan> daftarPenjualan = penjualanDAO.getAllPenjualan();
        for (Penjualan p : daftarPenjualan) {
            comboIdPenjualan.addItem(p.getIdPenjualan());
        }
    }

    private void tampilkanDetail(int idPenjualan) {
        tableModel.setRowCount(0);

        Penjualan penjualan = penjualanDAO.getById(idPenjualan);
        if (penjualan == null) {
            JOptionPane.showMessageDialog(this, "Data penjualan tidak ditemukan!");
            return;
        }

        lblIdPenjualan.setText("ID: " + penjualan.getIdPenjualan());
        lblTanggal.setText("Tanggal: " + penjualan.getTanggal());
        lblTotal.setText("Total: Rp" + penjualan.getTotal());

        List<DetailPenjualan> detailList = detailDAO.getAllByPenjualan(idPenjualan);
        for (DetailPenjualan detail : detailList) {
            Barang barang = barangDAO.getBarangById(detail.getIdBarang());
            if (barang != null) {
                tableModel.addRow(new Object[]{
                        barang.getNama(),
                        barang.getJenis(),
                        barang.getHarga(),
                        detail.getJumlah(),
                        detail.getSubtotal()
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormDetailPenjualan().setVisible(true));
    }
}




