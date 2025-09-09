package DAO;

import entity.FacturaProducto;
import java.util.List;

public interface FacturaProductoDAO{
    public void insertar(FacturaProducto Fp);
    public void actualizar(FacturaProducto Fp);
    public void eliminar(FacturaProducto idFp);
    public FacturaProducto obtenerPorId(int idFactura, int idProducto);
    public List<FacturaProducto> obtenerTodos();
}