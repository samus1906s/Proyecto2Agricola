/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.ICultivoDAO;
import Modelo.Cultivo;
import Modelo.EstadoCrecimiento;
import Modelo.TiposCultivo;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valdelomaar
 */
public class CultivoDAO implements ICultivoDAO {
    
    @Override
    public boolean crear(Cultivo c) throws Exception {
        String sql = "INSERT INTO cultivos (nombre, tipo, area_sembrada, estado, fecha_siembra, fecha_cosecha) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, c.getNombre());
        ps.setString(2, c.getTipo().name());
        ps.setDouble(3, c.getAreaSembrada());
        ps.setString(4, c.getEstado().name());
        ps.setDate(5, Date.valueOf(c.getFechaSiembra()));
        ps.setDate(6, Date.valueOf(c.getFechaCosecha()));

        int rows = ps.executeUpdate();

        ps.close(); 
        
        return rows > 0;
    }

    @Override
    public List<Cultivo> lista() throws Exception {
        List<Cultivo> lista = new ArrayList<>();
        String sql = "SELECT * FROM cultivos";

        Connection con = ConexionBD.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Cultivo c = new Cultivo();
            c.setIdCultivo(rs.getInt("id_cultivo"));
            c.setNombre(rs.getString("nombre"));
            c.setTipo(TiposCultivo.valueOf(rs.getString("tipo")));
            c.setAreaSembrada(rs.getDouble("area_sembrada"));
            c.setEstado(EstadoCrecimiento.valueOf(rs.getString("estado")));
            c.setFechaSiembra(rs.getDate("fecha_siembra").toLocalDate());
            c.setFechaCosecha(rs.getDate("fecha_cosecha").toLocalDate());
            lista.add(c);
        }
        rs.close();
        st.close();
        return lista;
    }

    @Override
    public Cultivo Leer(int id) throws Exception {
        String sql = "SELECT * FROM cultivos WHERE id_cultivo = ?";
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Cultivo c = null;
        if (rs.next()) {
            c = new Cultivo();
            c.setIdCultivo(rs.getInt("id_cultivo"));
            c.setNombre(rs.getString("nombre"));
            c.setTipo(TiposCultivo.valueOf(rs.getString("tipo")));
            c.setAreaSembrada(rs.getDouble("area_sembrada"));
            c.setEstado(EstadoCrecimiento.valueOf(rs.getString("estado")));
            c.setFechaSiembra(rs.getDate("fecha_siembra").toLocalDate());
            c.setFechaCosecha(rs.getDate("fecha_cosecha").toLocalDate());
        }
        rs.close();
        ps.close();
        return c;
    }

    @Override
    public boolean actualizar(Cultivo c) throws Exception {
        String sql = "UPDATE cultivos SET nombre=?, tipo=?, area_sembrada=?, estado=?, fecha_siembra=?, fecha_cosecha=? WHERE id_cultivo=?";
        
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, c.getNombre());
        ps.setString(2, c.getTipo().name());
        ps.setDouble(3, c.getAreaSembrada());
        ps.setString(4, c.getEstado().name());
        ps.setDate(5, Date.valueOf(c.getFechaSiembra()));
        ps.setDate(6, Date.valueOf(c.getFechaCosecha()));
        ps.setInt(7, c.getIdCultivo());

        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM cultivos WHERE id_cultivo=?";
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        
        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;
    }

    @Override
    public List<Cultivo> Buscar(String texto) throws Exception {
        String sql = "SELECT * FROM cultivos WHERE nombre LIKE ? OR tipo LIKE ?";
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%" + texto + "%");
        ps.setString(2, "%" + texto + "%");

        ResultSet rs = ps.executeQuery();
        List<Cultivo> lista = new ArrayList<>();

        while (rs.next()) {
            Cultivo c = new Cultivo();
            c.setIdCultivo(rs.getInt("id_cultivo"));
            c.setNombre(rs.getString("nombre"));
            c.setTipo(TiposCultivo.valueOf(rs.getString("tipo")));
            c.setAreaSembrada(rs.getDouble("area_sembrada"));
            c.setEstado(EstadoCrecimiento.valueOf(rs.getString("estado")));
            c.setFechaSiembra(rs.getDate("fecha_siembra").toLocalDate());
            c.setFechaCosecha(rs.getDate("fecha_cosecha").toLocalDate());
            lista.add(c);
        }
        rs.close();
        ps.close();
        return lista;
    }
    
}
