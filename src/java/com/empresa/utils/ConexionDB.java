// Archivo: ConexionDB.java
package com.empresa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_logistica?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // 🚨 CAMBIO CLAVE: Elimina el try-catch y propaga la excepción
    public static Connection getConnection() throws SQLException { 
        try {
            // Ya no es estrictamente necesario en JDBC moderno, pero es seguro mantenerlo
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            // Convierte la excepción de ClassNotFound en una excepción de SQL para un manejo uniforme
            throw new SQLException("No se encontró el Driver JDBC: " + e.getMessage()); 
        }
        
        // Si hay error en la URL, usuario o clave, DriverManager lanza SQLException directamente
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        // Opcional: System.out.println("✅ Conexión exitosa a la base de datos");
        return conn;
    }
}