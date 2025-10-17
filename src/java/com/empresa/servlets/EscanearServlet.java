package com.empresa.servlets;

import com.empresa.model.ProductoModel;
import com.empresa.utils.ConexionDB;
import com.empresa.utils.ProductoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/EscanearServlet")
public class EscanearServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarHistorial(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // aceptar ambos nombres para compatibilidad (codigo o codigoBarra)
        String codigoBarra = request.getParameter("codigoBarra");
        if (codigoBarra == null || codigoBarra.trim().isEmpty()) {
            codigoBarra = request.getParameter("codigo");
        }

        String cantidadStr = request.getParameter("cantidad");
        int cantidad = 1;
        try {
            if (cantidadStr != null && !cantidadStr.isEmpty()) {
                cantidad = Integer.parseInt(cantidadStr);
            }
        } catch (NumberFormatException ex) {
            cantidad = 1;
        }

        if (codigoBarra == null || codigoBarra.trim().isEmpty()) {
            request.setAttribute("error", "Ingrese un código válido.");
            cargarHistorial(request, response);
            return;
        }

        try (Connection con = ConexionDB.getConnection()) {
            ProductoDAO productoDAO = new ProductoDAO(con);

            boolean ok = productoDAO.registrarEscaneo(codigoBarra.trim(), cantidad);
            if (ok) {
                request.setAttribute("mensaje", "✔️ Escaneo registrado: " + codigoBarra + " x " + cantidad);
            } else {
                request.setAttribute("error", "No se pudo registrar el escaneo (revisa logs).");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error registrando escaneo: " + e.getMessage());
        }

        // recargar historial actualizado
        cargarHistorial(request, response);
    }

    private void cargarHistorial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = ConexionDB.getConnection()) {
            ProductoDAO productoDAO = new ProductoDAO(con);
            List<ProductoModel> lista = productoDAO.listarProductos();
            request.setAttribute("listaProductos", lista);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error cargando productos: " + e.getMessage());
        }
        request.getRequestDispatcher("/escanear.jsp").forward(request, response);
    }
}
