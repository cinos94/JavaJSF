/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.IOException;
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
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="Messages")
@ApplicationScoped
public class Messages {

    /**
     * Creates a new instance of Messages
     */
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    public ArrayList messages = new ArrayList();
    public String user;
    public String message;
    public String error;
    public boolean status;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    

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
                connect.close();
                      
                       
            }
            
    }
        catch (SQLException ex)
        {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "messages";
    }
    public String Send()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            if(connect != null)
            {
                statement = connect.createStatement();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                Integer id= (Integer)session.getAttribute("id");
                
                resultSet = statement.executeQuery("select idUsers from users where Nickname ='"+user+"'");
                status = resultSet.next();
                if(status)
                {
                    Integer idreceiver =resultSet.getInt("idUsers");
                    preparedStatement = connect.prepareStatement("insert into  messages values (default, ?, ?, ?)");
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, idreceiver);
                    preparedStatement.setString(3, message);
                    preparedStatement.executeUpdate();
                    error="Message sent";  
                    connect.close();
                }
                else
                {
                   error="Nickname doesn't exist";
                   return "sendmessage";
                }
            }
    }
        catch (SQLException ex)
        {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return"sendmessage";
    }
    public String LoadAllMessages() throws IOException
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
                
                resultSet = statement.executeQuery("select u.Nickname a,us.Nickname b,m.Message, m.idMessage id from messages m,users u,users us where us.idUsers=m.idSender and u.idUsers=m.idReceiver");
                
                while(resultSet.next())
            {
                Map m = new HashMap();
                m.put("idSender",resultSet.getString("b"));
                m.put("idReceiver",resultSet.getString("a"));
                m.put("Message",resultSet.getString("Message"));
                m.put("idMessage",resultSet.getString("id"));
                messages.add(m);
            }
                connect.close();
                
                //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
           // ec.redirect("http://localhost:8080/JavaJSF/admin_panel/allmessages.xhtml");
                  System.out.println("asdasdfgfgrrgregererergregerreg");    
                       
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "allmessages";
    }
    public String bla() throws IOException
    {
        
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect("http://localhost:8080/JavaJSF/index.xhtml");
        return "index";
    }
    public String deleteNote(String id) throws IOException
    {
        System.out.println("asdsadd");   
        try 
        {
            //System.out.println(id);
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
             FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if(connect != null)
            {
                statement = connect.createStatement();
                statement.executeUpdate("delete from messages where idMessage='"+id+"'");
                connect.close();
                LoadAllMessages();    
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "allmessages";
    }
}
