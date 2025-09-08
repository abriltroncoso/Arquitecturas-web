import DAO.ProductoDAO;
import entity.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class MySQLDAOProducto implements ProductoDAO {
    private Connection conexion;

    public MySQLDAOProducto(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Producto producto) {
        String query = "INSERT INTO productos VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(query);
            ps.setInt(1,producto.getIdProducto());
            ps.setString(2,producto.getNombre());
            ps.setDouble(3, producto.getValor());

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
                e.printStackTrace();
            }
        }
    }
    @Override
    public void actualizar(Producto producto) {}

    @Override
    public void eliminar(Producto producto) {

    }

    @Override
    public List<Producto> obtenerTodos() {
        return List.of();
    }

    @Override
    public Producto obtenerPorId(long id) {
        return null;
    }

    @Override
    public List<Producto> obtenerPorNombre(String nombre) {
        return List.of();
    }

    @Override
    public List<Producto> obtenerPorCategoria(String categoria) {
        return List.of();
    }

    @Override
    public List<Producto> obtenerPorPrecio(float precio) {
        return List.of();
    }
}





