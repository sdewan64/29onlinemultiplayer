/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Shaheed
 */
public class LoginServlet extends HttpServlet {

    private Connection conn = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject json = new JSONObject();

        Enumeration paramNames = request.getParameterNames();
        String params[] = new String[2];
        int i = 0;
        while(paramNames.hasMoreElements()){
            String paramName = (String) paramNames.nextElement();
            //System.out.println("ParamName : " +paramName);

            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
            //System.out.println("ParamValue : " +params[i]);

            i++;
        }

        //System.out.println("param0: "+params[0]+" param1: "+params[1]);
        //params 0 -> password & params 1 -> email

        DatabaseHandler db = new DatabaseHandler();

        conn = db.makeConnection();
        
        String query = "SELECT name FROM user WHERE email=? AND password=?";
        try {
            PreparedStatement prStmt = conn.prepareStatement(query);
            prStmt.setString(1, params[1]);
            prStmt.setString(2, params[0]);
            ResultSet rs = prStmt.executeQuery();
            if(rs.next()){
                json.put("reply", "done");
                json.put("username", rs.getString("name"));
                //System.out.println(rs.getString("name"));
            }else{
                json.put("reply", "Invalid email/password!");
                json.put("username", "Invalid User");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //System.out.println(json);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        
        db.closeAllConnections(conn);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
