/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.repository.dao;

import provider.repository.dao.infra.ConnFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author juan
 */
public class UserDao {

    private final Connection conn;

    public UserDao() {
        try {
            conn = ConnFactory.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get a connection to the database: " + ex.getMessage());
        }
    }

    /**
     * this method store a user in the database, returns a boolean saying if the
     * data was stored.
     *
     * @param email
     * @param senha
     * @return boolean
     * @throws SQLException
     */
    public boolean newUser(String email, String senha) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into user(email, senha) values(?,?);");
        stmt.setString(1, email);
        stmt.setString(2, senha);
        int isUpdated = stmt.executeUpdate();
        stmt.close();
        return isUpdated > 0;
    }

    /**
     * this method check if a given email and password combination exist in the
     * database.
     *
     * @param email
     * @param senha
     * @return boolean
     * @throws SQLException
     */
    public boolean isSigned(String email, String senha) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select id from user where email = ? and senha = ?;");
        stmt.setString(1, email);
        stmt.setString(2, senha);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            System.out.println("user login combination founded, the current user id is: " + id + '.');
        } else {
            rs.close();
            stmt.close();
            return false;
        }
        rs.close();
        stmt.close();
        return true;
    }
}
