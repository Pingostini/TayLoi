package com.empresa.servlets;

import com.empresa.utils.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        try (Connection conn = ConexionDB.getConnection()) {
            String sql = "SELECT * FROM usuarios WHERE usuario=? AND clave=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Crear sesión
                HttpSession sesion = request.getSession();

                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");

                // Guardamos datos en sesión
                sesion.setAttribute("usuario", nombreCompleto);
                sesion.setAttribute("rol", rs.getString("rol"));

                // 🔹 Este es el que usa RecepcionServlet
                sesion.setAttribute("usuarioLogueado", nombreCompleto);

                // Redirigir al servlet que carga el dashboard
                response.sendRedirect("dashboard");
            } else {
                // Usuario o clave incorrectos
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}
