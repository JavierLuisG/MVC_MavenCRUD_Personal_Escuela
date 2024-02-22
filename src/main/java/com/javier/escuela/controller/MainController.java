package com.javier.escuela.controller;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.view.PersonalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener{

    // Probar conexión 
    
    static PersonalView view = new PersonalView();
    DatabaseConnection conn = DatabaseConnection.getInstance();
    
    public static void main(String[] args) {
        
        view.setVisible(true);
        view.btnBuscar.addActionListener(new MainController());
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