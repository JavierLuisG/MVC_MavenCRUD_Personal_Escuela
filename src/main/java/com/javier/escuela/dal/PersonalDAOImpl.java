package com.javier.escuela.dal;

import com.javier.escuela.model.Personal;
import java.sql.*;

/*
* Se Implementa PersonalDAO donde están los métodos para el CRUD 
* No es necesario extender de DatabaseConnection para acceder a la conexión 
 */
public class PersonalDAOImpl implements PersonalDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int insertPersonal(Personal personal) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int findPersonalByNumIdentification(Personal personal) {
        conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT * FROM personal WHERE numero_identificacion = ?";
        try {
            ps = conn.prepareStatement(query);
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
                rs.close(); // es conveniente cerrar el rs despues de realizada la consulta
                ps.close(); // es conveniente cerrar el ps despues de realizada la consulta 
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error en la conexión, " + ex);
            return 0;
        }
    }

    @Override
    public int updatePersonal(Personal personal) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deletePersonal(Personal personal) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
