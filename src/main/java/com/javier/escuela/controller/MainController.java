package com.javier.escuela.controller;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.view.PersonalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener{

    // Probar conexión 
    
    private PersonalView view; // = new PersonalView();
    private DatabaseConnection conn; // = DatabaseConnection.getInstance();  

    public MainController(PersonalView view, DatabaseConnection conn) {
        this.view = view;
        this.conn = conn;
        view.btnBuscar.addActionListener(this);
    }
    
    public void iniciar () {
        view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.btnBuscar) {
            try {
                conn.getConnection();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }
}