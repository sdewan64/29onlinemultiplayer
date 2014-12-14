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
public class JoinGameServlet extends HttpServlet {

    private Connection conn = null;
    private Statement stmt = null;
    JSONObject json = new JSONObject();
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        //System.out.println("param0->"+params[0]);
        //System.out.println("param1->"+params[1]);
        //param0->gameid,param1->name
        
        DatabaseHandler db = new DatabaseHandler();
        
        conn = db.makeConnection();
        
        String query = "SELECT * FROM game WHERE id="+params[0];
        //System.out.println(query);
        
        PreparedStatement prStmt;
        try {
            prStmt = conn.prepareStatement(query);
            ResultSet rs = prStmt.executeQuery();
            
            if(rs.next()){
                String count = rs.getString("playercount");
                count = String.valueOf(Integer.parseInt(count)+1);
                
                String qry = "UPDATE game SET nameplayer"+count+"=\""+params[1]+"\",playercount=\""+count+"\" WHERE id=\""+params[0]+"\"";
                //System.out.println(qry);
                
                stmt = conn.createStatement();
                int updatedRows = stmt.executeUpdate(qry);
                if(updatedRows == 1){
                    json.put("reply", "done");
                    json.put("actionid", count);
                    //setting next player action to 1 to start bet
                    if(Integer.parseInt(count) == 4){
                        qry = "UPDATE game SET turn=1,nextactionuserid=1 WHERE id=\""+params[0]+"\"";
                        stmt.execute(qry);
                    }
                }else{
                    json.put("reply", "undone");
                }
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            
        } catch (SQLException ex) {
            Logger.getLogger(JoinGameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        db.closeAllConnections(conn,stmt);
        
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
