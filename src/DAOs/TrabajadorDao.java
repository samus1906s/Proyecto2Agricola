/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.ITrabjadorDao;
import Modelo.Trabajador;
import Utilidades.ConexionBD;
import java.util.List;

/**
 *
 * @author Student
 */
public class TrabajadorDao implements ITrabjadorDao{

    @Override
    public boolean crear(Trabajador t) throws Exception {
        try {
            String sql = "INSERT INTO trabajador (cedula, nombre, telefono, correo, puesto, tipo_trabajador, salario) VALUES (?,?,?,?,?,?,?);

            Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);   
       
            
        } catch (SQLException ex) {
            System.out.println("Error en crear(): " + ex);
            return false;
        }
    }

    @Override
    public Trabajador Leer(int id) throws Exception {
    }

    @Override
    public List<Trabajador> lista() throws Exception {
    }

    @Override
    public boolean actualizar(Trabajador c) throws Exception {
    }

    @Override
    public boolean eliminar(int id) throws Exception {
    }

    @Override
    public List<Trabajador> Buscar(String texto) throws Exception {
    }
    
}
