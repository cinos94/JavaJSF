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
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="ShowCathegoriesBean")
@ApplicationScoped
public class ShowCathegoriesBean {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    /**
     * Creates a new instance of ShowCathegories
     */
    public ShowCathegoriesBean() {
         
        
    }
    @PostConstruct
    public void Loadlist()
    {
        list.clear();
        note = null;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            if(connect != null)
            {
                statement = connect.createStatement();
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                resultSet = statement.executeQuery("select * from adminnote");
                resultSet.next(); ///?
                
                    if(resultSet.first() == true)
                    {
                    note = resultSet.getString("note");
                    }
                resultSet=statement.executeQuery("select * from cathegories");
                while(resultSet.next())
                {
                Map m = new HashMap();
                m.put("idCathegory",resultSet.getString("idCathegory"));
                m.put("Content",resultSet.getString("Content"));
                list.add(m);
                }
            }
    }
        catch (SQLException ex)
        {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String note;
    public ArrayList list = new ArrayList();
    public Integer id;
    public ArrayList topics = new ArrayList();

    public void setTopics(ArrayList topics) {
        this.topics = topics;
    }

    public ArrayList getTopics() {
        return topics;
    }
    

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public ArrayList getList() {
        return list;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
   /* public String showtopics(int id)
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
                Integer sub;
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
        this.id=id;
        return "cathegory";
    }*/
    

}
