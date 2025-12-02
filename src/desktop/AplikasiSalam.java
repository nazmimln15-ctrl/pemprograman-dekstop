package desktop;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
/**
 *
 * @author LENOVO
 */
public class AplikasiSalam {
    public static void main(String[] args){
        //membuat frame (jendela utama)
        JFrame frame = new JFrame ("Aplikasi Salam");
        frame.setSize(800,400); //ukuran frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //tombol close
        frame.setLayout(null);  //mengatur posisi manual. bisa diseret
        
        //membuat komponen
        JLabel label = new JLabel ("Masukan Nama: ");
        JLabel lAlamat = new JLabel ("Masukan Alamat: ");
        JTextField textField = new JTextField ();
        JTextField textAlamat = new JTextField ();
        JButton button = new JButton ("Tampilkan Salam");
        
        //mengatur posisi dan ukuran komponen
        label.setBounds (60, 30, 150, 25);
        lAlamat.setBounds(60, 60, 150, 25);
        textField.setBounds (160, 30, 150, 25);
        textAlamat.setBounds (160, 60, 150, 25);
        button.setBounds(60, 150, 140, 30);
        
        //menambahkan even pada tombol
        button.addActionListener(e -> {
            String nama = textField.getText();
            String talamat= textAlamat.getText ();
            JOptionPane.showMessageDialog(frame, "Hallo, " + nama + "   " + "dari " + talamat);
        });
        
        //menambah komponen ke frame
        frame.add (label);
        frame.add (lAlamat); 
        frame.add (textField);
        frame.add (textAlamat);
        frame.add (button);
        
        frame.setVisible(true);
        
    }
}
