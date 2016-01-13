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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */

@ManagedBean(name="ShowPosts")
@RequestScoped
public class ShowPosts {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    public ArrayList posts = new ArrayList();
    public String sub;
    public String post;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setPosts(ArrayList posts) {
        this.posts = posts;
    }

    public ArrayList getPosts() {
        return posts;
    }
    /**
     * Creates a new instance of ShowPosts
     */
    public ShowPosts() {
    }
    
    public String LoadPosts() throws IOException
    {
        try 
        {   
            posts.clear();
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            
            
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            if(connect != null)
            {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from posts p, users u where idTopic='"+ sub +"' AND p.idUsers = u.idUsers");
                session.setAttribute("sub", sub);
                while(resultSet.next())
            {
                Map m = new HashMap();
                m.put("idPost",resultSet.getString("idPost"));
                m.put("topic",resultSet.getString("tekst"));
                m.put("idTopic",resultSet.getString("idTopic"));
                m.put("idUsers",resultSet.getString("idUsers"));
                m.put("Nickname",resultSet.getString("Nickname"));
                posts.add(m);
            }
               return "topic";
            }
    }
        
            
        catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(ShowPosts.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "topic";
    }
    public String LoadPost()
            {
                return "topic";
            }
    public String AddPost() throws IOException
    {
        try
        {
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            if(connect != null)
            {
                statement = connect.createStatement();
                Integer id = (Integer)session.getAttribute("id");
                sub=(String)session.getAttribute("sub");
 
                    preparedStatement = connect.prepareStatement("insert into posts values (default, ?, ?, ?)");
                    preparedStatement.setString(1, post);
                    preparedStatement.setString(2, sub);
                    preparedStatement.setInt(3, id);
                    preparedStatement.executeUpdate();
            }
            LoadPosts();
    }
        catch (SQLException ex)
        {
            Logger.getLogger(ShowPosts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowPosts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "topic";
        
    }
}
