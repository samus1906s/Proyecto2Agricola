/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Interfaces.IUsuarioDao;
import Modelo.Usuario;
import Utilidades.ConexionBD;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Student
 */
public class UsuarioDao implements IUsuarioDao{
    
    @Override
    public boolean crear(Usuario u) throws Exception {
        try {
            String sql = "INSERT INTO usuarios (cedula, nombre, telefono, correo, usuario, contraseña, rol) " + "VALUES(?, ?, ?, ?, ?, ?, ?)";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getCedula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getUser());
            ps.setString(6, u.getContraseña());
            ps.setString(7, "USER");

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex){
            System.out.println("Error en crear(): " + ex);
            return false;
        }
    }

    @Override
    public Usuario Leer(int id) throws Exception {
        try {
            String sql = "SELECT * FROM usuarios WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Usuario u = null;

            if (rs.next()) {
                u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("contraseña"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
            }

            rs.close();
            ps.close();
            return u;

        } catch (SQLException ex){
            System.out.println("Error en Leer(): " + ex);
            return null;
        }
    }

    @Override
    public List<Usuario> lista() throws Exception {
        List<Usuario> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM usuarios";
            Connection con = ConexionBD.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("contraseña"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );

                lista.add(u);
            }

            rs.close();
            st.close();

        } catch (SQLException ex){
            System.out.println("Error en lista(): " + ex);
        }
        return lista;
    }

    @Override
    public boolean actualizar(Usuario u) throws Exception {
        try {
            String sql = "UPDATE usuarios SET cedula=?, nombre=?, telefono=?, correo=?, " + "usuario=?, contraseña=?, rol=? WHERE id=?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getCedula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getUser());
            ps.setString(6, u.getContraseña());
            ps.setString(7, "USER");
            ps.setInt(8, u.getId());

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex){
            System.out.println("Error en actualizar(): " + ex);
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        try {
            String sql = "DELETE FROM usuarios WHERE id = ?";

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
    public List<Usuario> Buscar(String texto) throws Exception {
        List<Usuario> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM usuarios WHERE cedula LIKE ? OR nombre LIKE ? OR usuario LIKE ? OR correo LIKE ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, texto + "%");
            ps.setString(2, texto + "%");
            ps.setString(3, texto + "%");
            ps.setString(4, texto + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("contraseña"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
                lista.add(u);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex){
            System.out.println("Error en Buscar(): " + ex);
        }

        return lista;
    } 
}
