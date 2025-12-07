/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.IProduccionDAO;
import Mappers.ProduccionMapper;
import Modelo.Produccion;
import Utilidades.ConexionBD;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author je110
 */
public class ProduccionDAO implements IProduccionDAO {

    @Override
    public boolean crear(Produccion p) throws Exception {
        String sql = "INSERT INTO produccion (cultivo_id, fecha, cantidad_recolectada, calidad_producto, destino) " + "VALUES (?, ?, ?, ?, ?)";

        Connection con = ConexionBD.getConnection();

        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, p.getIdCultivo());
        ps.setDate(2, Date.valueOf(p.getFecha()));
        ps.setBigDecimal(3, p.getCantidadRecolectada());
        ps.setString(4, p.getCalidadProducto());
        ps.setString(5, p.getDestino());

        int rows = ps.executeUpdate();

        if (rows > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setIdProduccion(rs.getInt(1)); 
            }
            rs.close();
        }
        
        ps.close();
        return rows > 0;
    }

    public int crearYRetornarId(Produccion p) throws Exception {
        String sql = "INSERT INTO produccion (cultivo_id, fecha, cantidad_recolectada, calidad_producto, destino) " +  "VALUES (?, ?, ?, ?, ?)";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, p.getIdCultivo());
        ps.setDate(2, Date.valueOf(p.getFecha()));
        ps.setBigDecimal(3, p.getCantidadRecolectada());
        ps.setString(4, p.getCalidadProducto());
        ps.setString(5, p.getDestino());

        int rows = ps.executeUpdate();
        
        if (rows > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                rs.close();
                ps.close();
                return idGenerado; 
            }
            rs.close();
        }
        
        ps.close();
        return -1; 
    }

    public boolean existeProduccionDuplicada(int cultivoId, LocalDate fecha, BigDecimal cantidad) throws Exception {
        String sql = "SELECT COUNT(*) FROM produccion " + "WHERE cultivo_id = ? AND fecha = ? AND cantidad_recolectada = ?";
        
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setInt(1, cultivoId);
        ps.setDate(2, Date.valueOf(fecha));
        ps.setBigDecimal(3, cantidad);
        
        ResultSet rs = ps.executeQuery();
        boolean existe = false;
        
        if (rs.next()) {
            existe = rs.getInt(1) > 0; 
        }
        
        rs.close();
        ps.close();
        
        return existe;
    }

    public Produccion obtenerUltima() throws Exception {
        String sql = "SELECT * FROM produccion ORDER BY id DESC LIMIT 1";
        
        Connection con = ConexionBD.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        Produccion p = null;
        if (rs.next()) {
            p = ProduccionMapper.resultadoSetDelModelo(rs);
        }
        
        rs.close();
        st.close();
        
        return p;
    }

    @Override
    public Produccion Leer(int id) throws Exception {
        String sql = "SELECT * FROM produccion WHERE id = ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Produccion p = null;

        if (rs.next()) {
            p = ProduccionMapper.resultadoSetDelModelo(rs);
        }

        rs.close();
        ps.close();

        return p;
    }

    @Override
    public List<Produccion> lista() throws Exception {
        String sql = "SELECT * FROM produccion ORDER BY fecha DESC";

        Connection con = ConexionBD.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Produccion> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
        }

        rs.close();
        st.close();

        return lista;
    }

    @Override
    public boolean actualizar(Produccion p) throws Exception {
        String sql = "UPDATE produccion SET cultivo_id=?, fecha=?, cantidad_recolectada=?, " + 
                     "calidad_producto=?, destino=? WHERE id=?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, p.getIdCultivo());
        ps.setDate(2, Date.valueOf(p.getFecha()));
        ps.setBigDecimal(3, p.getCantidadRecolectada());
        ps.setString(4, p.getCalidadProducto());
        ps.setString(5, p.getDestino());
        ps.setInt(6, p.getIdProduccion());

        int rows = ps.executeUpdate();
        ps.close();

        return rows > 0;
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConexionBD.getConnection();
            con.setAutoCommit(false); 

            String sqlDelete = "DELETE FROM produccion WHERE id = ?";
            ps = con.prepareStatement(sqlDelete);
            ps.setInt(1, id);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {

                ps.close();
                String sqlCount = "SELECT COUNT(*) FROM produccion";
                ps = con.prepareStatement(sqlCount);
                rs = ps.executeQuery();
                
                if (rs.next() && rs.getInt(1) == 0) {

                    ps.close();
                    String sqlReset = "ALTER TABLE produccion AUTO_INCREMENT = 1";
                    ps = con.prepareStatement(sqlReset);
                    ps.executeUpdate();
                    System.out.println("✅ Tabla produccion vacía. AUTO_INCREMENT reiniciado a 1");
                }
                
                con.commit(); 
                return true;
            }
            
            con.rollback(); 
            return false;
            
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("❌ Error al eliminar producción: " + e.getMessage());
            throw new Exception("Error al eliminar producción: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Produccion> listaDeCultivosId(int cultivoId) throws Exception {
        String sql = "SELECT * FROM produccion WHERE cultivo_id = ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, cultivoId);

        ResultSet rs = ps.executeQuery();

        List<Produccion> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
        }

        rs.close();
        ps.close();

        return lista;
    }

    @Override
    public List<Produccion> listaDeFechas(LocalDate fecha) throws Exception {
        String sql = "SELECT * FROM produccion WHERE fecha = ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDate(1, Date.valueOf(fecha));

        ResultSet rs = ps.executeQuery();

        List<Produccion> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
        }

        rs.close();
        ps.close();

        return lista;
    }

    @Override
    public List<Produccion> Buscar(String texto) throws Exception {
        String sql = "SELECT * FROM produccion " + "WHERE calidad_producto LIKE ? OR destino LIKE ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "%" + texto + "%");
        ps.setString(2, "%" + texto + "%");

        ResultSet rs = ps.executeQuery();

        List<Produccion> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
        }

        rs.close();
        ps.close();

        return lista;
    }
   
}
