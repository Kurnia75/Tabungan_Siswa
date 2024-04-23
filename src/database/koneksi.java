/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class koneksi {
      public Connection connection;
    
    public koneksi()
    {
        try{
            String url = "jdbc:mysql://localhost/projectapp_tabungan_siswa";
            String user = "root";
            String pass = "";
            connection = DriverManager.getConnection(url, user, pass);
            
        }catch(SQLException se){
            JOptionPane.showMessageDialog(null, "Tidak Terkoneksi Database");
         }
        }
    
}
