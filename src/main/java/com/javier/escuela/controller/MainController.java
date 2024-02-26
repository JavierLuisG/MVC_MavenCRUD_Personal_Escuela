package com.javier.escuela.controller;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.dal.dao.implement.PersonalDAOImpl;
import com.javier.escuela.model.Personal;
import com.javier.escuela.view.PersonalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JOptionPane;

public class MainController implements ActionListener {

    private PersonalView view;
    private DatabaseConnection conn;
    private Personal personal;
    private PersonalDAOImpl personalDAOImpl;

    private String identificacion;
    private String nombre;
    private String email;
    private String direccion;
    private String celular;
    private Date fechaIngreso;
    private String genero;

    public MainController(PersonalView view, DatabaseConnection conn) {
        // Se inicializan las instancias con el constructor
        this.view = view;
        this.conn = conn;
        personal = new Personal();
        personalDAOImpl = new PersonalDAOImpl();
        // Se envía el comboModel que se encuentra en personal al comboGenero en la view
        view.comboGenero.setModel(personal.getComboModel());
        // Ejecutar la acción del boton
        view.btnBuscar.addActionListener(this);
        view.btnRegistrar.addActionListener(this);
        view.btnActualizar.addActionListener(this);
        view.btnEliminar.addActionListener(this);
        view.btnLimpiar.addActionListener(this);
    }

    public void start() {
        view.setLocationRelativeTo(null); // Que se localice en el medio de la pantalla al start el programa
        view.setTitle("Gestion del personal");
        view.setResizable(false); // Impedir modificaciones en el tamaño 
        view.cajaID.setVisible(false); // No es necesario la visibilidad de la cajaID
        view.setVisible(true); // Permite la visibilidad de la ventana
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Condición que indica cual boton está siendo ejecutado    
        if (e.getSource() == view.btnRegistrar) {
            switch (validationEnteredData()) { // switch que valida que los datos se hayan ingresado correctamente
                case 1 -> {
                    // IMPORTANTE. para realizar el registro primero se pasan los valores a Personal para que no sean null, no puede ser dentro del switch           
                    personal.setNumeroIdentificacion(identificacion);
                    personal.setNombre(nombre);
                    personal.setEmail(email);
                    personal.setDireccion(direccion);
                    personal.setCelular(celular);
                    personal.setFechaIngreso(fechaIngreso);
                    personal.setGenero(genero);
                    switch (personalDAOImpl.insertPersonal(personal)) { // switch que valida el metodo insertPersonal luego de enviar los valores a las variables de personal
                        case 1 -> {
                            JOptionPane.showMessageDialog(null, "Registro exitoso");
                            toClean();
                            view.cajaBuscar.setText(identificacion);
                        }
                        case 2 ->
                            JOptionPane.showMessageDialog(null, "N° idetificación ya registrado");
                        case 0 ->
                            JOptionPane.showMessageDialog(null, "No se realizó el registro");
                    }
                }
                case 2 ->
                    JOptionPane.showMessageDialog(null, "Fecha de ingreso invalida\nSigue el formato (yyyy-mm-dd)");
                case 3 ->
                    JOptionPane.showMessageDialog(null, "Ingrese todos los datos solicitados");
            }
        }
        if (e.getSource() == view.btnBuscar) {
            String buscar = view.cajaBuscar.getText().trim();
            if (!buscar.isEmpty()) { // if que permite saber si ingresó o no, un valor en la cajaBuscar
                personal.setNumeroIdentificacion(view.cajaBuscar.getText().trim());
                switch (personalDAOImpl.findPersonalByNumIdentification(personal)) {
                    case 1 -> {
                        view.cajaID.setText(String.valueOf(personal.getIdPersonal()));
                        view.cajaIdentificacion.setText(personal.getNumeroIdentificacion());
                        view.cajaNombre.setText(personal.getNombre());
                        view.cajaEmail.setText(personal.getEmail());
                        view.cajaDireccion.setText(personal.getDireccion());
                        view.cajaCelular.setText(personal.getCelular());
                        view.cajaIngreso.setText(String.valueOf(personal.getFechaIngreso()));
                        view.comboGenero.setSelectedItem(personal.getGenero());
                        JOptionPane.showMessageDialog(null, "Consulta exitosa");
                    }
                    case 0 -> {
                        JOptionPane.showMessageDialog(null, "N° identificación no registrado");
                        toClean();
                        view.cajaIdentificacion.setText(buscar);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un N° identificación");
                toClean();
            }
        }
        if (e.getSource() == view.btnActualizar) {
            // Practicamente como el btnRegistrar
            switch (validationEnteredData()) {
                case 1 -> {
                    personal.setNumeroIdentificacion(identificacion);
                    personal.setNombre(nombre);
                    personal.setEmail(email);
                    personal.setDireccion(direccion);
                    personal.setCelular(celular);
                    personal.setFechaIngreso(fechaIngreso);
                    personal.setGenero(genero);
                    switch (personalDAOImpl.updatePersonal(personal)) {
                        case 1 -> {
                            JOptionPane.showMessageDialog(null, "Actualización exitosa");
                            toClean();
                            // Mostrar los datos que han quedado después de la actualización
                            view.cajaIdentificacion.setText(identificacion);
                            view.cajaNombre.setText(nombre);
                            view.cajaEmail.setText(email);
                            view.cajaDireccion.setText(direccion);
                            view.cajaCelular.setText(celular);
                            view.cajaIngreso.setText(String.valueOf(fechaIngreso));
                            view.comboGenero.setSelectedItem(genero);
                        }
                        case 2 ->
                            JOptionPane.showMessageDialog(null, "N° idetificación ya registrado");
                        case 3 ->
                            JOptionPane.showMessageDialog(null, "Ingrese correctamente los datos solicitados");
                        case 0 ->
                            JOptionPane.showMessageDialog(null, "Actualización invalida");
                    }
                }
                case 2 ->
                    JOptionPane.showMessageDialog(null, "Fecha de ingreso invalida\nSigue el formato (yyyy-mm-dd)");
                case 3 ->
                    JOptionPane.showMessageDialog(null, "Ingrese todos los datos solicitados");
            }
        }
        if (e.getSource() == view.btnEliminar) {
            personal.setIdPersonal(Integer.parseInt(view.cajaID.getText()));
            switch (personalDAOImpl.deletePersonal(personal)) {
                case 1 -> {
                    JOptionPane.showMessageDialog(null, "Registro eliminado");
                    toClean();
                }
                case 0 ->
                    JOptionPane.showMessageDialog(null, "No se eliminó registro");
            }
        }
        if (e.getSource() == view.btnLimpiar) {
            toClean();
        }
    }

    /**
     * Método para limpiar las JTextField
     */
    public void toClean() {
        view.cajaBuscar.setText("");
        view.cajaID.setText("");
        view.cajaIdentificacion.setText("");
        view.cajaNombre.setText("");
        view.cajaEmail.setText("");
        view.cajaDireccion.setText("");
        view.cajaCelular.setText("");
        view.cajaIngreso.setText("");
        view.comboGenero.setSelectedIndex(0);
    }

    /**
     * IllegalArgumentException salta cuando por ejemplo, doy registrar sin
     * tener los datos ingresados, esta excepción no se se puede capturar en
     * PersonalDAOImpl por tal razón,
     *
     * @return int para verificar que los datos sean ingresados correctamente
     */
    public int validationEnteredData() {
        // Pasar los valores de las cajas a las variables para verificar si cumple con todos los datos, la validación se realiza con el siguiente if
        identificacion = view.cajaIdentificacion.getText().trim();
        nombre = view.cajaNombre.getText().trim();
        email = view.cajaEmail.getText().trim();
        direccion = view.cajaDireccion.getText().trim();
        celular = view.cajaCelular.getText().trim();
        // El try verifica que la cajaIngreso contenga un formato Date valido
        try {
            fechaIngreso = Date.valueOf(view.cajaIngreso.getText().trim());
        } catch (IllegalArgumentException ex) {
            fechaIngreso = null;
        }
        genero = String.valueOf(view.comboGenero.getSelectedItem());
        // Este if valida que todas las cajas contengan un valor, es decir que todo está bien
        if (!identificacion.isEmpty() && !nombre.isEmpty() && !email.isEmpty()
                && !direccion.isEmpty() && !celular.isEmpty() && fechaIngreso != null
                && !genero.equals(personal.getComboModel().getElementAt(0))) {
            return 1;
            // Este else if para validar solamente la FechaIngreso cuando tiene un error y los demás datos están bien
        } else if (!identificacion.isEmpty() && !nombre.isEmpty() && !email.isEmpty()
                && !direccion.isEmpty() && !celular.isEmpty() && fechaIngreso == null
                && !genero.equals(personal.getComboModel().getElementAt(0))) {
            return 2;
        } else {
            // Si retorna 3 es porque faltan datos
            return 3;
        }
    }
}
