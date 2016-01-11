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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="Register")
@RequestScoped
public class Register {

    /**
     * Creates a new instance of Register
     */
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private ResultSet resultSet1 = null;
    private PreparedStatement preparedStatement = null;
    public Register() {
    }
    public String user;
    public String password;
    public String name;
    public String surname;
    public String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
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
    public String Validate()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root", "");
            statement = connect.createStatement();
            
            resultSet = statement.executeQuery("select * from baza.users WHERE Nickname = '"+user+"'");
            if(resultSet.first() == true)
            {
                error="Login aledry exists, please try different one.";
                return "registerPage";
            }
            else
            {
                preparedStatement = connect.prepareStatement("insert into  baza.users values (default, ?, ? , ?, ?)");

                preparedStatement.setString(1, password);
                preparedStatement.setString(2, user);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, surname);
                preparedStatement.executeUpdate();
                
                error="Registration sucessfull. Welcome "+ name+" " + surname;
                return "registerPage";
            }
        }
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "registerPage";
    }
    
}
