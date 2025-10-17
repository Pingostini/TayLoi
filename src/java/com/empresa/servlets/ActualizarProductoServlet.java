package com.empresa.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.empresa.utils.ConexionDB;
import com.empresa.utils.ProductoDAO;
import com.empresa.model.ProductoModel;

@WebServlet("/ActualizarProductoServlet") // ✅ corregido
public class ActualizarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1️⃣ Obtener datos del formulario
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String categoria = request.getParameter("categoria");
            BigDecimal precio = new BigDecimal(request.getParameter("precio"));

            // 2️⃣ Crear objeto producto
            ProductoModel producto = new ProductoModel();
            producto.setId(id);
            producto.setNombre(nombre);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);

            // 3️⃣ Actualizar datos usando DAO
            try (var con = ConexionDB.getConnection()) {
                ProductoDAO productoDAO = new ProductoDAO(con);
                productoDAO.actualizarProducto(producto);
            }

            // 4️⃣ Redirigir al servlet de escaneo (lista de productos)
            response.sendRedirect("EscanearServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar producto");
        }
    }
}
