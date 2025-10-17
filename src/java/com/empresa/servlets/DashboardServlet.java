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
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) comprobar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            if (conn == null) {
                throw new ServletException("No hay conexión a la BD (ConexionDB.getConnection() devolvió null).");
            }

            // Total recepciones
            int totalRecepciones = 0;
            ps = conn.prepareStatement("SELECT COUNT(*) FROM recepciones");
            rs = ps.executeQuery();
            if (rs.next()) totalRecepciones = rs.getInt(1);
            request.setAttribute("totalRecepciones", totalRecepciones);
            closeQuietly(rs); closeQuietly(ps);

            // Pendientes
            int pendientes = 0;
            ps = conn.prepareStatement("SELECT COUNT(*) FROM recepciones WHERE estado='pendiente'");
            rs = ps.executeQuery();
            if (rs.next()) pendientes = rs.getInt(1);
            request.setAttribute("recepcionesPendientes", pendientes);
            closeQuietly(rs); closeQuietly(ps);

            // Proveedores activos
            int proveedoresActivos = 0;
            ps = conn.prepareStatement("SELECT COUNT(*) FROM proveedores WHERE activo=1");
            rs = ps.executeQuery();
            if (rs.next()) proveedoresActivos = rs.getInt(1);
            request.setAttribute("proveedoresActivos", proveedoresActivos);
            closeQuietly(rs); closeQuietly(ps);

            // Incidencias abiertas
            int incidenciasAbiertas = 0;
            ps = conn.prepareStatement("SELECT COUNT(*) FROM incidencias WHERE estado='abierto'");
            rs = ps.executeQuery();
            if (rs.next()) incidenciasAbiertas = rs.getInt(1);
            request.setAttribute("incidenciasAbiertas", incidenciasAbiertas);
            closeQuietly(rs); closeQuietly(ps);

            // Eficiencia (validada / total)
            int validadas = 0;
            ps = conn.prepareStatement("SELECT COUNT(*) FROM recepciones WHERE estado='validada'");
            rs = ps.executeQuery();
            if (rs.next()) validadas = rs.getInt(1);
            double eficiencia = (totalRecepciones > 0) ? (validadas * 100.0 / totalRecepciones) : 0.0;
            request.setAttribute("eficienciaPromedio", eficiencia);
            closeQuietly(rs); closeQuietly(ps);

            // Tiempo promedio (usa fecha_inicio / fecha_fin si existen)
            double tiempoPromedio = 0.0;
            ps = conn.prepareStatement(
                "SELECT AVG(TIMESTAMPDIFF(MINUTE, fecha_inicio, fecha_fin)) FROM recepciones " +
                "WHERE fecha_inicio IS NOT NULL AND fecha_fin IS NOT NULL"
            );
            rs = ps.executeQuery();
            if (rs.next()) {
                tiempoPromedio = rs.getDouble(1); // puede ser NULL -> rs.getDouble devuelve 0.0
            }
            request.setAttribute("tiempoPromedio", tiempoPromedio);
            closeQuietly(rs); closeQuietly(ps);

            // Decimos al layout qué vista incluir
            request.setAttribute("page", "/WEB-INF/views/dashboard.jsp");

            // DEBUG: imprime en consola (temporal)
            System.out.println("DEBUG dashboard: total=" + totalRecepciones + " pendientes=" + pendientes +
                               " prov=" + proveedoresActivos + " ef=" + eficiencia + " tp=" + tiempoPromedio);

            // Forward al layout (que incluirá la vista)
            request.getRequestDispatcher("/layout.jsp").forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServletException("Error SQL en DashboardServlet: " + ex.getMessage(), ex);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }

    private void closeQuietly(AutoCloseable c) {
        if (c == null) return;
        try { c.close(); } catch (Exception ignore) {}
    }
}
