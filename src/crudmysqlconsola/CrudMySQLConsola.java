/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crudmysqlconsola;

import java.sql.*;
import java.util.Scanner;

public class CrudMySQLConsola {

    // Definir la conexión a la base de datos de MySQL
    private static Connection conn;

    public static void main(String[] args) {

        // Conectar a la base de datos de MySQL
        conn = getConnection();

        // Mostrar opciones al usuario
        Scanner sc = new Scanner(System.in);
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println("Elija una opción:");
            System.out.println("1. Obtener todos los datos");
            System.out.println("2. Insertar nuevo dato");
            System.out.println("3. Actualizar un dato");
            System.out.println("4. Eliminar un dato");
            System.out.println("0. Salir");
            opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    obtenerTodos();
                    break;
                case "2":
                    insertar();
                    break;
                case "3":
                    actualizar();
                    break;
                case "4":
                    eliminar();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }

        // Cerrar la conexión
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void obtenerTodos() {
        System.out.println("Obteniendo todos los registros...");

        // Obtener todos los registros de la tabla
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                int edad = rs.getInt("edad");
                System.out.printf("%d\t%s\t%s\t%s\t%d%n", id, nombre, apellido, email, edad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertar() {
        System.out.println("Insertando nuevo registro...");

        // Obtener datos del usuario
        Scanner sc = new Scanner(System.in);
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Apellido:");
        String apellido = sc.nextLine();
        System.out.println("Email:");
        String email = sc.nextLine();
        System.out.println("Edad:");
        int edad = sc.nextInt();

        // Insertar un nuevo registro en la tabla
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO usuarios (nombre, apellido, email, edad) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido        );
        pstmt.setString(3, email);
        pstmt.setInt(4, edad);
        pstmt.executeUpdate();
        System.out.println("Registro insertado correctamente");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void actualizar() {
    System.out.println("Actualizando un registro...");

    // Obtener ID del usuario
    Scanner sc = new Scanner(System.in);
    System.out.println("ID del usuario a actualizar:");
    int id = sc.nextInt();
    sc.nextLine();
    // Verificar si el registro existe
    try {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("El registro no existe");
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Obtener nuevos datos del usuario
    System.out.println("Nombre:");
    String nombre = sc.nextLine();
    System.out.println("Apellido:");
    String apellido = sc.nextLine();
    System.out.println("Email:");
    String email = sc.nextLine();
    System.out.println("Edad:");
    int edad = sc.nextInt();

    // Actualizar el registro en la tabla
    try {
        PreparedStatement pstmt = conn.prepareStatement("UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, edad = ? WHERE id = ?");
        pstmt.setString(1, nombre);
        pstmt.setString(2, apellido);
        pstmt.setString(3, email);
        pstmt.setInt(4, edad);
        pstmt.setInt(5, id);
        pstmt.executeUpdate();
        System.out.println("Registro actualizado correctamente");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void eliminar() {
    System.out.println("Eliminando un registro...");

    // Obtener ID del usuario
    Scanner sc = new Scanner(System.in);
    System.out.println("ID del usuario a eliminar:");
    int id = sc.nextInt();

    // Verificar si el registro existe
    try {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            System.out.println("El registro no existe");
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Eliminar el registro de la tabla
    try {
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM usuarios WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Registro eliminado correctamente");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Método para conectar a la base de datos de MySQL
private static Connection getConnection() {
    Connection conn = null;
    try {
        // Cargar el driver JDBC de MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Obtener la conexión a la base de datos
        String url = "jdbc:mysql://bpuzaom7ftfdyhtyp8qs-mysql.services.clever-cloud.com:3306/bpuzaom7ftfdyhtyp8qs?user=u54ykuipbzwuhdau&password=02AfcYPCNSyhRB0ghp4G&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        conn = DriverManager.getConnection(url);
        System.out.println("Conexión establecida correctamente");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return conn;
}
}
