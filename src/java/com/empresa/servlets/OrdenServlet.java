package com.empresa.servlets;

import com.empresa.dao.OrdenDAO;
import com.empresa.model.OrdenModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/OrdenServlet")
public class OrdenServlet extends HttpServlet {

    private OrdenDAO dao = new OrdenDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                request.getRequestDispatcher("orden_form.jsp").forward(request, response);
                break;
            case "insertar":
                insertarOrden(request, response);
                break;
            case "listar":
            default:
                listarOrdenes(request, response);
                break;
        }
    }

    private void listarOrdenes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OrdenModel> lista = dao.listarOrdenes();
        request.setAttribute("listaOrdenes", lista);
        request.getRequestDispatcher("ordenes.jsp").forward(request, response);
    }

    private void insertarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String proveedor = request.getParameter("proveedor");
            String estado = request.getParameter("estado");

            OrdenModel orden = new OrdenModel();
            orden.setProveedor(proveedor);
            orden.setFechaOrden(new Date()); // fecha actual
            orden.setEstado(estado);

            dao.insertarOrden(orden);
            response.sendRedirect("OrdenServlet?accion=listar");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar la orden.");
            request.getRequestDispatcher("orden_form.jsp").forward(request, response);
        }
    }
}
