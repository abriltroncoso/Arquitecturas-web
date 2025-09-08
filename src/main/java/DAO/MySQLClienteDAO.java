package DAO;

import entity.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLClienteDAO implements ClienteDAO {
    private final Connection conn;

    public MySQLClienteDAO(Connection conn){
        this.conn = conn;
        crearTabla();
    }

    private void crearTabla() {
        final String sql = "CREATE TABLE IF NOT EXISTS cliente (" +
                "    idCliente INT NOT NULL," +
                "    nombre VARCHAR(50) NOT NULL," +
                "    email VARCHAR(50) NOT NULL," +
                "    PRIMARY KEY (idCliente)" +
                ");";
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e){
            throw new RuntimeException("Error creando tabla 'cliente'", e);
        }
    }

    @Override
    public void create(Cliente c) {
        final String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getNombre());
            st.setString(2, c.getEmail());
            st.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error al insertar cliente", e);
        }
    }

    @Override
    public void update(Cliente c) {
        final String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE idCliente = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getNombre());
            st.setString(2, c.getEmail());
            st.setInt(3, c.getIdCliente());
            st.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    @Override
    public void delete(Integer idCliente) {
        final String sql = "DELETE FROM cliente WHERE idCliente = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idCliente);
            st.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error al eliminar cliente", e);
        }

    }
}
