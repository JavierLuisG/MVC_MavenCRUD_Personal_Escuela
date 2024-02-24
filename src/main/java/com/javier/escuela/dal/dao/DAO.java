
package com.javier.escuela.dal.dao;

import java.util.List;
/**
 * @param <T> interfaz genérica que define los métodos básicos para interactuar con la base de datos
 */
public interface DAO<T> {

    int insertPersonal(T t);

    int findPersonalByNumIdentification(T t);

    int updatePersonal(T t);

    int deletePersonal(T t);

    List<T> findAllPersonal(T t);

}
