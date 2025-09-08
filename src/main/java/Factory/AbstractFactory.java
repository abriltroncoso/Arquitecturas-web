package Factory;

import DAO.ProductoDAO;
import DAO.ClienteDAO;
import DAO.FacturaDAO;
import DAO.FacturaProductoDAO;


public abstract class AbstractFactory{
    public static final int MYSQL_JDBC=1;
    public abstract ProductoDAO getProductoDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract FacturaDAO getFacturaDAO();
    public abstract FacturaProductoDAO getFacturaProductoDAO();
    public static AbstractFactory getDAOFactory(int whichFactory){
        switch(whichFactory){
            case MYSQL_JDBC:{
                return MySQLDAOFactory.getInstance();

            }
            default: return null;
        }
    }

}