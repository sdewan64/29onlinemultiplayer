/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
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
public class CreateGameServlet extends HttpServlet {

    private Connection conn;
    private Statement stmt;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject json = new JSONObject();
        
        Enumeration paramNames = request.getParameterNames();
        String params[] = new String[1];
        int i=0;
        while(paramNames.hasMoreElements()){
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
            i++;
        }
        //System.out.println("param0->"+params[0]);
        
        DatabaseHandler db = new DatabaseHandler();
        
        conn = db.makeConnection();
        
        try {
            stmt = conn.createStatement();
            
            String query = "INSERT INTO game (hoster,nameplayer1) VALUES (\""+params[0]+"\",\""+params[0]+"\")";
            //System.out.println(query);
            
            int updatedRows = stmt.executeUpdate(query);
            if(updatedRows == 1){
                json.put("reply", "done");
            }else{
                json.put("reply", "undone");
            }
            
            query = "SELECT id FROM game WHERE hoster=\""+params[0]+"\"";
            PreparedStatement prstmt = conn.prepareStatement(query);
            ResultSet rs = prstmt.executeQuery();
            String id="0";
            while(rs.next()){
                id = rs.getString("id");
            }
            //System.out.println("id-> "+id);
            if(!id.equals("0")){
                json.put("gameid", id);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CreateGameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
        
        db.closeAllConnections(conn, stmt);
        
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
