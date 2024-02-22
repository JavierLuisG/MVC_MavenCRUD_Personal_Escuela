package com.javier.escuela.main;

import com.javier.escuela.controller.MainController;
import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.view.PersonalView;

public class Main {
    
    public static void main(String[] args) {
        
        PersonalView view = new PersonalView();
        DatabaseConnection conn = DatabaseConnection.getInstance();
        
        MainController control = new MainController(view, conn);
        control.iniciar();
    }    
}
