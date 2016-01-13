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
import javax.annotation.PostConstruct;
import javax.ejb.SessionBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */

@ManagedBean(name="ShowTopics")
@ApplicationScoped
public class ShowTopics {

      private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    public ArrayList topics = new ArrayList();
    public String topic;
    public Integer sub;

    public Integer getSub() {
        return sub;
    }

    public void setSub(Integer sub) {
        this.sub = sub;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    

    public void setTopics(ArrayList topics) {
        this.topics = topics;
    }

    public ArrayList getTopics() {
        return topics;
    }
    /**
     * Creates a new instance of ShowTopics
     */



    
    public ShowTopics() {
        
    }
    
    public String LoadTopics(int id)
    {
        try 
        {   
            
        
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if(session.getAttribute("login")==null && id==1)
        {
            return "login";
        }
            
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            if(connect != null)
            {
                session.setAttribute("cath",id);
                statement = connect.createStatement();
               
                sub=id;
                resultSet = statement.executeQuery("select * from topics t, users u where idCathegory='"+ sub +"' AND u.idUsers = t.idUsers");
                
                while(resultSet.next())
            {
                Map m = new HashMap();
                m.put("idTopic",resultSet.getString("idTopic"));
                m.put("topic",resultSet.getString("topic"));
                m.put("idCathegory",resultSet.getString("idCathegory"));
                m.put("idUsers",resultSet.getString("idUsers"));
                m.put("Nickname",resultSet.getString("Nickname"));
                topics.add(m);
            }         
            }
    }
        
        catch (SQLException ex)
        {
            Logger.getLogger(ShowTopics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowTopics.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            return "cathegory";
    }
    public String showposts(int id)
    {
        return "topic";
    }
    public String AddTopic()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            statement = connect.createStatement();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                sub=(Integer)session.getAttribute("cath");
                preparedStatement = connect.prepareStatement("insert into  topics values (default, ?, ? , ?)");
                //topic="asdasd";
                preparedStatement.setString(1, topic);
                preparedStatement.setInt(2, sub);
                preparedStatement.setInt(3, (Integer)session.getAttribute("id"));
                preparedStatement.executeUpdate();
                
            
        }
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ShowTopics.class.getName()).log(Level.SEVERE, null, ex);
        }
        LoadTopics(sub);
        return "cathegory";
    }
    
}
