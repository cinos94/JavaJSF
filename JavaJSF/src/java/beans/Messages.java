/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="Messages")
@RequestScoped
public class Messages {

    /**
     * Creates a new instance of Messages
     */
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    public ArrayList messages = new ArrayList();

    public ArrayList getMessages() {
        return messages;
    }

    public void setMessages(ArrayList messages) {
        this.messages = messages;
    }
    public Messages() {
    }
    public String LoadMessages()
    {
        try 
        {
            messages.clear();
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
             FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if(connect != null)
            {
                statement = connect.createStatement();
                Integer id= (Integer)session.getAttribute("id");
                
                resultSet = statement.executeQuery("select u.Nickname a,us.Nickname b,m.Message from messages m,users u,users us where m.idReceiver='"+id+"' and us.idUsers=m.idSender and u.idUsers='"+id+"'");
                
                while(resultSet.next())
            {
                Map m = new HashMap();
                m.put("idSender",resultSet.getString("b"));
                m.put("idReceiver",resultSet.getString("a"));
                m.put("Message",resultSet.getString("Message"));
                messages.add(m);
            }
                      
                       
            }
            
    }
        catch (SQLException ex)
        {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/user_panel/messages";
    }
}
