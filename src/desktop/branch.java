/*
CrudSwingJDBC_Driver.java
Versi diperbaiki — CRUD sederhana menggunakan Swing + JDBC (Driver dimuat eksplisit)
Penulis asli: (dari user)
Perbaikan oleh: ChatGPT
*/

package desktop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**

* CRUD Swing + JDBC — versi diperbaiki.
  */
  public class CrudSwingJDBC_Driver extends JFrame {
  private static final long serialVersionUID = 1L;

  // sesuaikan credensial jika perlu
  private static final String DB_URL = "jdbc:mysql://localhost:3306/carakonek?serverTimezone=UTC";
  private static final String DB_USER = "root";
  private static final String DB_PASS = "";

  private JTextField txtNama = new JTextField(25);
  private JTextField txtUmur = new JTextField(25);

  // model tabel: kolom ID, NAMA, UMUR (tidak editable langsung)
  private DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "NAMA", "UMUR"}, 0) {
  @Override
  public boolean isCellEditable(int row, int column) {
  return false;
  }
  };
  private JTable table = new JTable(model);

  public CrudSwingJDBC_Driver() {
  super("CRUD Swing + JDBC (Driver langsung)");
  initUI();

 
   // muat driver eksplisit (opsional pada Java modern, tapi diminta)
   try {
       // driver modern MySQL Connector/J menggunakan com.mysql.cj.jdbc.Driver
       Class.forName("com.mysql.cj.jdbc.Driver");
   } catch (ClassNotFoundException ex) {
       showError("Driver JDBC tidak ditemukan: " + ex.getMessage());
       // tidak langsung return — bisa tetap mencoba jika driver otomatis tersedia
   }

   loadData();


  }

  private void initUI() {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setLayout(new BorderLayout(8, 8));

   JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
   form.add(new JLabel("Nama: "));
   form.add(txtNama);
   form.add(new JLabel("Umur: "));
   form.add(txtUmur);

   JButton btnTambah = new JButton("Tambah");
   JButton btnUpdate = new JButton("Update");
   JButton btnHapus = new JButton("Hapus");
   JButton btnRefresh = new JButton("Refresh");

   form.add(btnTambah);
   form.add(btnUpdate);
   form.add(btnHapus);
   form.add(btnRefresh);

   add(form, BorderLayout.NORTH);
   add(new JScrollPane(table), BorderLayout.CENTER);

   // event handlers
   btnTambah.addActionListener(e -> create());
   btnUpdate.addActionListener(e -> update());
   btnHapus.addActionListener(e -> delete());
   btnRefresh.addActionListener(e -> loadData());

   // Mouse listener untuk mengambil data baris yang diklik
   table.addMouseListener(new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
           int r = table.getSelectedRow();
           if (r != -1) {
               txtNama.setText(model.getValueAt(r, 1).toString());
               txtUmur.setText(model.getValueAt(r, 2).toString());
           }
       }
   });

   setSize(650, 400);
   setLocationRelativeTo(null);


  }

  // CREATE
  private void create() {
  String nama = txtNama.getText().trim();
  String umurText = txtUmur.getText().trim();
  if (nama.isEmpty() || umurText.isEmpty()) {
  showWarning("Nama dan umur harus diisi!");
  return;
  }


   int umur;
   try {
       umur = Integer.parseInt(umurText);
       if (umur < 0) {
           showWarning("Umur tidak valid.");
           return;
       }
   } catch (NumberFormatException ex) {
       showWarning("Umur harus berupa angka.");
       return;
   }

   String sql = "INSERT INTO mahasiswa (nama, umur) VALUES (?, ?)";
   try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement(sql)) {

       ps.setString(1, nama);
       ps.setInt(2, umur);
       int rows = ps.executeUpdate();
       if (rows > 0) {
           showInfo("Data berhasil ditambahkan.");
           clearForm();
           loadData();
       } else {
           showWarning("Gagal menambah data.");
       }
   } catch (SQLException ex) {
       showError("Gagal menambah data: " + ex.getMessage());
   }

  }

  // READ
  private void loadData() {
  model.setRowCount(0);
  // perhatikan: gunakan nama tabel yang konsisten (mahasiswa)
  String sql = "SELECT id, nama, umur FROM mahasiswa ORDER BY id";
  try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
  PreparedStatement ps = conn.prepareStatement(sql);
  ResultSet rs = ps.executeQuery()) {

  
       while (rs.next()) {
           model.addRow(new Object[]{rs.getInt("id"), rs.getString("nama"), rs.getInt("umur")});
       }
   } catch (SQLException ex) {
       showError("Gagal memuat data: " + ex.getMessage());
   }
  

  }

  // UPDATE
  private void update() {
  int r = table.getSelectedRow();
  if (r == -1) {
  showWarning("Pilih baris yang ingin diupdate.");
  return;
  }
  int id = Integer.parseInt(model.getValueAt(r, 0).toString());

  
   String nama = txtNama.getText().trim();
   String umurText = txtUmur.getText().trim();
   if (nama.isEmpty() || umurText.isEmpty()) {
       showWarning("Nama dan umur harus diisi!");
       return;
   }

   int umur;
   try {
       umur = Integer.parseInt(umurText);
       if (umur < 0) {
           showWarning("Umur tidak valid.");
           return;
       }
   } catch (NumberFormatException ex) {
       showWarning("Umur harus berupa angka.");
       return;
   }

   String sql = "UPDATE mahasiswa SET nama = ?, umur = ? WHERE id = ?";
   try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement(sql)) {

       ps.setString(1, nama);
       ps.setInt(2, umur);
       ps.setInt(3, id);
       int rows = ps.executeUpdate();
       if (rows > 0) {
           showInfo("Data berhasil diperbarui.");
           clearForm();
           loadData();
       } else {
           showWarning("Data dengan ID tersebut tidak ditemukan.");
       }
   } catch (SQLException ex) {
       showError("Gagal memperbarui data: " + ex.getMessage());
   }
  

  }

  // DELETE
  private void delete() {
  int r = table.getSelectedRow();
  if (r == -1) {
  showWarning("Pilih baris yang ingin dihapus.");
  return;
  }
  int id = Integer.parseInt(model.getValueAt(r, 0).toString());

  
   int confirm = JOptionPane.showConfirmDialog(this,
           "Yakin ingin menghapus ID = " + id + " ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
   if (confirm != JOptionPane.YES_OPTION) return;

   String sql = "DELETE FROM mahasiswa WHERE id = ?";
   try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement(sql)) {

       ps.setInt(1, id);
       int rows = ps.executeUpdate();
       if (rows > 0) {
           showInfo("Data berhasil dihapus.");
           clearForm();
           loadData();
       } else {
           showWarning("Data dengan ID tersebut tidak ditemukan.");
       }
   } catch (SQLException ex) {
       showError("Gagal menghapus data: " + ex.getMessage());
   }
  

  }

  private void clearForm() {
  txtNama.setText("");
  txtUmur.setText("");
  table.clearSelection();
  txtNama.requestFocus();
  }

  // Dialog helper
  private void showInfo(String msg) {
  JOptionPane.showMessageDialog(this, msg, "Informasi", JOptionPane.INFORMATION_MESSAGE);
  }

  private void showWarning(String msg) {
  JOptionPane.showMessageDialog(this, msg, "Peringatan", JOptionPane.WARNING_MESSAGE);
  }

  private void showError(String msg) {
  JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static void main(String[] args) {
  SwingUtilities.invokeLater(() -> new CrudSwingJDBC_Driver().setVisible(true));
  }
  }
