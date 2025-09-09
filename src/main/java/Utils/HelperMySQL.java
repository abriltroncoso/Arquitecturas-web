package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HelperMySQL {
    public Connection conn = null;

    public HelperMySQL() {// Constructor
        String uri = "jdbc:mysql://localhost:3306/entregable1";

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTable(String tableName) throws SQLException {
        final String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando tabla '" + tableName + "'", e);
        }
    }

    public void createTables() throws SQLException {
        // Tabla Cliente
        crearTabla("CREATE TABLE IF NOT EXISTS Cliente (" +
                "    idCliente INT NOT NULL AUTO_INCREMENT," +
                "    nombre VARCHAR(500) NOT NULL," +
                "    email VARCHAR(150) NOT NULL," +
                "    PRIMARY KEY (idCliente)" +
                ");");
        // Tabla Producto
        crearTabla("CREATE TABLE IF NOT EXISTS Producto (" +
                "    idProducto INT NOT NULL AUTO_INCREMENT," +
                "    nombre VARCHAR(45) NOT NULL," +
                "    valor FLOAT NOT NULL," +
                "    PRIMARY KEY (idProducto)" +
                ");");
        // Tabla Factura
        crearTabla("CREATE TABLE IF NOT EXISTS Factura (" +
                "    idFactura INT NOT NULL AUTO_INCREMENT," +
                "    idCliente INT NOT NULL," +
                "    PRIMARY KEY (idFactura)," +
                "    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)" +
                ");");
        // Tabla Factura_Producto
        crearTabla("CREATE TABLE IF NOT EXISTS Factura_Producto (" +
                "    idFactura INT NOT NULL," +
                "    idProducto INT NOT NULL," +
                "    cantidad INT NOT NULL," +
                "    PRIMARY KEY (idFactura, idProducto)," +
                "    FOREIGN KEY (idFactura) REFERENCES Factura(idFactura)," +
                "    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)" +
                ");");
    }

    private void crearTabla(String t) throws SQLException {
        this.conn.prepareStatement(t).execute();
        this.conn.commit();
    }
}
