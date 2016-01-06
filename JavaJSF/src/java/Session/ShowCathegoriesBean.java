package Session;

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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="ShowCathegoriesBean")
@RequestScoped
public class ShowCathegoriesBean {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    /**
     * Creates a new instance of ShowCathegories
     */
    public ShowCathegoriesBean() {
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
    public String showtopics(int id)
    {
        return "cathegory";
    }
    

}
