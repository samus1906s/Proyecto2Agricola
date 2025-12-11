/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs; 

import Interfaces.IAlmacenDAO;
import Modelo.Almacen;
import Modelo.EstadoAlmacen;
import Utilidades.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author samue
 */
public class AlmacenDAO implements IAlmacenDAO{

    @Override
    public boolean crear(Almacen a) throws SQLException {
         try {
            String sql = "INSERT INTO almacen (produccionId, cantidadDisponible, fechaIngreso, fechaEgreso, estado) " + "VALUES (?, ?, ?, ?, ?)";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, a.getProduccionId());
            ps.setDouble(2, a.getCantidadDisponible());
            ps.setDate(3, Date.valueOf(a.getFechaIngreso()));

            if (a.getFechaEgreso() != null) {
                ps.setDate(4, Date.valueOf(a.getFechaEgreso()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, a.getEstado().name());

            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (SQLException ex) {
            System.out.println("Error en crear(): " + ex);
            return false;
        }
    }

    @Override
    public Almacen Leer(int id) throws SQLException {
         try {
            String sql = "SELECT * FROM almacen WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Almacen a = null;

            if (rs.next()) {
                a = new Almacen();
                a.setId(rs.getInt("id"));
                a.setProduccionId(rs.getInt("produccionId"));
                a.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                a.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());

                Date egreso = rs.getDate("fechaEgreso");
                a.setFechaEgreso(egreso != null ? egreso.toLocalDate() : null);

                a.setEstado(EstadoAlmacen.valueOf(rs.getString("estado")));
            }

            rs.close();
            ps.close();
            return a;

        } catch (SQLException ex) {
            System.out.println("Error en Leer(): " + ex);
            return null;
        }
    }

    @Override
    public List<Almacen> lista() throws SQLException {
        List<Almacen> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM almacen";
            Connection con = ConexionBD.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Almacen a = new Almacen();
                a.setId(rs.getInt("id"));
                a.setProduccionId(rs.getInt("produccionId"));
                a.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                a.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());

                Date egreso = rs.getDate("fechaEgreso");
                a.setFechaEgreso(egreso != null ? egreso.toLocalDate() : null);

                a.setEstado(EstadoAlmacen.valueOf(rs.getString("estado")));

                lista.add(a);
            }

            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println("Error en lista(): " + ex);
        }

        return lista;
    }

    @Override
    public boolean actualizar(Almacen a) throws SQLException {
        try {
            String sql = "UPDATE almacen SET produccionId=?, cantidadDisponible=?, fechaIngreso=?, fechaEgreso=?, estado=? " + "WHERE id=?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, a.getProduccionId());
            ps.setDouble(2, a.getCantidadDisponible());
            ps.setDate(3, Date.valueOf(a.getFechaIngreso()));

            if (a.getFechaEgreso() != null) {
                ps.setDate(4, Date.valueOf(a.getFechaEgreso()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, a.getEstado().name());
            ps.setInt(6, a.getId());

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
        try {
            String sql = "DELETE FROM almacen WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex) {
            System.out.println("Error en eliminar(): " + ex);
            return false;
        }
    }

    @Override
    public List<Almacen> Buscar(String texto) throws SQLException {
 
        List<Almacen> lista = new ArrayList<>();

        try {
            String sql = "SELECT id,produccionId,cantidadDisponible, fechaIngreso, fechaEgreso, estado FROM almacen WHERE id=?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, texto + "%");
            ps.setString(2, texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Almacen a = new Almacen();
                a.setId(rs.getInt("id"));
                a.setProduccionId(rs.getInt("produccionId"));
                a.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                a.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());

                Date egreso = rs.getDate("fechaEgreso");
                a.setFechaEgreso(egreso != null ? egreso.toLocalDate() : null);

                a.setEstado(EstadoAlmacen.valueOf(rs.getString("estado")));

                lista.add(a);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error en Buscar(): " + ex);
        }

        return lista;
    }
    
    public List<Almacen> listaPorFechaIngreso(LocalDate fecha) throws SQLException {
        List<Almacen> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM almacen WHERE fechaIngreso = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(fecha));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Almacen a = new Almacen();
                a.setId(rs.getInt("id"));
                a.setProduccionId(rs.getInt("produccionId"));
                a.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                a.setFechaIngreso(rs.getDate("fechaIngreso").toLocalDate());

                Date egreso = rs.getDate("fechaEgreso");
                a.setFechaEgreso(egreso != null ? egreso.toLocalDate() : null);

                a.setEstado(EstadoAlmacen.valueOf(rs.getString("estado")));

                lista.add(a);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error en listaPorFechaIngreso(): " + ex);
        }
        return lista;
    }
}
    
    

