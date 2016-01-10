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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="Login")
@RequestScoped
public class Login {

    /**
     * Creates a new instance of Login
     */
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private ResultSet resultSet1 = null;
    private PreparedStatement preparedStatement = null;
    public String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String password;
    public String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    public Login() {
    }
     
    public String Validate()
    {
        try
        {
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
           
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

                preparedStatement = connect.prepareStatement("select * from users where Nickname=? and Password=?");
                preparedStatement.setString(1,user);  
                preparedStatement.setString(2,password);  
                resultSet = preparedStatement.executeQuery();
                status = resultSet.next(); ///?
                
                    if(status)
                    {
                        int id = resultSet.getInt("idUsers");
                        
                        session.setAttribute("login", user);
                        session.setMaxInactiveInterval(1800); 
                        session.setAttribute("id",id);
                        connect.close();
                        return "index";
                    }
                    else
                    {
                        connect.close();
                        error="Wrong Username or Password";
                        return "login";
                    }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
      return "login";   
    }
    public String invalidate()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        session.invalidate();
        return "index";
    }
    
}
