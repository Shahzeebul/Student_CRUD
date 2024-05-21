package shaz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
    

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        
        int Eno = Integer.parseInt(req.getParameter("Eno"));
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String course = req.getParameter("course");
        
        String email = req.getParameter("email");
        String password = req.getParameter("password"); 
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "root");
            
            PreparedStatement ps = con.prepareStatement("insert into Student (Enrollment, Name, Age, Course, Email, Password) values(?, ?, ?, ?, ?, ?)");
            ps.setInt(1, Eno);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, course);
            
            ps.setString(5, email);
            ps.setString(6, password); 
            
            int count = ps.executeUpdate();
            if(count > 0) {
                resp.setContentType("text/html");
                out.print("<h3 style='color:green'>Employee registered successfully</h3>");
                
                RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
                rd.include(req, resp);
            } else {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>Employee not registered due to some error</h3>");
                
                RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            
            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
            
            RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
            rd.include(req, resp);
        }
    }
}
