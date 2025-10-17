<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.empresa.model.ProductoModel" %>

<%
    List<ProductoModel> listaProductos = (List<ProductoModel>) request.getAttribute("listaProductos");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Escanear Productos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body {
        background:#f5f7f4;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color:#333;
    }
    h1 {
        color:#14532d;
        font-weight:700;
        text-align:center;
        margin-bottom:25px;
    }
    h2 {
        color:#ffb400;
        font-weight:700;
        margin-bottom:20px;
    }
    .btn-green {
        background:#14532d;
        color:#fff;
        font-weight:600;
    }
    .btn-green:hover {
        background:#0f3d24;
    }
    .btn-yellow {
        background:#ffb400;
        color:#333;
        font-weight:600;
    }
    .btn-yellow:hover {
        background:#e0a000;
    }
    .btn-gray {
        background:#6c757d;
        color:#fff;
    }
    .btn-gray:hover {
        background:#5a6268;
    }
    .modal-content h3 {
        color:#14532d;
        margin-bottom:15px;
    }
</style>
</head>
<body class="p-4">

<!-- Botón regresar arriba -->
<div class="mb-3">
    <a href="dashboard" class="btn btn-gray">← Regresar al Dashboard</a>
</div>

<h1>Escanear Código de Barras</h1>

<div class="container-fluid">
  <div class="row g-4">

    <!-- Formulario a la izquierda -->
    <div class="col-md-4">
      <div class="card shadow-sm">
        <div class="card-body">
          <h2 class="card-title">Registrar Escaneo</h2>
          <form action="EscanearServlet" method="post">
              <div class="mb-3">
                <label for="codigo" class="form-label">Código de barras:</label>
                <input type="text" id="codigo" name="codigo" class="form-control" placeholder="Escanee o ingrese el código" required>
              </div>
              <div class="mb-3">
                <label for="cantidad" class="form-label">Cantidad:</label>
                <input type="number" id="cantidad" name="cantidad" class="form-control" value="1" min="1" required>
              </div>
              <button type="submit" class="btn btn-green w-100">Registrar</button>
          </form>
        </div>
      </div>
    </div>

    <!-- Historial a la derecha -->
    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-body">
          <button class="btn btn-yellow mb-3" type="button" onclick="toggleHistorial()">Ver Productos Validados</button>
          <div id="historial" class="d-none">
            <h2>Productos Registrados</h2>
            <div class="table-responsive">
              <table class="table table-bordered align-middle text-center">
                <thead class="table-success">
                  <tr>
                      <th>ID</th>
                      <th>Nombre</th>
                      <th>Código de Barra</th>
                      <th>Categoría</th>
                      <th>Precio</th>
                      <th>Stock</th>
                      <th>Acción</th>
                  </tr>
                </thead>
                <tbody>
                  <% if (listaProductos != null && !listaProductos.isEmpty()) { %>
                      <% for (ProductoModel p : listaProductos) { %>
                      <tr>
                          <td><%= p.getId() %></td>
                          <td><%= p.getNombre() %></td>
                          <td><%= p.getCodigoBarra() %></td>
                          <td><%= p.getCategoria() %></td>
                          <td>S/. <%= p.getPrecio() %></td>
                          <td><%= p.getStock() %></td>
                          <td>
                              <button type="button" class="btn btn-green btn-sm"
                                      onclick="openModal(<%=p.getId()%>, '<%=p.getNombre()%>', '<%=p.getCategoria()%>', <%=p.getPrecio()%>, <%=p.getStock()%>)">
                                  Editar
                              </button>
                          </td>
                      </tr>
                      <% } %>
                  <% } else { %>
                      <tr>
                          <td colspan="7" class="text-muted">No hay productos registrados aún.</td>
                      </tr>
                  <% } %>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modalEditar" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content p-3">
      <div class="modal-header">
        <h3 class="modal-title">Editar Producto</h3>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <form action="ActualizarProductoServlet" method="post">
        <div class="modal-body">
            <input type="hidden" id="modalId" name="id">
            <div class="mb-3">
              <label class="form-label">Nombre:</label>
              <input type="text" id="modalNombre" name="nombre" class="form-control" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Categoría:</label>
              <input type="text" id="modalCategoria" name="categoria" class="form-control">
            </div>
            <div class="mb-3">
              <label class="form-label">Precio:</label>
              <input type="number" step="0.01" id="modalPrecio" name="precio" class="form-control" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Stock:</label>
              <input type="number" id="modalStock" name="stock" class="form-control" required>
            </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-green">Actualizar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
function toggleHistorial(){
    document.getElementById("historial").classList.toggle("d-none");
}
function openModal(id, nombre, categoria, precio, stock){
    document.getElementById('modalId').value = id;
    document.getElementById('modalNombre').value = nombre;
    document.getElementById('modalCategoria').value = categoria;
    document.getElementById('modalPrecio').value = precio;
    document.getElementById('modalStock').value = stock;
    var modal = new bootstrap.Modal(document.getElementById('modalEditar'));
    modal.show();
}
</script>

</body>
</html>
