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
    public boolean crear(Produccion p) throws SQLException {
        try {
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
            
        } catch (SQLException ex) {
            System.out.println("Error en crear(): " + ex);
            return false;
        }
    }

    public int crearYRetornarId(Produccion p) throws SQLException {
        try {
            String sql = "INSERT INTO produccion (cultivo_id, fecha, cantidad_recolectada, calidad_producto, destino) "  + "VALUES (?, ?, ?, ?, ?)";

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
            
        } catch (SQLException ex) {
            System.out.println("Error en crearYRetornarId(): " + ex);
            return -1;
        }
    }

    public boolean existeProduccionDuplicada(int cultivoId, LocalDate fecha, BigDecimal cantidad) throws SQLException {
        try {
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
            
        } catch (SQLException ex) {
            System.out.println("Error en existeProduccionDuplicada(): " + ex);
            return false;
        }
    }

    public Produccion obtenerUltima() throws SQLException {
        try {
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
            
        } catch (SQLException ex) {
            System.out.println("Error en obtenerUltima(): " + ex);
            return null;
        }
    }

    @Override
    public Produccion Leer(int id) throws SQLException {
        try {
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
            
        } catch (SQLException ex) {
            System.out.println("Error en Leer(): " + ex);
            return null;
        }
    }

    @Override
    public List<Produccion> lista() throws SQLException {
        List<Produccion> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM produccion ORDER BY fecha DESC";

            Connection con = ConexionBD.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
            }

            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            System.out.println("Error en lista(): " + ex);
        }

        return lista;
    }

    @Override
    public boolean actualizar(Produccion p) throws SQLException {
        try {
            String sql = "UPDATE produccion SET cultivo_id=?, fecha=?, cantidad_recolectada=?, " + "calidad_producto=?, destino=? WHERE id=?";

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
            
        } catch (SQLException ex) {
            System.out.println("Error en actualizar(): " + ex);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
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
                    System.out.println("Tabla produccion vacía. AUTO_INCREMENT reiniciado a 1");
                }
                
                con.commit(); 
                return true;
            }
            
            con.rollback(); 
            return false;
            
        } catch (SQLException ex) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error en rollback: " + rollbackEx);
                }
            }
            System.out.println("Error en eliminar(): " + ex);
            return false;
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error cerrando recursos: " + ex);
            }
        }
    }

    @Override
    public List<Produccion> listaDeCultivosId(int cultivoId) throws SQLException {
        List<Produccion> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM produccion WHERE cultivo_id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cultivoId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
            }

            rs.close();
            ps.close();
            
        } catch (SQLException ex) {
            System.out.println("Error en listaDeCultivosId(): " + ex);
        }

        return lista;
    }

    @Override
    public List<Produccion> listaDeFechas(LocalDate fecha) throws SQLException {
        List<Produccion> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM produccion WHERE fecha = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(fecha));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
            }

            rs.close();
            ps.close();
            
        } catch (SQLException ex) {
            System.out.println("Error en listaDeFechas(): " + ex);
        }

        return lista;
    }

    @Override
    public List<Produccion> Buscar(String texto) throws SQLException {
        List<Produccion> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM produccion WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(ProduccionMapper.resultadoSetDelModelo(rs));
            }

            rs.close();
            ps.close();
            
        } catch (SQLException ex) {
            System.out.println("Error en Buscar(): " + ex);
        }

        return lista;
    }
   
}
