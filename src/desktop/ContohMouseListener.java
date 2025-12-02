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
public class ContohMouseListener {
    public static void main (String[] args) {
        JFrame frame = new JFrame("Demo Mouse Listener");
        JLabel label = new JLabel("Arahkan atau klik Mouse disini", SwingConstants.CENTER);
        label.addMouseListener(new MouseListener(){
            public void mouseClicked (MouseEvent e) {
                label.setText("Mouse dikik pada koordinat: (" + e.getX() + "," + e.getY() + ")");
            }
            public void mousePressed(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mouseEntered(MouseEvent e){
                label.setText("Mouse masuk ke area label");
            }
            public void mouseExited(MouseEvent e){
                label.setText("Mouse keluar dari area label");
            }
        });
        frame.add(label);
        frame.setSize(400,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
