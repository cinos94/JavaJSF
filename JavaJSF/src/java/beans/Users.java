/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import static java.lang.System.out;
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
@ManagedBean(name="Users")
@RequestScoped

public class Users {

    /**
     * Creates a new instance of Users
     */
     private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    public ArrayList messages = new ArrayList();
    public String Login;
    public ArrayList users = new ArrayList();
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList getUsers() {
        return users;
    }

    public void setUsers(ArrayList users) {
        this.users = users;
    }
    

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }
    public String name;
    public String surname;
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String message;
    public String error;
    public boolean status;
    public Integer interval;

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public ArrayList getMessages() {
        return messages;
    }

    public void setMessages(ArrayList messages) {
        this.messages = messages;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Users() {
    }
    
    public String User()
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
                Login = (String)session.getAttribute("login");
                Integer Id = (Integer)session.getAttribute("id");
                resultSet = statement.executeQuery("select * from users where Nickname='" + Login + "' and idUsers='" + Id + "'");
                resultSet.next(); ///?
                
                    if(resultSet.first() == true)
                    {
                        surname = resultSet.getString("Surname");
                        name = resultSet.getString("Name");
                        password = resultSet.getString("password");
                        interval = resultSet.getInt("Inter");
                        return "profile";
                    }
                    else
                    {
                        connect.close();
                        error="Something went wrong :/";
                        return "profile";
                    }

            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
         return "profile";
    }
    public String Save()
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
               Integer Id= (Integer)session.getAttribute("id");
               System.out.print(interval);
                preparedStatement = connect.prepareStatement("UPDATE users SET Nickname=?, Name=?, password=?, Surname=?, Inter=? WHERE idUsers=?");
                preparedStatement.setString(1, Login);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, surname);
                preparedStatement.setInt(5,interval);
                preparedStatement.setInt(6, Id);
                preparedStatement.executeUpdate();
               
               
                //resultSet = statement.executeQuery("UPDATE users SET Nickname='" + Login + "', Name='" + Name + "', password='" + Password + "', Surname='"+Surname+"'"+"WHERE idUsers='"+id+"'");
                connect.close();
                error="Zapisano poprawnie";
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "profile";
    }
public String ShowAll()
{
    try 
        {
            users.clear();
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
             FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if(connect != null)
            {
                statement = connect.createStatement();
                Integer id= (Integer)session.getAttribute("id");
                
                resultSet = statement.executeQuery("select * from users");
                
                while(resultSet.next())
            {
                Map m = new HashMap();
                m.put("idUser",resultSet.getString("idUsers"));
                m.put("Nickname",resultSet.getString("Nickname"));
                m.put("Name",resultSet.getString("Name"));
                m.put("Surname",resultSet.getString("Surname"));
                users.add(m);
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
    return "users";
}

public String DeleteUser(String id)
{
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
                statement.executeUpdate("delete from users where idUsers='"+id+"'");
                connect.close();
                ShowAll();    
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        }
    return "users";
}
        
    
}
