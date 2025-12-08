/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.ITrabjadorDao;
import Modelo.Administrador;
import Modelo.Trabajador;
import Modelo.TrabajadorCampo;
import Utilidades.ConexionBD;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Reynold
 */
public class TrabajadorDao implements ITrabjadorDao{
     @Override
    public boolean crear(Trabajador t) throws SQLException {
        try {
            String sql = "INSERT INTO trabajadores (cedula, nombre, telefono, correo, puesto, tipo_trabajador, salario)" + "VALUES(?, ?, ?, ?, ?, ?, ?)";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, t.getCedula());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getTelefono());
            ps.setString(4, t.getCorreo());
            ps.setDouble(7, t.getSalario());

            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;

        } catch (SQLException ex) {
            System.out.println("Error en crear(): " + ex);
            return false;
        }
    }

    @Override
    public Trabajador Leer(int id) throws SQLException {
        try {
            String sql = "SELECT * FROM trabajadores WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Trabajador t = null;

            if (rs.next()) {
                String puestoStr = rs.getString("puesto");
                Administrador puesto = puestoStr != null ? Administrador.valueOf(puestoStr) : null;
                
                String tipoStr = rs.getString("tipo_trabajador");
                TrabajadorCampo tipo = tipoStr != null ? TrabajadorCampo.valueOf(tipoStr) : null;
                
                t = new Trabajador(
                    rs.getInt("id"),
                    puesto,
                    tipo,
                    rs.getDouble("salario"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
            }

            rs.close();
            ps.close();
            return t;

        } catch (SQLException ex){
            System.out.println("Error en Leer(): " + ex);
            return null;
        }
    }

    @Override
    public List<Trabajador> lista() throws SQLException {
        List<Trabajador> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM trabajadores";
            
            Connection con = ConexionBD.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String puestoStr = rs.getString("puesto");
                Administrador puesto = puestoStr != null ? Administrador.valueOf(puestoStr) : null;
                
                String tipoStr = rs.getString("tipo_trabajador");
                TrabajadorCampo tipo = tipoStr != null ? TrabajadorCampo.valueOf(tipoStr) : null;
                
                Trabajador t = new Trabajador(
                    rs.getInt("id"),
                    puesto,
                    tipo,
                    rs.getDouble("salario"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );

                lista.add(t);
            }

            rs.close();
            rs.close();

        } catch (SQLException ex){
            System.out.println("Error en lista(): " + ex);
        }

        return lista;
    }

    @Override
    public boolean actualizar(Trabajador t) throws SQLException {
        try {
            String sql = "UPDATE trabajadores SET cedula=?, nombre=?, telefono=?, correo=?, " + "puesto=?, tipo_trabajador=?, salario=? WHERE id=?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, t.getCedula());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getTelefono());
            ps.setString(4, t.getCorreo());
            ps.setDouble(7, t.getSalario());
            ps.setInt(8, t.getId());

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex){
            System.out.println("Error en actualizar(): " + ex);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        try {
            String sql = "DELETE FROM trabajadores WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex){
            System.out.println("Error en eliminar(): " + ex);
            return false;
        }
    }

    @Override
    public List<Trabajador> Buscar(String texto) throws SQLException {
        List<Trabajador> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM trabajadores WHERE cedula LIKE ? OR nombre LIKE ? OR correo LIKE ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, texto + "%");
            ps.setString(2, texto + "%");
            ps.setString(3, texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String puestoStr = rs.getString("puesto");
                Administrador puesto = puestoStr != null ? Administrador.valueOf(puestoStr) : null;
                
                String tipoStr = rs.getString("tipo_trabajador");
                TrabajadorCampo tipo = tipoStr != null ? TrabajadorCampo.valueOf(tipoStr) : null;
                
                Trabajador t = new Trabajador(
                    rs.getInt("id"),
                    puesto,
                    tipo,
                    rs.getDouble("salario"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );

                lista.add(t);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex){
            System.out.println("Error en Buscar(): " + ex);
        }

        return lista;
    }
    
    
     
    public Trabajador buscarPorCedula(String cedula) throws Exception {
        try {
            String sql = "SELECT * FROM trabajadores WHERE cedula = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);

            ResultSet rs = ps.executeQuery();
            Trabajador t = null;

            if (rs.next()){
                String puestoStr = rs.getString("puesto");
                Administrador puesto = puestoStr != null ? Administrador.valueOf(puestoStr) : null;
                
                String tipoStr = rs.getString("tipo_trabajador");
                TrabajadorCampo tipo = tipoStr != null ? TrabajadorCampo.valueOf(tipoStr) : null;
                
                t = new Trabajador(
                    rs.getInt("id"),
                    puesto,
                    tipo,
                    rs.getDouble("salario"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
            }

            rs.close();
            ps.close();
            return t;
        } catch (SQLException ex){
            System.out.println("Error en buscarPorCedula(): " + ex);
            return null;
        }
    }
}
