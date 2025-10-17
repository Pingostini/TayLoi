package com.empresa.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/PageServlet")
public class PageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = request.getParameter("page");
        String target;

        if (page == null) {
            target = "/WEB-INF/views/dashboard.jsp";
        } else {
            switch (page) {
                case "dashboard":
                    target = "/WEB-INF/views/dashboard.jsp";
                    break;
                case "recepcion":
                    target = "/WEB-INF/views/recepcion.jsp";
                    break;
                case "operaciones": 
                    // Contenedor que maneja escanear + incidencias
                    target = "/WEB-INF/views/operaciones.jsp";
                    break;
                default:
                    target = "/WEB-INF/views/dashboard.jsp";
                    break;
            }
        }

        // Parámetro para decidir qué tab se activa (escanear/incidencias)
        String tab = request.getParameter("tab");
        if (tab != null) {
            request.setAttribute("tab", tab);
        }

        request.setAttribute("page", target);
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }
}

