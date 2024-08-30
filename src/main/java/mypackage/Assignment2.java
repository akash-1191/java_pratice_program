/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Akash Maurya R
 */
@WebServlet(name = "Assignment2", urlPatterns = {"/Assignment2"})
public class Assignment2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

        // Initialize variables to hold cookie values
        String rememberedUsername = "";
        String rememberedPassword = "";
        
        // Retrieve cookies from the request
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    rememberedUsername = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    rememberedPassword = cookie.getValue();
                }
            }
        }

        // Generate the HTML form
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Form</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f2f2f2; }");
        out.println(".login-container { width: 300px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0px 0px 10px 0px #000; }");
        out.println("h2 { text-align: center; color: #333; }");
        out.println("label { font-weight: bold; display: block; margin-bottom: 5px; }");
        out.println("input[type='text'], input[type='password'] { width: 90%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 5px; }");
        out.println(".remember-me { display: flex; align-items: center; margin-bottom: 15px; }");
        out.println("input[type='checkbox'] { margin-right: 5px; }");
        out.println("input[type='submit'] { width: 100%; padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; }");
        out.println("input[type='submit']:hover { background-color: #45a049; }");
        out.println("</style>");
        out.println("</head>");
        
        out.println("<body>");
        out.println("<div class='login-container'>");
        out.println("<h2>Login</h2>");
        out.println("<form action='Assignment2' method='get'>");
        out.println("<label for='username'>User Name</label>");
        out.println("<input type='text' id='username' name='username' value='" + rememberedUsername + "'>");
        out.println("<label for='pass'>Password</label>");
        out.println("<input type='password' id='pass' name='pass' value='" + rememberedPassword + "'>");
        out.println("<div class='remember-me'>");
        out.println("<input type='checkbox' id='checked' name='checked'>");
        out.println("<label for='checked'>Remember Me!</label>");
        out.println("</div>");
        out.println("<input type='submit' name='submit' value='Login'>");
        out.println("</form>");
        
        // Handle login and cookie logic after form submission
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        String rememberMe = request.getParameter("checked");
        
        // Check if username and password are provided (skip validation)
        if (username != null && password != null) {
            if ("on".equals(rememberMe)) {
                // Create cookies for the username and password
                javax.servlet.http.Cookie userCookie = new javax.servlet.http.Cookie("username", username);
                javax.servlet.http.Cookie passCookie = new javax.servlet.http.Cookie("password", password);
                userCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                passCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            } else {
                // Remove the cookies if "Remember Me" is not checked
                javax.servlet.http.Cookie userCookie = new javax.servlet.http.Cookie("username", "");
                javax.servlet.http.Cookie passCookie = new javax.servlet.http.Cookie("password", "");
                userCookie.setMaxAge(0); // Expire the cookie immediately
                passCookie.setMaxAge(0); // Expire the cookie immediately
                response.addCookie(userCookie);
                response.addCookie(passCookie);
            }
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}


// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
