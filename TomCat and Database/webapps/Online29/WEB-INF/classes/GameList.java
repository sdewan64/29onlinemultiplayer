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
public class GameList extends HttpServlet {

    private Connection conn = null;
    JSONObject json = new JSONObject();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //no get post required
        
        DatabaseHandler db = new DatabaseHandler();
        
        conn = db.makeConnection();
        
        String[][] gameList = new String[3][2];
        gameList[0][0] = "null";
        gameList[0][1] = "null";
        gameList[1][0] = "null";
        gameList[1][1] = "null";
        gameList[2][0] = "null";
        gameList[2][1] = "null";
        
        String query = "SELECT id,hoster,playercount FROM game";
        
        try {
            PreparedStatement prStmt = conn.prepareStatement(query);
            ResultSet rs = prStmt.executeQuery();
            
            int i = 0;
            
            while(rs.next()){
                if(Integer.parseInt(rs.getString("playercount"))<4){
                    //first hoster then id
                    gameList[i][0] = rs.getString("hoster");
                    gameList[i][1] = rs.getString("id");
                    i++;
                    if(i==3) break;
                }
            }
            
            if(i==0){
                json.put("reply", "nogamefound");
                //System.out.println("nogamefound");
            }else{
                json.put("reply", "gamefound");
                //System.out.println("gamefound");
            }
            
            for(i=0;i<3;i++){
                //gameHost0->hoster & gameId0->1
                json.put("gameHost"+String.valueOf(i) , gameList[i][0]);
                json.put("gameId"+String.valueOf(i) , gameList[i][1]);
                //System.out.println("gamehost: "+gameList[i][0]+" gameId: "+gameList[i][1]);
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            
        } catch (SQLException ex) {
            Logger.getLogger(GameList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println(json);
        
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
