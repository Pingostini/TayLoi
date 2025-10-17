package com.empresa.servlets;

import com.empresa.dao.RecepcionDAO;
import com.empresa.model.RecepcionModel;
import com.empresa.model.DetalleRecepcionModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/RecepcionServlet")
public class RecepcionServlet extends HttpServlet {
    private RecepcionDAO dao = new RecepcionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                request.getRequestDispatcher("recepcion_form.jsp").forward(request, response);
                break;
            case "completar":
                completarRecepcion(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
                break;
        }
    }

    // Para acciones que vienen por POST (guardar)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("guardar".equals(accion)) {
            guardarRecepcion(request, response);
        } else {
            // por defecto, redirigir a listar
            response.sendRedirect("RecepcionServlet?accion=listar");
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listaRecepciones", dao.listarRecepciones());
        req.getRequestDispatcher("recepcion.jsp").forward(req, resp);
    }

    private void guardarRecepcion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession sesion = req.getSession();
            String usuario = (String) sesion.getAttribute("usuario"); // tu LoginServlet debe guardar "usuario"
            if (usuario == null) usuario = "desconocido";

            String proveedor = req.getParameter("proveedor");
            String idOrdenStr = req.getParameter("id_orden");
            Integer idOrden = null;
            if (idOrdenStr != null && !idOrdenStr.trim().isEmpty()) {
                try { idOrden = Integer.parseInt(idOrdenStr); } catch (NumberFormatException ex) { idOrden = null; }
            }

            RecepcionModel r = new RecepcionModel();
            r.setIdOrden(idOrden);
            r.setProveedor(proveedor);
            r.setFechaRecepcion(new Date());
            r.setEstado("pendiente");
            r.setRecibidoPor(usuario);

            int idRecepcion = dao.insertarRecepcion(r);

            // detalles (producto[], cantidad[])
            String[] productos = req.getParameterValues("producto");
            String[] cantidades = req.getParameterValues("cantidad");
            if (productos != null && cantidades != null) {
                for (int i = 0; i < productos.length && i < cantidades.length; i++) {
                    String prod = productos[i];
                    String cantStr = cantidades[i];
                    if (prod == null || prod.trim().isEmpty()) continue;
                    int cant = 0;
                    try { cant = Integer.parseInt(cantStr); } catch (Exception ex) { cant = 0; }
                    DetalleRecepcionModel d = new DetalleRecepcionModel();
                    d.setIdRecepcion(idRecepcion);
                    d.setProducto(prod);
                    d.setCantidad(cant);
                    dao.insertarDetalle(d);
                }
            }

            resp.sendRedirect("RecepcionServlet?accion=listar");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al guardar recepción: " + e.getMessage());
            req.getRequestDispatcher("recepcion_form.jsp").forward(req, resp);
        }
    }

    private void completarRecepcion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                dao.actualizarEstado(id, "completada");
            } catch (NumberFormatException e) { /* ignore */ }
        }
        resp.sendRedirect("RecepcionServlet?accion=listar");
    }
}
