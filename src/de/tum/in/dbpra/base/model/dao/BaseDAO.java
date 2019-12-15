package de.tum.in.dbpra.base.model.dao;

import de.tum.in.dbpra.base.DBUtility;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author Michael Schwarz
 */
public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException, ClassNotFoundException {
        return DBUtility.getConnection();
    }
}