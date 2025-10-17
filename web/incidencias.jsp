<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Incidencias</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .form-section {
            background: #ffffff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        .table-section {
            background: #ffffff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            max-height: 80vh;
            overflow-y: auto;
        }
    </style>
</head>
<body>
<div class="container-fluid p-4">

    <!-- Botón de regresar -->
    <div class="mb-3">
        <a href="dashboard" class="btn btn-secondary">← Regresar al Dashboard</a>
    </div>

    <div class="row">
        <!-- Formulario de incidencia -->
        <div class="col-md-4">
            <div class="form-section">
                <h4 class="mb-4">Registrar Incidencia</h4>
                <form action="IncidenciaServlet" method="post">
                    <div class="mb-3">
                        <label class="form-label">Descripción</label>
                        <textarea name="descripcion" class="form-control" rows="3" required></textarea>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Prioridad</label>
                        <select name="prioridad" class="form-select" required>
                            <option value="">Seleccione</option>
                            <option value="Alta">Alta</option>
                            <option value="Media">Media</option>
                            <option value="Baja">Baja</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Responsable</label>
                        <input type="text" name="responsable" class="form-control" placeholder="Nombre del responsable" required>
                    </div>

                    <button type="submit" class="btn btn-success w-100">Guardar Incidencia</button>
                </form>

                <%
                    String mensaje = (String) request.getAttribute("mensaje");
                    if (mensaje != null) {
                %>
                    <div class="alert alert-info mt-3"><%= mensaje %></div>
                <%
                    }
                %>
            </div>
        </div>

        <!-- Tabla de incidencias -->
        <div class="col-md-8">
            <div class="table-section">
                <h4 class="mb-4">Lista de Incidencias</h4>
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Descripción</th>
                            <th>Prioridad</th>
                            <th>Responsable</th>
                            <th>Fecha</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            java.util.List<String[]> incidencias = (java.util.List<String[]>) request.getAttribute("incidencias");
                            if (incidencias != null && !incidencias.isEmpty()) {
                                for (String[] inc : incidencias) {
                        %>
                            <tr>
                                <td><%= inc[0] %></td>
                                <td><%= inc[1] %></td>
                                <td><%= inc[2] %></td>
                                <td><%= inc[3] %></td>
                                <td><%= inc[4] %></td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="5" class="text-center text-muted">No hay incidencias registradas</td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
