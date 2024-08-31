/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Akash Maurya R
 */
@WebServlet(name = "CalculatorServlet", urlPatterns = {"/CalculatorServlet"})
public class CalculatorServlet extends HttpServlet {

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
            double num1 = Double.parseDouble(request.getParameter("num1"));
            double num2 = Double.parseDouble(request.getParameter("num2"));
            String operation = request.getParameter("operation");

            double result = 0;
            String operator = "";
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedNum1 = df.format(num1);
            String formattedNum2 = df.format(num2);
            switch (operation) {
                case "add":
                    result = num1 + num2;
                    operator = "+";
                    break;
                case "subtract":
                    result = num1 - num2;
                    operator = "-";
                    break;
                case "multiply":
                    result = num1 * num2;
                    operator = "*";
                    break;
                case "divide":
                    if (num2 != 0) {
                        result = num1 / num2;
                        operator = "/";
                    } else {
                        out.println("Cannot divide by zero");
                        return;
                    }
                    break;
            }
         
            String formattedResult = df.format(result);

            out.println("<p>" + formattedNum1 + " " + operator + " " + formattedNum2 + " = " + formattedResult + "</p>");
            out.println("<a href='index.html'>Home Page</a>");

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
