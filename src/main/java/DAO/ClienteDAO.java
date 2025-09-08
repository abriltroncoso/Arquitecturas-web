package DAO;

import entity.Cliente;

public interface ClienteDAO {
    void create(Cliente c);
    void update(Cliente c);
    void delete(Integer idCliente);
}
