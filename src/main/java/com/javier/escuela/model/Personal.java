package com.javier.escuela.model;

import java.sql.Date;
import javax.swing.DefaultComboBoxModel;

public class Personal {
    
    // variables que corresponden a las columnas de Personal en la DB
    private int idPersonal;
    private String numeroIdentificacion;
    private String nombre;
    private String email;
    private String direccion;
    private String celular;
    private Date fechaIngreso;
    private String genero;
    
    // comboModel para implementar al JComboBox de genero
    private String[] generoArray = {"Seleccionar", "Masculino", "Femenino"};
    private DefaultComboBoxModel comboModel = new DefaultComboBoxModel(generoArray);

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }
    
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }   

    public DefaultComboBoxModel getComboModel() {
        return comboModel;
    }

    public void setComboModel(DefaultComboBoxModel comboModel) {
        this.comboModel = comboModel;
    }
}
