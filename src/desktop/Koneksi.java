/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

public class Koneksi {
    

    public static Connection con;
    public static Statement stm;
    
    public static void main(String args[]){
        
        try {
            
            String url  ="jdbc:mysql://localhost:3306/carakonek"; 
            String user ="root";                           
            String pass ="";                               
            
          
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            con =DriverManager.getConnection(url,user,pass);
            
           
            stm = con.createStatement();
            
            
            System.out.println("koneksi berhasil;");
            
//            menambahkan data
            String sqlInsert = "INSERT INTO db_mahasiswa (nama,umur) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            ps.setString(1, "azmi");
            ps.setInt (2, 20);
            ps.executeUpdate();
            System.out.println("Data berhasil ditambahkan");
            
            con.close(); 
            
        } catch (Exception e) {
            
            System.err.println("koneksi gagal " + e.getMessage());
        }
    }
}
