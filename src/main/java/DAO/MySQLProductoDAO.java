package DAO;
import entity.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductoDAO implements ProductoDAO {
    private Connection conexion;

    public MySQLProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Producto producto) {
        String query = "INSERT INTO producto VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(query);
            ps.setInt(1,producto.getIdProducto());
            ps.setString(2,producto.getNombre());
            ps.setFloat(3, producto.getValor());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto insertado correctamente.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {

            }
        }
    }
    @Override
    public void actualizar(Producto producto) {
        String query = "UPDATE productos SET nombre=?, valor=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(query);
            ps.setString(1,producto.getNombre());
            ps.setFloat(2,producto.getValor());
            ps.setInt(3,producto.getIdProducto());
            int filasActualizadas = ps.executeUpdate();
            if(filasActualizadas > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto con el ID especificado.");
            }
        }catch (SQLException e) {

        } finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public void eliminar(Producto producto) {
        String query = "DELETE FROM productos WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(query);
            ps.setInt(1,producto.getIdProducto());
            int filasEliminadas = ps.executeUpdate();
            if(filasEliminadas > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontró el producto con el ID especificado.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM producto";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conexion.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                Float precio = rs.getFloat("valor");

                Producto producto = new Producto(id, nombre, precio);
                productos.add(producto);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productos;

    }

    @Override
    public Producto obtenerPorId(int id) {
        Producto producto = null;
        String query = "SELECT * FROM producto WHERE idProducto=?";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(query);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if (rs.next()){
                String nombre = rs.getString("nombre");
                Float precio = rs.getFloat("precio");

                producto = new Producto(id, nombre, precio);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return  producto;
    }


}





