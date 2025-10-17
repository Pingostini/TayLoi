<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Recepciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background:#f5f7f4;
            font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        h1 {
            color:#14532d;
            font-weight:700;
        }
        .btn-green { background:#14532d; color:#fff; font-weight:600; }
        .btn-green:hover { background:#0f3d24; }
        .btn-yellow { background:#ffb400; color:#333; font-weight:600; }
        .btn-yellow:hover { background:#e0a000; }
        .btn-gray { background:#6c757d; color:#fff; font-weight:600; }
        .btn-gray:hover { background:#5a6268; }
        .badge-pendiente { background:#ffb400; color:#333; }
        .badge-completo { background:#4caf50; }
    </style>
</head>
<body>

<div class="container my-4">
<div class="mb-3">
    <a href="dashboard" class="btn btn-gray">← Regresar al Dashboard</a>
</div>
    <!-- Encabezado -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>📦 Lista de Recepciones</h1>
        <a class="btn btn-green" href="RecepcionServlet?accion=nuevo">➕ Nueva Recepción</a>
    </div>

    <!-- Mensajes de error -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">${error}</div>
    </c:if>

    <!-- Card con la tabla -->
    <div class="card shadow-sm">
        <div class="card-body">
            <h5 class="card-title mb-3" style="color:#14532d;font-weight:600">📑 Historial de Recepciones</h5>

            <div class="table-responsive">
                <table class="table table-bordered table-hover align-middle">
                    <thead class="table-success">
                        <tr>
                            <th>ID</th>
                            <th>Orden</th>
                            <th>Proveedor</th>
                            <th>Fecha</th>
                            <th>Items</th>
                            <th>Estado</th>
                            <th>Recibido por</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${listaRecepciones}">
                            <tr>
                                <td>${r.idRecepcion}</td>
                                <td><c:out value="${r.idOrden}" default="-" /></td>
                                <td>${r.proveedor}</td>
                                <td>${r.fechaRecepcion}</td>
                                <td><span class="badge bg-secondary">${r.detalles.size()}</span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${r.estado == 'pendiente'}">
                                            <span class="badge badge-pendiente">Pendiente</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-completo">Completa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><small class="text-muted">${r.recibidoPor}</small></td>
                                <td>
                                    <!-- Acciones -->
                                    <div class="d-flex flex-wrap gap-2">
                                        <a href="RecepcionServlet?accion=ver&id=${r.idRecepcion}" 
                                           class="btn btn-sm btn-gray">👁 Ver</a>
                                        <c:if test="${r.estado == 'pendiente'}">
                                            <a href="RecepcionServlet?accion=completar&id=${r.idRecepcion}" 
                                               class="btn btn-sm btn-green">✔ Completar</a>
                                        </c:if>
                                        <a href="RecepcionServlet?accion=eliminar&id=${r.idRecepcion}" 
                                           class="btn btn-sm btn-yellow">🗑 Eliminar</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty listaRecepciones}">
                            <tr>
                                <td colspan="8" class="text-center text-muted">No hay recepciones registradas aún.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
