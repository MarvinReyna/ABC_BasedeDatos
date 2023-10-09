package cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Clientes {
    public static void main(String[] args) {
        Connection connection = null;
        String url = "jdbc:mariadb://localhost:3306/datoscliente";
        String user = "root";
        String pwd = "1234";

        try {
            connection = DriverManager.getConnection(url, user, pwd);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Menú");
                System.out.println("1. Insertar cliente");
                System.out.println("2. Leer cliente");
                System.out.println("3. Actualizar Datos cliente");
                System.out.println("4.Eliminar cliente");
                System.out.println("5. Salir");
                System.out.print("Ingrese la opcion:");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese nombre cliente ");
                        String nombre = scanner.nextLine();
                        System.out.print("el nit del cliente: ");
                        float nit = scanner.nextFloat();
                        insertarcliente(connection, nombre, nit);
                        break;
                    case 2:
                        LeerClientes(connection);
                        break;
                    case 3:
                        System.out.print("ingrese el nombre del cliente a actualizar: ");
                        String nombreActualizar = scanner.nextLine();
                        System.out.print("Ingrese el nit: ");
                       
                        float nit2 = scanner.nextFloat();
                        actualizarcliente(connection, nombreActualizar, nit2);
                        break;
                    case 4:
                        System.out.print("Ingrese el nombre del cliente a eliminar: ");
                        String nombreEliminar = scanner.nextLine();
                        eliminarcliente(connection, nombreEliminar);
                        break;
                    case 5:
                        System.out.println("Saliendo del programa.");
                        return;
                    default:
                        System.out.println("INGRESE OPCION CORRECTA.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
             try {
                connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void insertarcliente(Connection connection, String nombre, float nit) throws SQLException {
        String sql = "INSERT INTO cliente (nombre, nit) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setFloat(2, nit);
            preparedStatement.executeUpdate();
            System.out.println("Cliente agregago");
        }
    }

    public static void LeerClientes(Connection connection) throws SQLException {
        String sql = "SELECT * FROM cliente";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
             String nombre = resultSet.getString("nombre");
             float nit = resultSet.getFloat("nit");
             System.out.println("Nombre: " + nombre + ", Nit: " + nit);
            }
        }
    }

    public static void actualizarcliente(Connection connection, String nombre, float nit) throws SQLException {
        String sql = "UPDATE cliente  SET nit = ? WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	preparedStatement.setString(2, nombre);
        	preparedStatement.setFloat(1, nit);
            preparedStatement.executeUpdate();
            System.out.println("Cliente ACTUALIZADO.");
        }
    }

    public static void eliminarcliente(Connection connection, String nombre) throws SQLException {
        String sql = "DELETE FROM cliente WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.executeUpdate();
            System.out.println("cliente eliminado");
        }
    }
}
