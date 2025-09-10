package DAO;

import entity.Cliente;

import java.util.List;

public interface ClienteDAO {
    public void insertar(Cliente c);
    public void actualizar(Cliente c);
    public void eliminar(Integer idCliente);
    public List<Cliente> obtenerTodos();
    public Cliente obtenerPorId(int idCliente);
    public List<Cliente> obtenerClientePorFacturacion();
}
