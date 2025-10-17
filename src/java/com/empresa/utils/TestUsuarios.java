package com.empresa.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestUsuarios {
    public static void main(String[] args) {
        try (Connection conn = ConexionDB.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conexión establecida. Listando usuarios...\n");

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, nombre, apellido, correo, rol FROM usuarios");

                while (rs.next()) {
                    System.out.println(
                        "ID: " + rs.getInt("id") +
                        " | Nombre: " + rs.getString("nombre") +
                        " " + rs.getString("apellido") +
                        " | Correo: " + rs.getString("correo") +
                        " | Rol: " + rs.getString("rol")
                    );
                }

                rs.close();
                stmt.close();
            } else {
                System.out.println("❌ No se pudo conectar a la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
