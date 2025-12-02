/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author LENOVO
 */
public class ContohActionListener {
    public static void main (String[] args) {
        JFrame frame = new JFrame ("Demo ActionListener");
        JButton button = new JButton ("Klik Saya");
        
        //menambahkan listener ke tombol 
        button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Tombol diklik!");
        }
    });
        
        frame.add(button);
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
};
