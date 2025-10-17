package com.empresa.servlets;

import com.empresa.utils.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/IncidenciaServlet")
public class IncidenciaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String descripcion = request.getParameter("descripcion");

        try (Connection conn = ConexionDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO incidencias (descripcion, estado) VALUES (?, 'abierto')");
            ps.setString(1, descripcion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/PageServlet?page=operaciones&tab=incidencias");
    }
}
