package beans;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="ShowCathegoriesBean")
@SessionScoped
public class ShowCathegoriesBean {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private ResultSet resultSet1 = null;
    private PreparedStatement preparedStatement = null;
    public String cathegory;

    public String getCathegory() {
        return cathegory;
    }

    public void setCathegory(String cathegory) {
        this.cathegory = cathegory;
    }
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
            skin=null;
            skins.add("style.css");
            skins.add("style2.css");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
            String cookie = cookies.get("style").toString();
            if(cookie==null)
            {
            Cookie cookie2= new Cookie ("style","style.css");
                cookie2.setMaxAge(60*24);
                response.addCookie(cookie2);
            }
            Stats();
            
            
    }
        catch (SQLException ex)
        {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Stats()
    {
        try 
        {
            
            Class.forName("com.mysql.jdbc.Driver");  
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();   
            Cookie[] cookies = null;
            cookies = request.getCookies();
            date= new Date();
            if( cookies == null )
            {
                preparedStatement = connect.prepareStatement("update stats set visited = visited + 1");
                preparedStatement.executeUpdate();
                Cookie cookie = new Cookie("visited","visited");
                cookie.setMaxAge(60*24);
                response.addCookie(cookie);
                
                
                preparedStatement = connect.prepareStatement("select * from stats");   
                resultSet1 = preparedStatement.executeQuery();
                resultSet1.next();
                num = resultSet1.getInt("visited");
                preparedStatement=connect.prepareStatement("Select count(idUsers) a from users");
                resultSet1 =preparedStatement.executeQuery();
                resultSet1.next();
                am=resultSet1.getInt("a");
            }
            else
            {
                preparedStatement = connect.prepareStatement("select * from stats");   
                resultSet1 = preparedStatement.executeQuery();
                resultSet1.next();
                num = resultSet1.getInt("visited");
                preparedStatement=connect.prepareStatement("Select count(idUsers) a from users");
                resultSet1 =preparedStatement.executeQuery();
                resultSet1.next();
                am=resultSet1.getInt("a");
            }
            
            connect.close();
        }
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Integer num;
    public Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }
    public Integer am;

    public Integer getAm() {
        return am;
    }

    public void setAm(Integer am) {
        this.am = am;
    }
    public String note;
    public ArrayList list = new ArrayList();
    public Integer id;
    public ArrayList topics = new ArrayList();
    public String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    

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
    
public String SaveNote() throws IOException
{
    try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            if(connect != null)
            {
                statement = connect.createStatement();
                preparedStatement = connect.prepareStatement("UPDATE adminnote SET note=?");
                preparedStatement.setString(1, note);
                preparedStatement.executeUpdate();
            }
            return "index";
            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.redirect("http://localhost:8080/JavaJSF/index.xhtml");
           // return "/JavaJSF/index";
         }
    
        catch (SQLException ex)
        {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowCathegoriesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    return "/JavaJSF/index";
}
public String create()
{
    try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            statement = connect.createStatement();
            
            resultSet = statement.executeQuery("select * from cathegories WHERE Content = '"+cathegory+"'");
            if(resultSet.first() == true)
            {
                error="That cathegory already exists!";
                return "index";
            }
            else
            {
                preparedStatement = connect.prepareStatement("insert into  cathegories values (default, ?)");
                System.out.print(cathegory);
                preparedStatement.setString(1, cathegory);
                preparedStatement.executeUpdate();
                
                Loadlist();
                error="Successfully created!";
                return "index";
            }
        }
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    return "index";
}

public String skin;

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }
public ArrayList skins = new ArrayList();

    public ArrayList getSkins() {
        return skins;
    }

    public void setSkins(ArrayList skins) {
        this.skins = skins;
    }
public void setskin()
{
    if(skin=="style2.css")
    {
        skin="style.css";
    }
    else skin="style2.css";
}

}
