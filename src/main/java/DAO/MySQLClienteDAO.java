package DAO;

import entity.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLClienteDAO implements ClienteDAO {
    private final Connection conn;

    public MySQLClienteDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insertar(Cliente c) {
        String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
        PreparedStatement ps = null;
    
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar cliente", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE idCliente = ?";
        PreparedStatement ps = null;
    
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setInt(3, c.getIdCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void eliminar(Integer idCliente) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        PreparedStatement ps = null;
    
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los clientes", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientes;
    }

    @Override
    public Cliente obtenerPorId(int idCliente) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cliente por ID", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cliente;
    }
    @Override
    public List<Cliente> obtenerClientePorFacturacion() {
        List<Cliente> clientes = new ArrayList<>();
        String sql ="""
            SELECT c.idCliente, c.nombre, c.email
            FROM Cliente c
            JOIN Factura f ON c.idCliente = f.idCliente
            JOIN Factura_Producto fp ON f.idFactura = fp.idFactura
            JOIN Producto p ON fp.idProducto = p.idProducto
            GROUP BY c.idCliente, c.nombre, c.email
            ORDER BY SUM(fp.cantidad * p.valor) DESC
            """;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener clientes por facturación", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientes;
    }

    }
    
    

