/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Shaheed
 */
@WebServlet(urlPatterns = {"/RegistrationServlet"})
public class RegistrationServlet extends HttpServlet {
    
    private Connection conn = null;
    private Statement stmt = null;

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        JSONObject json = new JSONObject();
        
        Enumeration paramNames = request.getParameterNames();
        String params[] = new String[3];
        int i = 0;
        while(paramNames.hasMoreElements()){
            String paramName = (String) paramNames.nextElement();
            //System.out.println("ParamName : " +paramName);
            
            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
            //System.out.println("ParamValue : " +params[i]);
            
            i++;
        }
        
        
        DatabaseHandler db = new DatabaseHandler();
        
        conn = db.makeConnection();
        try {
            stmt = conn.createStatement();
            
            //params[0]=password,params[1]=name,params[2]=email
            String query = "INSERT INTO user (name,email,password) VALUES (\""+params[1]+"\",\""+params[2]+"\",\""+params[0]+"\")";
            //System.out.println(query);
        
            int updatedRows = stmt.executeUpdate(query);
            if(updatedRows == 1){
                json.put("reply", "done");
            }else{
                json.put("reply", "undone");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //System.out.println(updatedRows);
        //System.out.println(json);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        
        //closing mysql connection (now done in DatanaseHandler class)
        db.closeAllConnections(conn, stmt);
        
    }
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            doGet(request,response);
        } catch (ServletException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
