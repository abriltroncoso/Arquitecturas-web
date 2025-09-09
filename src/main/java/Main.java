
import DAO.ClienteDAO;
import Factory.AbstractFactory;
import Factory.MySQLDAOFactory;
import Utils.HelperMySQL;
import entity.Cliente;

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




        ClienteDAO clienteDAO = chosenFactory.getClienteDAO();
        clienteDAO.insertar(new Cliente(1,"Juan Perez", "juan.perez@example.com"));
    }
}
