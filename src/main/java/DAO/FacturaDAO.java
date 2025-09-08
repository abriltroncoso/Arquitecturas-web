package DAO;

import entity.Factura;

public interface FacturaDAO {
    void create(Factura f);
    void update(Factura f);
    void delete(Integer idFactura);
}
