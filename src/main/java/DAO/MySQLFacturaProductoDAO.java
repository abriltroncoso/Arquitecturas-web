package DAO;


import entity.Cliente;
import entity.FacturaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MySQLFacturaProductoDAO implements FacturaProductoDAO {
    private Connection conexion;
    public MySQLFacturaProductoDAO(Connection conexion) {
        this.conexion = conexion;}

   @Override
    public void insertar(FacturaProducto fp){
        String sql= "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
       try (PreparedStatement ps = conexion.prepareStatement(sql)) {
           ps.setInt(1, fp.getIdFactura());
           ps.setInt(2, fp.getIdProducto());
           ps.setInt(3, fp.getCantidad());
           ps.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException("Error al insertar en factura_producto", e);
       }

   }
   @Override
    public  void actualizar(FacturaProducto fp){
       String sql = "UPDATE factura_producto SET cantidad = ? WHERE idFactura = ? AND idProducto = ?";
       try (PreparedStatement ps = conexion.prepareStatement(sql)) {
           ps.setInt(1, fp.getCantidad());
           ps.setInt(2, fp.getIdFactura());
           ps.setInt(3, fp.getIdProducto());
           ps.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException("Error al modificar en factura_producto", e);
       }
   }
   @Override
    public  void eliminar(FacturaProducto fp){
        String sql = "DELETE FROM factura_producto WHERE idFactura = ?";
        try(PreparedStatement ps= conexion.prepareStatement(sql)) {
            ps.setInt(1, fp.getIdFactura());
            ps.setInt(2, fp.getIdProducto());
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar en factura_producto", e);
        }
   }
   @Override
   public FacturaProducto obtenerPorId(int idFactura, int idProducto){
       String sql = "SELECT * FROM factura_producto WHERE idFactura = ? AND idProducto = ?";
       try (PreparedStatement ps = conexion.prepareStatement(sql)) {
           ps.setInt(1, idFactura);
           ps.setInt(2, idProducto);
           var rs = ps.executeQuery();
           if (rs.next()) {
               return new FacturaProducto(
                       rs.getInt("idFactura"),
                       rs.getInt("idProducto"),
                       rs.getInt("cantidad")
               );
           }
       } catch (SQLException e) {
           throw new RuntimeException("Error al obtener factura_producto", e);
       }
       return null;
   }
   @Override
    public List<FacturaProducto> obtenerTodos(){
        String sql = "SELECT * FROM factura_producto";
       List<FacturaProducto> lista = new ArrayList<>();
       try (Statement st = conexion.createStatement();
            var rs = st.executeQuery(sql)) {
           while (rs.next()) {
               lista.add(new FacturaProducto(
                       rs.getInt("idFactura"),
                       rs.getInt("idProducto"),
                       rs.getInt("cantidad")
               ));
           }
       } catch (SQLException e) {
           throw new RuntimeException("Error al obtener todos los factura_producto", e);
       }
       return lista;
   }


}
