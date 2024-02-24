package com.javier.escuela.controller;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.dal.dao.implement.PersonalDAOImpl;
import com.javier.escuela.model.Personal;
import com.javier.escuela.view.PersonalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MainController implements ActionListener {

    private PersonalView view;
    private DatabaseConnection conn;
    private Personal personal;
    private PersonalDAOImpl personalDAOImpl;

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
        if (e.getSource() == view.btnBuscar) {
            personal.setNumeroIdentificacion(view.cajaBuscar.getText());
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
                case 0 ->
                    JOptionPane.showMessageDialog(null, "No se realizo consulta");
            }
        }
    }
}
