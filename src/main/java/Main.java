
import DAO.ClienteDAO;
import Factory.AbstractFactory;
import Utils.HelperMySQL;
import entity.Cliente;

public class Main {
    public static void main(String[] args) throws Exception {
        HelperMySQL helper = new HelperMySQL();
        helper.dropTable("factura_producto");
        helper.dropTable("factura");
        helper.dropTable("producto");
        helper.dropTable("cliente");
        helper.createTables();
        helper.closeConnection();

        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
    
        ClienteDAO clienteDAO = chosenFactory.getClienteDAO();
        clienteDAO.insertar(new Cliente(1,"Juan Perez", "juan.perez@example.com"));
    }
}
