package com.javier.escuela.dal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
   
    // La única instancia permitida de la clase DatabaseConnection
    private static DatabaseConnection instancia = null;
    // La conexión a la base de datos
    private Connection conn;
    // Las propiedades de conexión a la base de datos
    Properties properties = new Properties();
    // El flujo de entrada para cargar las propiedades
    private InputStream inputStream;
    // La URL de conexión a la base de datos
    private String url;
    
    /* Para implementar correctamente el patrón Singleton en una clase, se debe hacer el constructor de la clase privado para prevenir la creación de nuevas instancias utilizando el operador `new`. 
       Luego, se crea un método estático `getInstance()` que cree y devuelva la única instancia permitida de la clase si aún no existe, y simplemente devuelva la instancia existente en lugar de crear una nueva si ya ha sido creada. */
    private DatabaseConnection() {}

    // El método getConnection() se encarga de obtener y devolver la conexión a la base de datos
    public Connection getConnection() {
        try {            
            loadPropertiesDB();
            String driver = properties.getProperty("driver");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error, " + ex);
        }
        return conn;
    }

    // El método loadPropertiesDB() se encarga de cargar las propiedades de conexión desde el archivo applicationDB.properties
    private void loadPropertiesDB() {
        try {            
            inputStream = getClass().getClassLoader().getResourceAsStream("applicationDB.properties");
            properties.load(inputStream);
            String hostname = properties.getProperty("hostname");
            String port = properties.getProperty("port");
            String database = properties.getProperty("database");
            url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
        } catch (NullPointerException ex) {
            System.err.println("Error, " + ex);
        } catch (FileNotFoundException ex) {
            System.err.println("Error, loading properties: " + ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // El método getInstance() se encarga de crear y devolver la única instancia permitida de la clase
    public static DatabaseConnection getInstance() {
        if (instancia == null) {
            return instancia = new DatabaseConnection();
        } else {
            return instancia;
        }
    }
}