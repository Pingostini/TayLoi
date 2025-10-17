package com.empresa.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.empresa.model.ProductoModel;

public class ProductoDAO {

    private Connection con;

    // 🔹 Constructor: recibe la conexión
    public ProductoDAO(Connection con) {
        this.con = con;
    }

    // ✅ Insertar producto nuevo
    public boolean insertarProducto(ProductoModel producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, codigo_barra, categoria, precio, stock, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setString(3, producto.getCodigoBarra());
            ps.setString(4, producto.getCategoria());
            ps.setBigDecimal(5, producto.getPrecio());
            ps.setInt(6, producto.getStock());
            ps.setBoolean(7, producto.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Buscar producto por código de barras
    public ProductoModel buscarPorCodigoBarra(String codigoBarra) {
        String sql = "SELECT * FROM productos WHERE codigo_barra = ?";
        ProductoModel p = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigoBarra);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new ProductoModel();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCodigoBarra(rs.getString("codigo_barra"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setActivo(rs.getBoolean("activo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    // ✅ Actualizar stock de un producto existente
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Listar todos los productos
    public List<ProductoModel> listarProductos() {
        List<ProductoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY id DESC";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoModel p = new ProductoModel();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCodigoBarra(rs.getString("codigo_barra"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setActivo(rs.getBoolean("activo"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    // ✅ Actualizar producto completo
    public boolean actualizarProducto(ProductoModel producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setBigDecimal(3, producto.getPrecio()); // usa BigDecimal
            ps.setInt(4, producto.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Registrar escaneo: si existe el producto -> suma stock, si no existe -> lo inserta
public boolean registrarEscaneo(String codigoBarra, int cantidad) {
    String sqlSelect = "SELECT id, stock FROM productos WHERE codigo_barra = ?";
    String sqlUpdate = "UPDATE productos SET stock = ? WHERE id = ?";
    String sqlInsert = "INSERT INTO productos (nombre, descripcion, codigo_barra, categoria, precio, stock, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (PreparedStatement ps = con.prepareStatement(sqlSelect)) {
        ps.setString(1, codigoBarra);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // 📌 Ya existe el producto → actualizamos stock
                int id = rs.getInt("id");
                int stockActual = rs.getInt("stock");
                try (PreparedStatement psUp = con.prepareStatement(sqlUpdate)) {
                    psUp.setInt(1, stockActual + cantidad);
                    psUp.setInt(2, id);
                    return psUp.executeUpdate() > 0;
                }
            } else {
                // 📌 No existe → insertamos un nuevo producto mínimo
                try (PreparedStatement psIns = con.prepareStatement(sqlInsert)) {
                    psIns.setString(1, "Producto sin nombre");       // Nombre genérico
                    psIns.setString(2, "Registrado por escaneo");    // Descripción
                    psIns.setString(3, codigoBarra);                 // Código de barras
                    psIns.setString(4, "General");                   // Categoría por defecto
                    psIns.setBigDecimal(5, new java.math.BigDecimal("0.00")); // Precio inicial
                    psIns.setInt(6, cantidad);                       // Stock inicial
                    psIns.setBoolean(7, true);                       // Activo = true
                    return psIns.executeUpdate() > 0;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    


}
