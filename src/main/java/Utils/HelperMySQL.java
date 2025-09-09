package Utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class HelperMySQL {
    public Connection conn = null;

    public HelperMySQL(Connection conn) {
        this.conn = conn;
    }


    public void dropTable(String tableName) throws SQLException {
        final String sql = "DROP TABLE IF EXISTS " + tableName;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando tabla '" + tableName + "'", e);
        }
    }

    private void crearTabla(String t) throws SQLException {
        this.conn.prepareStatement(t).execute();
        this.conn.commit();
    }

    public void createTables() throws SQLException {
        // Tabla Cliente
        // Tabla cliente
        crearTabla("CREATE TABLE IF NOT EXISTS cliente (" +
                "    idCliente INT NOT NULL AUTO_INCREMENT," +
                "    nombre VARCHAR(500) NOT NULL," +
                "    email VARCHAR(150) NOT NULL," +
                "    CONSTRAINT pk_cliente PRIMARY KEY (idCliente)" +
                ");");

        // Tabla producto
        crearTabla("CREATE TABLE IF NOT EXISTS producto (" +
                "    idProducto INT NOT NULL," +
                "    nombre VARCHAR(45) NOT NULL," +
                "    valor FLOAT NOT NULL," +
                "    CONSTRAINT pk_producto PRIMARY KEY (idProducto)" +
                ");");

        // Tabla factura
        crearTabla("CREATE TABLE IF NOT EXISTS factura (" +
                "    idFactura INT NOT NULL," +
                "    idCliente INT NOT NULL," +
                "    CONSTRAINT pk_factura PRIMARY KEY (idFactura)," +
                "    CONSTRAINT fk_factura_cliente FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)" +
                ");");

        // Tabla factura_producto
        crearTabla("CREATE TABLE IF NOT EXISTS factura_producto (" +
                "    idFactura INT NOT NULL," +
                "    idProducto INT NOT NULL," +
                "    cantidad INT NOT NULL," +
                "    CONSTRAINT pk_factura_producto PRIMARY KEY (idFactura, idProducto)," +
                "    CONSTRAINT fk_factura_producto_factura FOREIGN KEY (idFactura) REFERENCES factura(idFactura)," +
                "    CONSTRAINT fk_factura_producto_producto FOREIGN KEY (idProducto) REFERENCES producto(idProducto)" +
                ");");

    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src\\main\\resources\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }

}
