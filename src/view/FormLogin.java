/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// === File: FormLogin.java ===
package view;

import dao.PetugasDAO;
import model.Petugas;

import javax.swing.*;
import java.awt.*;

public class FormLogin extends JFrame {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin, btnBatal;

    public FormLogin() {
        setTitle("Login Toko ATK");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Panel Gambar Kiri ===
        JPanel panelKiri = new JPanel();
        panelKiri.setBackground(Color.WHITE);
        panelKiri.setPreferredSize(new Dimension(250, 350));

        ImageIcon icon = new ImageIcon(getClass().getResource("/gambar/pngegg.png"));
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel lblGambar = new JLabel(new ImageIcon(img));
        panelKiri.add(lblGambar);

        // === Panel Form Login Kanan ===
        JPanel panelKanan = new JPanel();
        panelKanan.setBackground(new Color(173, 216, 230)); // biru lembut
        panelKanan.setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsername.setBounds(30, 40, 80, 25);
        panelKanan.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(120, 40, 180, 25);
        panelKanan.add(tfUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setBounds(30, 80, 80, 25);
        panelKanan.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(120, 80, 180, 25);
        panelKanan.add(tfPassword);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLogin.setBounds(60, 130, 100, 30);
        panelKanan.add(btnLogin);

        btnBatal = new JButton("Batal");
        btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBatal.setBounds(180, 130, 100, 30);
        panelKanan.add(btnBatal);

        // Tambah Panel ke Frame
        add(panelKiri, BorderLayout.WEST);
        add(panelKanan, BorderLayout.CENTER);

        // Aksi Tombol
        btnLogin.addActionListener(e -> prosesLogin());
        btnBatal.addActionListener(e -> System.exit(0));
    }

    private void prosesLogin() {
        String username = tfUsername.getText();
        String password = String.valueOf(tfPassword.getPassword());

        PetugasDAO dao = new PetugasDAO();
        Petugas petugas = dao.getLoginPetugas(username, password);

        if (petugas != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil!\nSelamat datang, " + petugas.getNama());
            new FormMenuUtama().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal!\nUsername atau password salah.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}





