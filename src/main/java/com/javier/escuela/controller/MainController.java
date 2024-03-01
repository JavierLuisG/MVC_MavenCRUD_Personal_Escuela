package com.javier.escuela.controller;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.dal.dao.implement.PersonalDAOImpl;
import com.javier.escuela.model.Personal;
import com.javier.escuela.view.PersonalView;
import com.javier.escuela.view.RegistrosPersonalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MainController implements ActionListener {

    private PersonalView personalView;
    /**
     * Se inicializa registroPerView de forma global y no al ejecutar el
     * btnRegistros para que pueda acceder en los distintos métodos, ya que
     * arrojaba un NullPointerException al ejecutar el btnRegresar
     */
    private RegistrosPersonalView registrosPerView = new RegistrosPersonalView(personalView, true);
    private DefaultTableModel tableModelRegistrosPersonal = new DefaultTableModel(); // Creación del modelo para la tabla en RegistrosPersonalView

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

    /**
     * NumberFormatException salta cuando se intenta eliminar sin tener un
     * idPersonal seleccionado, por eso se crea isSelectedRecord que permite
     * identificar si hay o no un registro seleccionado.
     */
    private boolean isSelectedRecord = false;

    public MainController(PersonalView personalView, DatabaseConnection conn) {
        // Se inicializan las instancias con el constructor
        this.personalView = personalView;
        this.conn = conn;
        personal = new Personal();
        personalDAOImpl = new PersonalDAOImpl();
        // Se envía el comboModel que se encuentra en personal al comboGenero en la view
        personalView.comboGenero.setModel(personal.getComboModel());
        // Ejecutar la acción del boton
        personalView.btnBuscar.addActionListener(this);
        personalView.btnRegistrar.addActionListener(this);
        personalView.btnActualizar.addActionListener(this);
        personalView.btnEliminar.addActionListener(this);
        personalView.btnLimpiar.addActionListener(this);
        personalView.btnRegistros.addActionListener(this);
        personalView.menuSalir.addActionListener(this);
        loadModel(); // Iniciar el modelo en la tabla de RegistrosPersonalView (si se inicia dentro de startRegistrosPersonalView cada vez que se ingrese a registros se multiplican los modelos en la tabla)
    }

    public void start() {
        personalView.setLocationRelativeTo(null); // Que se localice en el medio de la pantalla al start el programa
        personalView.setTitle("Gestion del personal");
        personalView.setResizable(false); // Impedir modificaciones en el tamaño 
        personalView.cajaID.setVisible(false); // No es necesario la visibilidad de la cajaID
        personalView.setVisible(true); // Permite la visibilidad de la ventana
    }

    /**
     * Se asignan al modelo de la tabla Registros las columnas con sus nombres,
     * este método es condicional para mostrar la información que se desee
     */
    public void loadModel() {
        tableModelRegistrosPersonal.addColumn("N° identificación");
        tableModelRegistrosPersonal.addColumn("Nombre");
        tableModelRegistrosPersonal.addColumn("Email");
        tableModelRegistrosPersonal.addColumn("Celular");
    }

    /**
     * Método que permite comenzar la vista de RegistroPersonal
     *
     * @param registrosPerView
     */
    public void startRegistrosPersonalView(RegistrosPersonalView registrosPerView) {
        registrosPerView.tablaRegistros.setModel(tableModelRegistrosPersonal);
        registrosPerView.setLocationRelativeTo(null);
        registrosPerView.setTitle("Registros del personal");
        registrosPerView.setResizable(false);
        registrosPerView.btnRegresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Condición que indica cual boton está siendo ejecutado    
        if (e.getSource() == personalView.btnRegistrar) {
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
                            personalView.cajaBuscar.setText(identificacion);
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
        if (e.getSource() == personalView.btnBuscar) {
            String buscar = personalView.cajaBuscar.getText().trim();
            if (!buscar.isEmpty()) { // if que permite saber si ingresó o no, un valor en la cajaBuscar
                personal.setNumeroIdentificacion(personalView.cajaBuscar.getText().trim());
                switch (personalDAOImpl.findPersonalByNumIdentification(personal)) {
                    case 1 -> {
                        personalView.cajaID.setText(String.valueOf(personal.getIdPersonal()));
                        personalView.cajaIdentificacion.setText(personal.getNumeroIdentificacion());
                        personalView.cajaNombre.setText(personal.getNombre());
                        personalView.cajaEmail.setText(personal.getEmail());
                        personalView.cajaDireccion.setText(personal.getDireccion());
                        personalView.cajaCelular.setText(personal.getCelular());
                        personalView.cajaIngreso.setText(String.valueOf(personal.getFechaIngreso()));
                        personalView.comboGenero.setSelectedItem(personal.getGenero());
                        JOptionPane.showMessageDialog(null, "Consulta exitosa");
                        isSelectedRecord = true;
                    }
                    case 0 -> {
                        JOptionPane.showMessageDialog(null, "N° identificación no registrado");
                        toClean();
                        personalView.cajaIdentificacion.setText(buscar);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un N° identificación");
                toClean();
            }
        }
        if (e.getSource() == personalView.btnActualizar) {
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
                            personalView.cajaIdentificacion.setText(identificacion);
                            personalView.cajaNombre.setText(nombre);
                            personalView.cajaEmail.setText(email);
                            personalView.cajaDireccion.setText(direccion);
                            personalView.cajaCelular.setText(celular);
                            personalView.cajaIngreso.setText(String.valueOf(fechaIngreso));
                            personalView.comboGenero.setSelectedItem(genero);
                            
                            personalView.cajaID.setText(String.valueOf(personal.getIdPersonal())); // Se envía el valor a la cajaID para eliminar, debido al toClean que la puso ""
                            isSelectedRecord = true; // Para poder eliminar después de actualizar
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
        if (e.getSource() == personalView.btnEliminar) {
            if (isSelectedRecord) { // Con este if se verifica que tenga un registro buscado por medio del btnBuscar 
                // Filtro de confirmación para eliminar registro
                if (JOptionPane.showConfirmDialog(personalView, "¿Seguro de elimanar el registro?", 
                        "Borrar registro",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    personal.setIdPersonal(Integer.parseInt(personalView.cajaID.getText()));
                    switch (personalDAOImpl.deletePersonal(personal)) {
                        case 1 -> {
                            JOptionPane.showMessageDialog(null, "Registro eliminado");
                            toClean();
                        }
                        case 0 ->
                            JOptionPane.showMessageDialog(null, "No se eliminó registro");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Para eliminar seleccione primero un N° identificación");
            }
        }
        if (e.getSource() == personalView.btnLimpiar) {
            toClean();
        }
        if (e.getSource() == personalView.btnRegistros) {
            List<Personal> list = personalDAOImpl.findAllPersonal(personal); // Obtengo la lista generada en findAllPersonal
            tableModelRegistrosPersonal.setRowCount(0); // Este método fija desde donde se comienza indicando la fila, en este caso desde 0 que es el comienzo
            for (Personal persona : list) { // recorro la lista generada en findAllPersonal
                // Obtengo los valores ya generados en Personal
                identificacion = persona.getNumeroIdentificacion();
                nombre = persona.getNombre();
                email = persona.getEmail();
                celular = persona.getCelular();
                direccion = persona.getDireccion();
                fechaIngreso = persona.getFechaIngreso();
                genero = persona.getGenero();
                // Esos valores los asigno en un vector
                Object[] fila = {identificacion, nombre, email, celular}; // Indicar cuales se quieren mostrar y que concuerde con la columna asignada en loadModel
                // Agrego ese vector con los valores obtenidos en la fila de la tabla Registros
                tableModelRegistrosPersonal.addRow(fila);
            }
            startRegistrosPersonalView(registrosPerView); // Se comienzan lo vista
            registrosPerView.setVisible(true); // Se asigna visibilidad
        }
        if (e.getSource() == personalView.menuSalir) {
            System.exit(0);
        }
        if (e.getSource() == registrosPerView.btnRegresar) {
            registrosPerView.dispose();
        }
    }

    /**
     * Método para limpiar las JTextField
     */
    public void toClean() {
        personalView.cajaBuscar.setText("");
        personalView.cajaID.setText("");
        personalView.cajaIdentificacion.setText("");
        personalView.cajaNombre.setText("");
        personalView.cajaEmail.setText("");
        personalView.cajaDireccion.setText("");
        personalView.cajaCelular.setText("");
        personalView.cajaIngreso.setText("");
        personalView.comboGenero.setSelectedIndex(0);
        isSelectedRecord = false;
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
        identificacion = personalView.cajaIdentificacion.getText().trim();
        nombre = personalView.cajaNombre.getText().trim();
        email = personalView.cajaEmail.getText().trim();
        direccion = personalView.cajaDireccion.getText().trim();
        celular = personalView.cajaCelular.getText().trim();
        // El try verifica que la cajaIngreso contenga un formato Date valido
        try {
            fechaIngreso = Date.valueOf(personalView.cajaIngreso.getText().trim());
        } catch (IllegalArgumentException ex) {
            fechaIngreso = null;
        }
        genero = String.valueOf(personalView.comboGenero.getSelectedItem());
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
