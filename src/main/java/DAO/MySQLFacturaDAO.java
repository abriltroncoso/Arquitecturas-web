package DAO;

import entity.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLFacturaDAO implements FacturaDAO {
    private Connection conn;

    public MySQLFacturaDAO(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insertar(Factura f) {
        String query = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, f.getIdFactura());
            ps.setInt(2, f.getIdCliente());
            ps.executeUpdate();
            System.out.println("Factura insertada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actualizar(Factura f) {
        String query = "UPDATE Factura SET idCliente = ? WHERE idFactura = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, f.getIdCliente());
            ps.setInt(2, f.getIdFactura());
            ps.executeUpdate();
            System.out.println("Factura actualizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void eliminar(Integer idFactura) {
        String query = "DELETE FROM Factura WHERE idFactura = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, idFactura);
            ps.executeUpdate();
            System.out.println("Factura eliminada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Factura buscarFactura(Integer idFactura) {
        String query = "SELECT idCliente FROM Factura WHERE idFactura = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Factura factura = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, idFactura);
            rs = ps.executeQuery();
            if (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                factura = new Factura(idFactura, idCliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return factura;
    }


}
