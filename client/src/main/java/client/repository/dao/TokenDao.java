/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.repository.dao;

import client.repository.dao.infra.ConnFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juan
 */
public class TokenDao {

    private Connection conn;

    public TokenDao() {
        try {
            conn = ConnFactory.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get a connection of the databese: " + ex.getMessage());
        }
    }

    /**
     * this method store a token in the database and returns a boolean saying if
     * the data was stored.
     *
     * @param token
     * @param appID
     * @return boolean
     * @throws SQLException
     */
    public boolean storeToken(String token, long appID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into token values(?,?);");
        stmt.setString(1, token);
        stmt.setLong(2, appID);
        int reply = stmt.executeUpdate();
        stmt.close();
        return reply > 0;
    }

    /**
     * this method delete a token of the database and returns a boolean saying if
     * the data was deleted.
     *
     * @param token
     * @return boolean
     * @throws SQLException
     */
    public boolean removeToken(String token) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("delete from token where tokenValue = ?;");
        stmt.setString(1, token);
        int reply = stmt.executeUpdate();
        stmt.close();
        return reply > 0;
    }

    /**
     * this method returns a list with all tokens stored in the database. 
     * 
     * @return List
     * @throws SQLException
     */
    public List<String> getTokens() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select tokenValue from token;");
        ResultSet rs = stmt.executeQuery();
        List<String> tokens = new ArrayList<>();
        while (rs.next()) {
            tokens.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        return tokens;
    }
}
