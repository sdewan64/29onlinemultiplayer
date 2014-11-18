
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Shaheed
 */
public class ConnectDatabase {
    private final String DATABASE_URL = "jdbc:mysql://localhost/base29";
    private final String DATABASE_USER = "root";
    private final String DATABASE_PASSWORD = "";
    
    private Connection conn = null;
    
    public Connection makeConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
         
        return conn;
    }
        
}
