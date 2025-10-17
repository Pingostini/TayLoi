<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tai Loy - Logistics System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body { font-family: Arial, sans-serif; }

        /* Sidebar */
        .sidebar {
            min-height: 100vh;
            background-color: #0d3b2e;
            color: white;
            padding: 20px 15px;
        }
        .sidebar h4 {
            font-size: 1.2rem;
            margin-bottom: 1.5rem;
        }
        .sidebar a {
            color: white;
            display: block;
            padding: 10px 12px;
            margin: 6px 0;
            text-decoration: none;
            border-radius: 6px;
            transition: background 0.2s;
        }
        .sidebar a:hover {
            background-color: #145c43;
        }

        /* Contenido */
        main {
            background-color: #f8f9fa;
            min-height: 100vh;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        
        <!-- Sidebar -->
        <nav class="col-md-2 col-lg-2 sidebar d-flex flex-column">
            <h4 class="text-center">Tai Loy<br>Logistics System</h4>
            <p class="text-center mb-4">
                <strong><%= session.getAttribute("rol") %></strong>
            </p>

            <a href="dashboard">Dashboard</a>
            <a href="recepcion.jsp">Recepción</a>
            <a href="escanear.jsp">Escanear</a>
            <a href="incidencias.jsp">Incidencias</a>

            <div class="mt-auto">
                <hr class="border-light">
                <a href="LogoutServlet" class="text-danger">Cerrar Sesión</a>
            </div>
        </nav>

        <!-- Main Content -->
        <main class="col-md-10 col-lg-10 p-4">
            <%-- Mostrar la vista dinámica enviada desde el servlet --%>
            <%
                String vista = (String) request.getAttribute("page");
                if (vista == null) vista = "/WEB-INF/views/dashboard.jsp";
            %>
            <jsp:include page="<%= vista %>" />
        </main>
    </div>
</div>
</body>
</html>
