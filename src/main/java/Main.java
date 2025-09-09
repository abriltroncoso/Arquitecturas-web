
import DAO.ClienteDAO;
import Factory.AbstractFactory;
import Utils.HelperMySQL;

public class Main {
    public static void main(String[] args) throws Exception {
        HelperMySQL helper = new HelperMySQL();
        helper.dropTable("Cliente");
        helper.dropTable("Factura");
        helper.dropTable("Factura_Producto");
        helper.dropTable("Producto");
        helper.createTables();
        helper.closeConnection();

        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
    
    }
}
