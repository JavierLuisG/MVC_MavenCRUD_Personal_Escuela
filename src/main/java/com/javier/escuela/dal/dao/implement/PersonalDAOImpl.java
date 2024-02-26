package com.javier.escuela.dal.dao.implement;

import com.javier.escuela.dal.DatabaseConnection;
import com.javier.escuela.dal.dao.PersonalDAO;
import com.javier.escuela.model.Personal;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Se Implementa PersonalDAO donde están los métodos para el CRUD 
* No es necesario extender de DatabaseConnection para acceder a la conexión 
 */
public class PersonalDAOImpl implements PersonalDAO {

    final String insert = "INSERT INTO personal (numero_identificacion,nombre,email,direccion,celular,fecha_ingreso,genero) "
            + "VALUES (?,?,?,?,?,?,?)";
    final String select = "SELECT * FROM personal WHERE numero_identificacion = ?";
    final String update = "UPDATE personal SET numero_identificacion = ?, nombre = ?, email = ?, direccion = ?, "
            + "celular = ?, fecha_ingreso = ?, genero = ? WHERE idPersonal = ?";
    final String delete = "DELETE FROM personal WHERE idPersonal = ?";

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public int insertPersonal(Personal personal) {
        // se llama al metodo getInstance y a su vez se asigna a la conexion por medio de getConnection
        conn = DatabaseConnection.getInstance().getConnection();
        try {
            ps = conn.prepareStatement(insert);
            ps.setString(1, personal.getNumeroIdentificacion());
            ps.setString(2, personal.getNombre());
            ps.setString(3, personal.getEmail());
            ps.setString(4, personal.getDireccion());
            ps.setString(5, personal.getCelular());
            ps.setDate(6, personal.getFechaIngreso());
            ps.setString(7, personal.getGenero());
            if (ps.executeUpdate() > 0) {
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
            DatabaseConnection.getInstance().closeConnection(); // Gestionar el cierre de la conexion a la base de datos desde el Controlador
        }
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
        conn = DatabaseConnection.getInstance().getConnection();
        try {
            ps = conn.prepareStatement(update);
            ps.setString(1, personal.getNumeroIdentificacion());
            ps.setString(2, personal.getNombre());
            ps.setString(3, personal.getEmail());
            ps.setString(4, personal.getDireccion());
            ps.setString(5, personal.getCelular());
            ps.setDate(6, personal.getFechaIngreso());
            ps.setString(7, personal.getGenero());
            ps.setInt(8, personal.getIdPersonal());
            if (ps.executeUpdate() > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error en la conexión, " + ex);
            return 0;
        } finally {
            try {
                ps.close(); // se cierra el ps despues de realizada la consulta, se hace en el finally porque puede que entre en el catch y no solo en el try
            } catch (SQLException ex) {
                System.err.println("No se pudo cerrar el Prepared Statement");
            }
            DatabaseConnection.getInstance().closeConnection(); // Gestionar el cierre de la conexion a la base de datos desde el Controlador
        }
    }

    @Override
    public int deletePersonal(Personal personal) {
        conn = DatabaseConnection.getInstance().getConnection();
        try {
            ps = conn.prepareStatement(delete);
            ps.setInt(1, personal.getIdPersonal());
            if (ps.executeUpdate() > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error en la conexión, " + ex);
            return 0;
        } finally {
            try {
                ps.close(); // se cierra el ps despues de realizada la consulta, se hace en el finally porque puede que entre en el catch y no solo en el try
            } catch (SQLException ex) {
                System.err.println("No se pudo cerrar el Perared Statement");
            }
            DatabaseConnection.getInstance().closeConnection(); // Gestionar el cierre de la conexion a la base de datos desde el Controlador
        }
    }

    @Override
    public List<Personal> findAllPersonal(Personal t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
