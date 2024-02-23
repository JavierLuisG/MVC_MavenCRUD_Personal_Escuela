package com.javier.escuela.dal;

import com.javier.escuela.model.Personal;
import java.sql.SQLException;

/**
 * Interfaz para el acceso a datos de la entidad Personal. -> CRUD
 */
public interface PersonalDAO {
    int insertPersonal(Personal personal) throws SQLException;
    int findPersonalByNumIdentification(Personal personal) throws SQLException;
    int updatePersonal(Personal personal) throws SQLException;
    int deletePersonal(Personal personal) throws SQLException;
}
