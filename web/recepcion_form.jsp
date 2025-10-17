<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Nueva Recepción</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background:#f5f7f4;
            padding:30px;
        }
        h2 { 
            color:#14532d;
            font-weight:700;
            margin-bottom:20px;
        }
        h3 { 
            color:#ffb400;
            font-weight:600;
            margin-bottom:15px;
        }
        .btn-green { background:#0f9d58; color:#fff; font-weight:600; }
        .btn-green:hover { background:#0c7d44; }
        .btn-red { background:#dc3545; color:#fff; font-weight:600; }
        .btn-red:hover { background:#bb2d3b; }
        .btn-blue { background:#007bff; color:#fff; font-weight:600; }
        .btn-blue:hover { background:#0069d9; }
        .btn-gray { background:#6c757d; color:#fff; font-weight:600; }
        .btn-gray:hover { background:#5a6268; }
    </style>
</head>
<body>

<div class="container">

    <!-- Botón regresar -->
    <div class="mb-3">
        <a href="RecepcionServlet?accion=listar" class="btn btn-gray">← Regresar</a>
    </div>

    <h2>➕ Nueva Recepción</h2>

    <form action="RecepcionServlet" method="post">
        <input type="hidden" name="accion" value="guardar"/>

        <!-- Datos principales -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <div class="mb-3 row">
                    <label class="col-sm-3 col-form-label">Id Orden (opcional)</label>
                    <div class="col-sm-9">
                        <input type="text" name="id_orden" placeholder="Ej: 123" class="form-control"/>
                    </div>
                </div>

                <div class="mb-3 row">
                    <label class="col-sm-3 col-form-label">Proveedor</label>
                    <div class="col-sm-9">
                        <input type="text" name="proveedor" required class="form-control"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Detalles -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h3>Detalles</h3>
                <div class="table-responsive">
                    <table class="table table-bordered align-middle text-center" id="detalles-table">
                        <thead class="table-success">
                            <tr>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="text" name="producto" required class="form-control"/></td>
                                <td><input type="number" name="cantidad" min="0" value="1" required class="form-control"/></td>
                                <td>
                                    <button type="button" class="btn btn-red btn-sm" onclick="removeRow(this)">Eliminar</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <button type="button" class="btn btn-green mt-2" onclick="addRow()">➕ Agregar producto</button>
            </div>
        </div>

        <!-- Botones -->
        <button type="submit" class="btn btn-blue">Guardar Recepción</button>
        <a href="RecepcionServlet?accion=listar" class="btn btn-gray ms-2">Cancelar</a>
    </form>

</div>

<script>
    function addRow(){
        const tbody = document.querySelector('#detalles-table tbody');
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><input type="text" name="producto" required class="form-control"/></td>
            <td><input type="number" name="cantidad" min="0" value="1" required class="form-control"/></td>
            <td><button type="button" class="btn btn-red btn-sm" onclick="removeRow(this)">Eliminar</button></td>
        `;
        tbody.appendChild(tr);
    }
    function removeRow(btn){
        const row = btn.closest('tr');
        const tbody = row.parentElement;
        if (tbody.children.length <= 1) {
            row.querySelectorAll('input').forEach(i => i.value = (i.type === 'number'? '1':'' ));
            return;
        }
        row.remove();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
