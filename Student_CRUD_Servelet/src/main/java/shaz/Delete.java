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

@WebServlet("/Delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        int Enrollment = Integer.parseInt(req.getParameter("Eno")); // Assuming 'Eno' is Enrollment number

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "root");

            PreparedStatement ps = con.prepareStatement("DELETE FROM Student WHERE Enrollment = ?");
            ps.setInt(1, Enrollment);

            int count = ps.executeUpdate();
            if (count > 0) {
                resp.setContentType("text/html");
                out.print("<h3 style='color:green'>Student deleted successfully</h3>");

                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.include(req, resp);
            } else {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>Student not found or delete failed</h3>");

                RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();

            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");

            RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
            rd.include(req, resp);
        }
    }
}
