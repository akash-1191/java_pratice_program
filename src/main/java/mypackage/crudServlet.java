/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Akash Maurya R
 */
@WebServlet(name = "crudServlet", urlPatterns = {"/crudServlet"})
public class crudServlet extends HttpServlet {

    Connection conn;

    public crudServlet() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/msc?useSSL=false";
        String user = "root";
        String password = "am1191";
        conn = DriverManager.getConnection(url, user, password);
        out.println("Connection establish");

    }

    private void showData(PrintWriter out) throws SQLException {

        String showData = "select * from productmaster";
        Statement smt = conn.createStatement();
        ResultSet rs = smt.executeQuery(showData);

        out.println("<table border='2' cellspacing='0'>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Product_Name</th>");
        out.println("<th>Produc_Price</th>");
        out.println("<th>Product_Unit</th>");
        out.println("<th>Stock</th>");
        out.println("<th colspan='2'>Action</th>");
        out.println("</tr>");

        while (rs.next()) {
            int proid = rs.getInt("proid");
            out.println("<tr>");
            out.println("<td>" + proid + "</td>");
            out.println("<td>" + rs.getString("proname") + "</td>");
            out.println("<td>" + rs.getInt("proprice") + "</td>");
            out.println("<td>" + rs.getInt("prounit") + "</td>");
            out.println("<td>" + rs.getString("stock") + "</td>");
            out.println("<td><button onclick=\"openModal()\" "
                    + "proid='" + proid + "'"
                    + " proname='" + rs.getString("proname")
                    + "' proprice='" + rs.getInt("proprice")
                    + "' prounit='" + rs.getInt("prounit")
                    + "'stock='" + rs.getString("stock")
                    + "' style='color:blue; cursor:pointer;padding:5px; background-color:white;border:none;'>Update</button></td>");
            out.println("<form method='post'onsubmit='return confirmDelete()'>");
            out.println("<input type='hidden' name='proid' value='" + proid + "'/>");
            out.println("<td><input type='submit' name='delete' value='Delete' style='color:red; cursor:pointer; padding:5px; border:none; background-color:white;'/></td>");
            out.println("</form>");
            out.println("</tr>");
        }

    }

    private void addData(String pname, int proprice, int prounit, String stock) throws SQLException {

        String insert = "INSERT INTO productmaster (proname, proprice, prounit, stock) VALUES (?, ?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(insert);
        psmt.setString(1, pname);
        psmt.setInt(2, proprice);
        psmt.setInt(3, prounit);
        psmt.setString(4, stock);
        psmt.executeUpdate();
    }

    private void deleteData(int proid) throws SQLException {
        String del = "delete from productmaster where proid=?";
        PreparedStatement psmt = conn.prepareStatement(del);
        psmt.setInt(1, proid);
        psmt.executeUpdate();
    }

    private void updateData(String proname, int proprice, int prounit, String stock, int proid) throws SQLException {
        String update = "UPDATE productmaster SET proname=?, proprice=?, prounit=?, stock=? WHERE proid=?";
        try (PreparedStatement psmt = conn.prepareStatement(update)) {
            psmt.setString(1, proname);
            psmt.setInt(2, proprice);
            psmt.setInt(3, prounit);
            psmt.setString(4, stock);
            psmt.setInt(5, proid);
            psmt.executeUpdate();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (request.getParameter("submit") != null) {
                String pname = request.getParameter("proname");
                int proprice = Integer.parseInt(request.getParameter("proprice"));
                int prounit = Integer.parseInt(request.getParameter("prounit"));
                String stock = request.getParameter("stock");
                addData(pname, proprice, prounit, stock);
                out.println("<script> alert('Data Inserted...') </script>");
            }
            if (request.getParameter("delete") != null) {
                int proid = Integer.parseInt(request.getParameter("proid"));
                out.println("<script>");
                out.println("function confirmDelete() {");
                out.println("return confirm('Are you sure you want to delete this item?');");
                out.println("}");
                out.println("</script>");
                deleteData(proid);
                out.println("<script>alert('Data Deleted...');</script>");
            }
            if (request.getParameter("update") != null) {
                String pname = request.getParameter("proname");
                int proprice = Integer.parseInt(request.getParameter("proprice"));
                int prounit = Integer.parseInt(request.getParameter("prounit"));
                String stock = request.getParameter("stock");
                int proid = Integer.parseInt(request.getParameter("proid"));
                updateData(pname, proprice, prounit, stock, proid);
                out.println("<script>alert('Data Updated...');</script>");
            }

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet crudServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form  method='post'>");
            out.println("<label> Product Name</label>");
            out.println("<input type='text' name='proname'/> ");
            out.println("<br/><br/>");
            out.println("<label> Product Price </label>");
            out.println("<input type='text' name='proprice' />");
            out.println("<br/><br/>");
            out.println("<label> Product Unit </label>");
            out.println("<input type='text' name='prounit' />");
            out.println("<br/><br/>");
            out.println("<label> Stock </label>");
            out.println("<input type='text' name='stock' />");
            out.println("<br/><br/>");
            out.println("<input type='submit' name='submit' value='submit' style='margin-left:50px;'/>");
            out.println("</form>");
            out.println("<br/><br/>");

            out.println("<dialog id='updateDialog' style='border:2px solid black; width:30%; padding:10px'>");
            out.println("<form id='updateForm' method='post'>");
            out.println("<input type='hidden' id='proid' name='proid'/>");
            out.println("<label>Product Name</label>");
            out.println("<input type='text' id='proname' name='proname'/> ");
            out.println("<br/><br/>");
            out.println("<label>Product Price</label>");
            out.println("<input type='text' id='proprice' name='proprice'/>");
            out.println("<br/><br/>");
            out.println("<label>Product Unit</label>");
            out.println("<input type='text' id='prounit' name='prounit'/>");
            out.println("<br/><br/>");
            out.println("<label>Stock</label>");
            out.println("<input type='text' id='stock' name='stock'/>");
            out.println("<br/><br/>");
            out.println("<input type='submit' id='update' name='update' value='Update' style='margin-left:50px;'/>");
            out.println("<button type='button'onclick=\"closeModal()\"  style='margin-left:50px;'>Cancel</button>");
            out.println("</form>");
            out.println("</dialog>");
            out.println("<br/><br/>");

            out.println("<script>");
            out.println("function openModal() {");
            out.println("const dialog = document.querySelector('#updateDialog');");
            out.println("const button = event.target;");
            out.println("document.querySelector('#updateForm').reset();");
            out.println("document.querySelector('#proid').value = button.getAttribute('proid');");
            out.println("document.querySelector('#proname').value = button.getAttribute('proname');");
            out.println("document.querySelector('#proprice').value = button.getAttribute('proprice');");
            out.println("document.querySelector('#prounit').value = button.getAttribute('prounit');");
            out.println("document.querySelector('#stock').value = button.getAttribute('stock');");
            out.println("dialog.showModal();");
            out.println("}");
            out.println("function closeModal() {");
            out.println("const dialog = document.querySelector('#updateDialog');");
            out.println("dialog.close();");
            out.println("}");
            out.println("</script>");

            showData(out);

//            out.println("<h1>Servlet crudServlet at " + request.getContextPath() + "</h1>");
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
        try {
            try {
                processRequest(request, response);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(crudServlet.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (SQLException ex) {
            Logger.getLogger(crudServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            try {
                processRequest(request, response);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(crudServlet.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (SQLException ex) {
            Logger.getLogger(crudServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
