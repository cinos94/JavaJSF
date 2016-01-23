/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marcin
 */
@ManagedBean(name="SkinBean")
@SessionScoped
public class SkinBean {

    /**
     * Creates a new instance of SkinBean
     */
    public SkinBean() {
    }
    public String skin;

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }
    public void setskin()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
            Cookie cookie = (Cookie)cookies.get("style");
            System.out.print(cookie.getValue());
            if("style.css".equals(cookie.getValue()))
            {
                Cookie cookie2= new Cookie ("style","style2.css");
            cookie2.setMaxAge(60*24);
            response.addCookie(cookie2);
            }
            else
            {
            Cookie cookie2= new Cookie ("style","style.css");
            cookie2.setMaxAge(60*24);
            response.addCookie(cookie2);
            }
    }
}
