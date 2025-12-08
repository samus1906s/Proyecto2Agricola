/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import DAOs.UsuarioDao;
import DTOs.UsuarioDto;
import Interfaces.IUsuarioDao;
import Mappers.UsuarioMapper;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Student
 */
public class ServicioUsuario {
    private IUsuarioDao udao;
    private UsuarioMapper umapper;

    public ServicioUsuario(IUsuarioDao udao, UsuarioMapper umapper) {
        this.udao = udao;
        this.umapper = umapper;
    }
    
    public boolean registrar(UsuarioDto dto) throws Exception{
        UsuarioDao usuarioDao = (UsuarioDao) udao;
        
        if (usuarioDao.existeUsuario(dto.getUsuario())){
            throw new Exception("El nombre de usuario ya existe");
        }
        
        if (usuarioDao.buscarPorCedula(dto.getCedula()) !=null){
            throw new Exception("La cédula ya está registrada");
        }
        
        Usuario usuario = umapper.ToEntidad(dto);
        return udao.crear(usuario);
    }

    public boolean actualizar(UsuarioDto dto) throws Exception{
        Usuario usuario = umapper.ToEntidad(dto);
        return udao.actualizar(usuario);
    }

    public boolean eliminar(int id) throws Exception{
        return udao.eliminar(id);
    }

    public UsuarioDto obtenerPorId(int id) throws Exception{
        Usuario u = udao.Leer(id);
        return u != null ? umapper.ToDto(u) : null;
    }

    
    public List<UsuarioDto> listar() throws Exception{
        List<Usuario> lista = udao.lista();
        List<UsuarioDto> resultado = new ArrayList<>();

        for (Usuario u : lista){
            resultado.add(umapper.ToDto(u));
        }
        return resultado;
    }

    public List<UsuarioDto> buscar(String texto) throws Exception{
        List<Usuario> lista = udao.Buscar(texto);
        List<UsuarioDto> resultado = new ArrayList<>();
        
        for (Usuario u : lista){
            resultado.add(umapper.ToDto(u));
        }
        return resultado;
    }

    public UsuarioDto autenticar(String usuario, String contraseña) throws Exception{
        UsuarioDao usuarioDao = (UsuarioDao) udao;
        Usuario u = usuarioDao.autenticar(usuario, contraseña);
        return u != null ? umapper.ToDto(u) : null;
    }

    public UsuarioDto buscarPorUsuario(String usuario) throws Exception{
        UsuarioDao usuarioDao = (UsuarioDao) udao;
        Usuario u = usuarioDao.buscarPorUsuario(usuario);
        return u != null ? umapper.ToDto(u) : null;
    }

    public UsuarioDto buscarPorCedula(String cedula) throws Exception{
        UsuarioDao usuarioDao = (UsuarioDao) udao;
        Usuario u = usuarioDao.buscarPorCedula(cedula);
        return u != null ? umapper.ToDto(u) : null;
    }

    
    public boolean existeUsuario(String usuario) throws Exception{
        UsuarioDao usuarioDao = (UsuarioDao) udao;
        return usuarioDao.existeUsuario(usuario);
    }

    public boolean cambiarContraseña(int id, String contraseñaActual, String contraseñaNueva) throws Exception{
        Usuario usuario = udao.Leer(id);
        
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        
        if (!usuario.getContraseña().equals(contraseñaActual)){
            throw new Exception("La contraseña actual es incorrecta");
        }
        
        usuario.setContraseña(contraseñaNueva);
        return udao.actualizar(usuario);
    }
}
