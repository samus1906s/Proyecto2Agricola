/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.IUsuarioDAO;
import Mappers.UsuarioMapper;
import Modelo.Usuario;
import Utilidades.ConexionBD;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Reynold
 */
public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public boolean crear(Usuario u) throws SQLException {
        try {
            String sql = "INSERT INTO usuarios (nombre_completo, usuario, email, rol, contrasena, estado) " +"VALUES (?, ?, ?, ?, ?, ?)";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getRol().name());

            String hashContrasena = BCrypt.hashpw(u.getContrasena(), BCrypt.gensalt());
            ps.setString(5, hashContrasena);
            
            ps.setString(6, u.getEstado().name());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    u.setId(rs.getInt(1));
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

    @Override
    public Usuario Leer(int id) throws SQLException {
        try {
            String sql = "SELECT * FROM usuarios WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Usuario u = null;

            if (rs.next()) {
                u = UsuarioMapper.resultadoSetDelModelo(rs);
            }

            rs.close();
            ps.close();

            return u;

        } catch (SQLException ex) {
            System.out.println("Error en Leer(): " + ex);
            return null;
        }
    }

    @Override
    public List<Usuario> lista() throws SQLException {
        List<Usuario> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM usuarios ORDER BY id DESC";

            Connection con = ConexionBD.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lista.add(UsuarioMapper.resultadoSetDelModelo(rs));
            }

            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println("Error en lista(): " + ex);
        }

        return lista;
    }

    @Override
    public boolean actualizar(Usuario u) throws SQLException {
        try {
            String sql = "UPDATE usuarios SET nombre_completo=?, usuario=?, email=?, rol=?, estado=? WHERE id=?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getRol().name());
            ps.setString(5, u.getEstado().name());
            ps.setInt(6, u.getId());

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex) {
            System.out.println("Error en actualizar(): " + ex);
            return false;
        }
    }
    
    public boolean actualizarConContrasena(Usuario u, String nuevaContrasena) throws SQLException {
        try {
            String sql = "UPDATE usuarios SET nombre_completo=?, usuario=?, email=?, rol=?, contrasena=?, estado=? WHERE id=?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getNombreCompleto());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getRol().name());

            String hashContrasena = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());
            ps.setString(5, hashContrasena);
            
            ps.setString(6, u.getEstado().name());
            ps.setInt(7, u.getId());

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex) {
            System.out.println("Error en actualizarConContrasena(): " + ex);
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

            String sqlDelete = "DELETE FROM usuarios WHERE id = ?";
            ps = con.prepareStatement(sqlDelete);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ps.close();
                String sqlCount = "SELECT COUNT(*) FROM usuarios";
                ps = con.prepareStatement(sqlCount);
                rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) == 0) {
                    ps.close();
                    String sqlReset = "ALTER TABLE usuarios AUTO_INCREMENT = 1";
                    ps = con.prepareStatement(sqlReset);
                    ps.executeUpdate();
                    System.out.println("Tabla usuarios vacía. AUTO_INCREMENT reiniciado a 1");
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
    public Usuario autenticar(String usuario, String contrasena) throws SQLException {
        try {
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND estado = 'ACTIVO'";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();
            Usuario u = null;

            if (rs.next()) {
                String hashAlmacenado = rs.getString("contrasena");

                if (BCrypt.checkpw(contrasena, hashAlmacenado)) {
                    u = UsuarioMapper.resultadoSetDelModelo(rs);
                }
            }

            rs.close();
            ps.close();

            return u;

        } catch (SQLException ex) {
            System.out.println("Error en autenticar(): " + ex);
            return null;
        }
    }

    @Override
    public boolean existeUsuario(String usuario) throws SQLException {
        try {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();
            boolean existe = false;

            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }

            rs.close();
            ps.close();

            return existe;

        } catch (SQLException ex) {
            System.out.println("Error en existeUsuario(): " + ex);
            return false;
        }
    }

    @Override
    public boolean registrarAcceso(int usuarioId) throws SQLException {
        try {
            String sql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuarioId);

            int rows = ps.executeUpdate();
            ps.close();

            return rows > 0;

        } catch (SQLException ex) {
            System.out.println("Error en registrarAcceso(): " + ex);
            return false;
        }
    }

    @Override
    public List<Usuario> Buscar(String texto) throws SQLException {
        List<Usuario> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM usuario WHERE id = ?";

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            String busqueda = "%" + texto + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(UsuarioMapper.resultadoSetDelModelo(rs));
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error en Buscar(): " + ex);
        }

        return lista;
    }

    @Override
    public boolean existenUsuarios() throws SQLException {
        try {
        String sql = "SELECT COUNT(*) FROM usuarios";
        
        Connection con = ConexionBD.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        boolean hayUsuarios = false;
        
        if (rs.next()) {
            hayUsuarios = rs.getInt(1) > 0;
        }
        
        rs.close();
        ps.close();
        
        return hayUsuarios;
        
    } catch (SQLException ex) {
        System.out.println("Error en existenUsuarios(): " + ex);
        throw ex;
    }
    }
    
}
