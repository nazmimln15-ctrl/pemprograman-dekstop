/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author LENOVO
 */

public class FormRegistrasi extends JFrame {
    // Komponen form
    private JTextField txtNama;
    private JRadioButton rdoPria, rdoWanita;
    private JCheckBox cbMembaca, cbOlahraga, cbProgramming, cbMusik;
    private JButton btnTambah, btnHapus, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    public FormRegistrasi() {
        super("Form Registrasi - Contoh JRadioButton, JCheckBox, JTable");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel utama menggunakan BorderLayout
        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel form di kiri (GridBagLayout untuk fleksibilitas) ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nama
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(new JLabel("Nama:"), gbc);
        txtNama = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        panelForm.add(txtNama, gbc);

        // Jenis Kelamin - JRadioButton + ButtonGroup
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(new JLabel("Jenis Kelamin:"), gbc);
        JPanel panelGender = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rdoPria = new JRadioButton("Pria");
        rdoWanita = new JRadioButton("Wanita");
        ButtonGroup bgGender = new ButtonGroup();
        bgGender.add(rdoPria); bgGender.add(rdoWanita);
        panelGender.add(rdoPria); panelGender.add(rdoWanita);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        panelForm.add(panelGender, gbc);

        // Hobi - JCheckBox (bisa memilih banyak)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(new JLabel("Hobi:"), gbc);
        JPanel panelHobi = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cbMembaca = new JCheckBox("Membaca");
        cbOlahraga = new JCheckBox("Olahraga");
        cbProgramming = new JCheckBox("Programming");
        cbMusik = new JCheckBox("Musik");
        panelHobi.add(cbMembaca); panelHobi.add(cbOlahraga);
        panelHobi.add(cbProgramming); panelHobi.add(cbMusik);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        panelForm.add(panelHobi, gbc);

        // Tombol aksi
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnTambah = new JButton("Tambah ke Tabel");
        btnHapus = new JButton("Hapus Baris Terpilih");
        btnClear = new JButton("Bersihkan Form");
        panelButtons.add(btnTambah); panelButtons.add(btnHapus); panelButtons.add(btnClear);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1;
        panelForm.add(panelButtons, gbc);

        // --- Panel tabel di kanan ---
        String[] columnNames = {"Nama", "Jenis Kelamin", "Hobi"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Buat agar sel tabel tidak dapat langsung diubah (readonly)
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        // --- Penempatan ke panel utama ---
        panelUtama.add(panelForm, BorderLayout.WEST);
        panelUtama.add(scroll, BorderLayout.CENTER);

        // Tambahkan panel utama ke frame
        this.getContentPane().add(panelUtama);

        // --- Event handling ---
        btnTambah.addActionListener(e -> onTambah());
        btnClear.addActionListener(e -> clearForm());
        btnHapus.addActionListener(e -> onHapusBaris());

        // Ketika baris dipilih, tampilkan detail di form (opsional, untuk edit/view)
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                populateFormFromTable(row);
            }
        });

        // Tambahan: enter pada textbox Nama menekan tombol Tambah
        txtNama.addActionListener(e -> btnTambah.doClick());
    }

    private void onTambah() {
        String nama = txtNama.getText().trim();
        String gender = rdoPria.isSelected() ? "Pria" : rdoWanita.isSelected() ? "Wanita" : "";
        ArrayList<String> hobiList = new ArrayList<>();
        if (cbMembaca.isSelected()) hobiList.add("Membaca");
        if (cbOlahraga.isSelected()) hobiList.add("Olahraga");
        if (cbProgramming.isSelected()) hobiList.add("Programming");
        if (cbMusik.isSelected()) hobiList.add("Musik");
        String hobi = String.join(", ", hobiList);

        // Validasi sederhana
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong.", "Validasi", JOptionPane.WARNING_MESSAGE);
            txtNama.requestFocus();
            return;
        }
        if (gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih jenis kelamin.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (hobiList.isEmpty()) {
            int pilih = JOptionPane.showConfirmDialog(this, "Tidak memilih hobi. Lanjutkan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (pilih != JOptionPane.YES_OPTION) return;
        }

        // Tambah ke tabel
        tableModel.addRow(new Object[]{nama, gender, hobi});
        clearForm();
    }

    private void onHapusBaris() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus pada tabel.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int pilih = JOptionPane.showConfirmDialog(this, "Hapus baris terpilih?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            clearForm();
        }
    }

    // Isi form berdasarkan data di tabel (ketika baris dipilih)
    private void populateFormFromTable(int row) {
        String nama = tableModel.getValueAt(row, 0).toString();
        String gender = tableModel.getValueAt(row, 1).toString();
        String hobi = tableModel.getValueAt(row, 2).toString();

        txtNama.setText(nama);
        if (gender.equals("Pria")) rdoPria.setSelected(true);
        else if (gender.equals("Wanita")) rdoWanita.setSelected(true);
        else {
            rdoPria.setSelected(false);
            rdoWanita.setSelected(false);
        }

        // Reset semua checkbox lalu set berdasarkan data tabel
        cbMembaca.setSelected(false);
        cbOlahraga.setSelected(false);
        cbProgramming.setSelected(false);
        cbMusik.setSelected(false);
        if (!hobi.isEmpty()) {
            String[] parts = hobi.split("\\s*,\\s*");
            for (String s : parts) {
                if (s.equals("Membaca")) cbMembaca.setSelected(true);
                if (s.equals("Olahraga")) cbOlahraga.setSelected(true);
                if (s.equals("Programming")) cbProgramming.setSelected(true);
                if (s.equals("Musik")) cbMusik.setSelected(true);
            }
        }
    }

    private void clearForm() {
        txtNama.setText("");
        rdoPria.setSelected(false);
        rdoWanita.setSelected(false);
        cbMembaca.setSelected(false);
        cbOlahraga.setSelected(false);
        cbProgramming.setSelected(false);
        cbMusik.setSelected(false);
        table.clearSelection();
        txtNama.requestFocus();
    }

    public static void main(String[] args) {
        // Jalankan di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new FormRegistrasi().setVisible(true);
        });
    }
}
