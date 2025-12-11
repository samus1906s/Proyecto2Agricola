/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.ITrabajadorDAO;
import Modelo.TipoPuesto;
import Modelo.Trabajador;
import Modelo.TrabajadorCampo;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Reynold
 */
public class TrabajadorDAO implements ITrabajadorDAO {
    
    @Override
    public boolean crear(Trabajador t) throws Exception {
        String sql = "INSERT INTO trabajador (cedula, nombre, telefono, correo, puesto, tipo_trabajador, salario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, t.getCedula());
        ps.setString(2, t.getNombre());
        ps.setString(3, t.getTelefono());
        ps.setString(4, t.getCorreo());
        ps.setString(5, t.getPuesto().name());
        ps.setString(6, t.getTipoTrabajador().name());
        ps.setDouble(7, t.getSalario());

        int rows = ps.executeUpdate();

        if (rows > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setIdTrabajador(rs.getInt(1));
            }
            rs.close();
        }

        ps.close();
        return rows > 0;
    }

    @Override
    public Trabajador Leer(int id) throws Exception {
        String sql = "SELECT * FROM trabajador WHERE id_trabajador = ?";
    
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Trabajador t = null;
        if (rs.next()) {
            t = new Trabajador();
            t.setIdTrabajador(rs.getInt("id_trabajador"));
            t.setCedula(rs.getString("cedula"));
            t.setNombre(rs.getString("nombre"));

            String telefonoBD = rs.getString("telefono");
            String telefonoLimpio = telefonoBD != null ? telefonoBD.replaceAll("[^0-9-]", "") : "";
            t.setTelefonoSinValidar(telefonoLimpio);
            
            t.setCorreoSinValidar(rs.getString("correo"));
            t.setPuesto(TipoPuesto.valueOf(rs.getString("puesto")));
            t.setTipoTrabajador(TrabajadorCampo.valueOf(rs.getString("tipo_trabajador")));
            t.setSalario(rs.getDouble("salario"));
        }
        
        rs.close();
        ps.close();
        return t;
    }

    @Override
    public List<Trabajador> lista() throws Exception {
        List<Trabajador> lista = new ArrayList<>();
        String sql = "SELECT * FROM trabajador";
    
        Connection con = ConexionBD.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {

            Trabajador t = new Trabajador();
            
            t.setIdTrabajador(rs.getInt("id_trabajador"));
            t.setCedula(rs.getString("cedula"));
            t.setNombre(rs.getString("nombre"));
 
            String telefonoBD = rs.getString("telefono");
            String telefonoLimpio = telefonoBD != null ? telefonoBD.replaceAll("[^0-9-]", "") : "";
            t.setTelefonoSinValidar(telefonoLimpio);

            t.setCorreoSinValidar(rs.getString("correo"));
            
            t.setPuesto(TipoPuesto.valueOf(rs.getString("puesto")));
            t.setTipoTrabajador(TrabajadorCampo.valueOf(rs.getString("tipo_trabajador")));
            t.setSalario(rs.getDouble("salario"));
            
            lista.add(t);
        }
        
        rs.close();
        st.close();
        return lista;
    }

    @Override
    public boolean actualizar(Trabajador t) throws Exception {
        String sql = "UPDATE trabajador SET cedula=?, nombre=?, telefono=?, correo=?, puesto=?, tipo_trabajador=?, salario=? WHERE id_trabajador=?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, t.getCedula());
        ps.setString(2, t.getNombre());
        ps.setString(3, t.getTelefono());
        ps.setString(4, t.getCorreo());
        ps.setString(5, t.getPuesto().name());
        ps.setString(6, t.getTipoTrabajador().name());
        ps.setDouble(7, t.getSalario());
        ps.setInt(8, t.getIdTrabajador());

        int rows = ps.executeUpdate();
        ps.close();

        return rows > 0;
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM trabajador WHERE id_trabajador = ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();
        ps.close();

        return rows > 0;
    }

    @Override
    public List<Trabajador> Buscar(String texto) throws Exception {
        List<Trabajador> lista = new ArrayList<>();
        String sql = "SELECT * FROM produccion WHERE id = ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        String busqueda = "%" + texto + "%";
        ps.setString(1, busqueda);
        ps.setString(2, busqueda);
        ps.setString(3, busqueda);
        ps.setString(4, busqueda);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Trabajador t = new Trabajador();
            t.setIdTrabajador(rs.getInt("id_trabajador"));
            t.setCedula(rs.getString("cedula"));
            t.setNombre(rs.getString("nombre"));
            
            String telefonoBD = rs.getString("telefono");
            String telefonoLimpio = telefonoBD != null ? telefonoBD.replaceAll("[^0-9-]", "") : "";
            t.setTelefonoSinValidar(telefonoLimpio);
            
            t.setCorreoSinValidar(rs.getString("correo"));
            
            String puestoStr = rs.getString("puesto");
            t.setPuesto(puestoStr != null ? TipoPuesto.valueOf(puestoStr) : null);
            
            String tipoStr = rs.getString("tipo_trabajador");
            t.setTipoTrabajador(tipoStr != null ? TrabajadorCampo.valueOf(tipoStr) : null);
            
            t.setSalario(rs.getDouble("salario"));
            
            lista.add(t);
        }

        rs.close();
        ps.close();

        return lista;
    }
    
    public Trabajador buscarPorCedula(String cedula) throws Exception {
        String sql = "SELECT * FROM trabajador WHERE cedula = ?";

        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, cedula);

        ResultSet rs = ps.executeQuery();
        Trabajador t = null;

        if (rs.next()) {
            t = new Trabajador();
            t.setIdTrabajador(rs.getInt("id_trabajador"));
            t.setCedula(rs.getString("cedula"));
            t.setNombre(rs.getString("nombre"));
            
            String telefonoBD = rs.getString("telefono");
            String telefonoLimpio = telefonoBD != null ? telefonoBD.replaceAll("[^0-9-]", "") : "";
            t.setTelefonoSinValidar(telefonoLimpio);
            
            t.setCorreoSinValidar(rs.getString("correo"));
            
            String puestoStr = rs.getString("puesto");
            t.setPuesto(puestoStr != null ? TipoPuesto.valueOf(puestoStr) : null);
            
            String tipoStr = rs.getString("tipo_trabajador");
            t.setTipoTrabajador(tipoStr != null ? TrabajadorCampo.valueOf(tipoStr) : null);
            
            t.setSalario(rs.getDouble("salario"));
        }

        rs.close();
        ps.close();
        return t;
    }
}