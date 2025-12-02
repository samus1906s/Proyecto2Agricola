/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entidades;

/**
 *
 * @author samue
 */
public interface iMapper <Entidad,DTO> {
    public DTO ToDto(Entidad ent);
    
    public Entidad ToEntidad(DTO dto);
}
