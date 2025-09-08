package DAO;

import entity.Factura;

public interface FacturaDAO {
    void insertar(Factura f);
    void actualizar(Factura f);
    void eliminar(Integer idFactura);
    Factura buscarFactura(Integer idFactura);
}
