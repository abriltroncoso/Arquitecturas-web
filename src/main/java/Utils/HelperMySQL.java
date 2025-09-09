package Utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import entity.Cliente;
import entity.Factura;
import entity.FacturaProducto;
import entity.Producto;

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

    public void populateDB() throws Exception {
        try {
            System.out.println("Populating DB...");
            for (CSVRecord row : getData("clientes.csv")) {
                if (row.size() >= 3) {
                    String idString = row.get(0);
                    String nombre = row.get(1);
                    String email = row.get(2);

                    if (!idString.isEmpty() && !nombre.isEmpty() && !email.isEmpty()) {
                        try {
                            int idCliente = Integer.parseInt(idString);
                            Cliente cliente = new Cliente(idCliente, nombre, email);
                            insertCliente(cliente, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de cliente: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Clientes insertados");

            for (CSVRecord row : getData("productos.csv")) {
                if (row.size() >= 3) {
                    String idProducto = row.get(0);
                    String nombre = row.get(1);
                    String valor = row.get(2);

                    if (!idProducto.isEmpty() && !nombre.isEmpty() && !valor.isEmpty()) {
                        try {
                            int idProd = Integer.parseInt(idProducto);
                            float valorProd = Float.parseFloat(valor);
                            Producto producto = new Producto(idProd, nombre, valorProd);
                            insertProducto(producto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de cliente: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Productos insertados");

            for (CSVRecord row : getData("facturas.csv")) {
                if (row.size() >= 2) {
                    String idFacturaStr = row.get(0);
                    String idClienteStr = row.get(1);

                    if (!idFacturaStr.isEmpty() && !idClienteStr.isEmpty()) {
                        try {
                            int idFactura = Integer.parseInt(idFacturaStr);
                            int idCliente = Integer.parseInt(idClienteStr);
                            Factura factura = new Factura(idFactura, idCliente);
                            insertFactura(factura, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de factura: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Facturas instaertadas");

            for (CSVRecord row : getData("facturas-productos.csv")) {
                if (row.size() >= 3) {
                    String idFacturaStr = row.get(0);
                    String idProductoStr = row.get(1);
                    String cantidadStr = row.get(2);

                    if (!idFacturaStr.isEmpty() && !idProductoStr.isEmpty() && !cantidadStr.isEmpty()) {
                        try {
                            int idFactura = Integer.parseInt(idFacturaStr);
                            int idProducto = Integer.parseInt(idProductoStr);
                            int cantidad = Integer.parseInt(cantidadStr);

                            FacturaProducto facturaProducto = new FacturaProducto(idFactura, idProducto, cantidad);
                            insertFacturaProducto(facturaProducto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de factura_producto: " + e.getMessage());
                        } 
                    }
                }
            }
            System.out.println("Factura-productos insertados");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int insertCliente(Cliente cliente, Connection conn) throws Exception {
        String insert = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertProducto(Producto producto, Connection conn) throws Exception {
        String insert = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getValor());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertFactura(Factura factura, Connection conn) throws Exception {
        String insert = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdCliente());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertFacturaProducto(FacturaProducto facturaProducto, Connection conn) throws Exception {
        String insert = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, facturaProducto.getIdFactura());
            ps.setInt(2, facturaProducto.getIdProducto());
            ps.setInt(3, facturaProducto.getCantidad());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private void closePsAndCommit(Connection conn, PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
