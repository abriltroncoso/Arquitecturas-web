
import DAO.ClienteDAO;
import DAO.ProductoDAO;
import Factory.AbstractFactory;
import Factory.MySQLDAOFactory;
import Utils.HelperMySQL;
import entity.Cliente;
import entity.Producto;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        MySQLDAOFactory factory = MySQLDAOFactory.getInstance();
        Connection conn = factory.createConnection();

        HelperMySQL helper = new HelperMySQL(conn);
        helper.dropTable("factura_producto");
        helper.dropTable("factura");
        helper.dropTable("producto");
        helper.dropTable("cliente");
        helper.createTables();
        helper.populateDB();

        ProductoDAO productoDAO = factory.getProductoDAO();
        Producto productoMayorRecaudacion = productoDAO.obtenerProductoMayorRecaudacion();
        System.out.println("Producto con mayor recaudación:");
        System.out.println(productoMayorRecaudacion);
        /*
         * ClienteDAO clienteDAO = chosenFactory.getClienteDAO();
         * clienteDAO.insertar(new Cliente(1,"Juan Perez", "juan.perez@example.com"));
         * 
         * ProductoDAO productoDAO = chosenFactory.getProductoDAO();
         * productoDAO.insertar(new Producto(1,"yerba",20.5f));
         * productoDAO.obtenerTodos();
         */
    }
}
