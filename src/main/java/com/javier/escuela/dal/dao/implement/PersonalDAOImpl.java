package com.javier.escuela.dal.dao.implement;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.dal.dao.PersonalDAO;
import com.javier.escuela.model.Personal;
import java.sql.*;
import java.util.List;

/*
* Se Implementa PersonalDAO donde están los métodos para el CRUD 
* No es necesario extender de DatabaseConnection para acceder a la conexión 
 */
public class PersonalDAOImpl implements PersonalDAO {

    final String select = "SELECT * FROM personal WHERE numero_identificacion = ?";

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public int insertPersonal(Personal personal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int findPersonalByNumIdentification(Personal personal) {
        conn = DatabaseConnection.getInstance().getConnection();
        try {
            ps = conn.prepareStatement(select);
            ps.setString(1, personal.getNumeroIdentificacion());
            rs = ps.executeQuery();
            if (rs.next()) {
                personal.setIdPersonal(rs.getInt("idPersonal"));
                personal.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                personal.setNombre(rs.getString("nombre"));
                personal.setEmail(rs.getString("email"));
                personal.setDireccion(rs.getString("direccion"));
                personal.setCelular(rs.getString("celular"));
                personal.setFechaIngreso(rs.getDate("fecha_ingreso"));
                personal.setGenero(rs.getString("genero"));
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error en la conexión, " + ex);
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close(); // se cierra el ps despues de realizada la consulta, se hace en el finally porque puede que entre en el catch y no solo en el try
                } catch (SQLException ex) {
                    System.err.println("No se pudo cerrar el Prepared Statement");
                }
            }
            if (rs != null) {
                try {
                    rs.close(); // se cierra el rs despues de realizada la consulta, se hace en el finally porque puede que entre en el catch y no solo en el try
                } catch (SQLException ex) {
                    System.err.println("No se pudo cerrar el Result Set");
                }
            }
            DatabaseConnection.getInstance().closeConnection(); // Gestionar el cierre de la conexion a la base de datos desde el Controlador
        }
    }

    @Override
    public int updatePersonal(Personal personal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deletePersonal(Personal personal) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Personal> findAllPersonal(Personal t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
