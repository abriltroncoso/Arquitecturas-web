package DAO;

import entity.Producto;
import java.util.List;

public interface ProductoDAO    {
    public void insertar(Producto producto);
    public void actualizar(Producto producto);
    public void eliminar(Producto producto);
    public List<Producto> obtenerTodos();
    public Producto obtenerPorId(long id);
    public List<Producto> obtenerPorNombre(String nombre);
    public List<Producto> obtenerPorCategoria(String categoria);
    public List<Producto> obtenerPorPrecio(float precio);
}