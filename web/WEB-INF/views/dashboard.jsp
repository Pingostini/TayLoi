<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container my-4">
    <h2 class="mb-4">Bienvenido, <%= session.getAttribute("usuario") != null ? session.getAttribute("usuario") : "Usuario" %></h2>

    <div class="row g-4">
        <!-- Eficiencia Promedio -->
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body text-center">
                    <h6 class="text-muted">Eficiencia Promedio</h6>
                    <div class="display-6 fw-bold text-success">
                        <fmt:formatNumber value="${eficienciaPromedio}" maxFractionDigits="0"/> %
                    </div>
                    <small class="text-muted">Comparado con la semana pasada</small>
                </div>
            </div>
        </div>

        <!-- Proveedores Activos -->
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body text-center">
                    <h6 class="text-muted">Proveedores Activos</h6>
                    <div class="display-6 fw-bold text-primary">
                        <fmt:formatNumber value="${proveedoresActivos}" maxFractionDigits="0"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Total Recepciones -->
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body text-center">
                    <h6 class="text-muted">Total Recepciones</h6>
                    <div class="display-6 fw-bold text-info">
                        <fmt:formatNumber value="${totalRecepciones}" maxFractionDigits="0"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Pendientes Validación -->
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body text-center">
                    <h6 class="text-muted">Pendientes Validación</h6>
                    <div class="display-6 fw-bold text-warning">
                        <fmt:formatNumber value="${recepcionesPendientes}" maxFractionDigits="0"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Incidencias Abiertas -->
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body text-center">
                    <h6 class="text-muted">Incidencias Abiertas</h6>
                    <div class="display-6 fw-bold text-danger">
                        <fmt:formatNumber value="${incidenciasAbiertas}" maxFractionDigits="0"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Tiempo Promedio -->
        <div class="col-md-4">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body text-center">
                    <h6 class="text-muted">Tiempo Promedio</h6>
                    <div class="display-6 fw-bold text-secondary">
                        <fmt:formatNumber value="${tiempoPromedio}" maxFractionDigits="0"/> min
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
