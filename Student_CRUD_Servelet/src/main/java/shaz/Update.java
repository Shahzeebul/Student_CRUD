package shaz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        // Retrieve and parse parameters
        int Eno = Integer.parseInt(req.getParameter("Eno"));
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String course = req.getParameter("course");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "root");

            // Prepare SQL update statement
            ps = con.prepareStatement("UPDATE Student SET Name=?, Age=?, Course=? WHERE Enrollment=?");

            // Set the parameters for the prepared statement
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setInt(4, Eno);

            // Execute the update
            int count = ps.executeUpdate();

            // Check if the update was successful
            if (count > 0) {
                resp.setContentType("text/html");
                out.print("<h3 style='color:green'>Student updated successfully</h3>");
            } else {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>Student not found or update failed</h3>");
            }

            // Forward to the profile.jsp
            RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
            rd.include(req, resp);

        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
            rd.include(req, resp);
        } finally {
            // Close the resources to avoid resource leaks
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
