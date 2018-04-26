/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.repository.dao.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author juan
 */
public class ConnFactory {

    private static Connection conn;
    
    /**
     * this method returns a Connection object to the database, if the connections
     * already exist then not return a new connection.
     * @return Connection
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        if(conn == null)
            return conn = DriverManager.getConnection("jdbc:h2:mem:;" + "INIT=RUNSCRIPT FROM 'classpath:schema.sql'\\;", "sa", "");
        else
            return conn;
    }
}
