/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository.dao;

import datastore.manager.repository.dao.infra.ConnFactory;
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

    private final Connection conn;

    public TokenDao() {
        try {
            conn = ConnFactory.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get a connection with the database:" + ex.getMessage());
        }
    }

    /**
     * this method store a token in the database and returns a boolean saying if
     * the data was stored.
     *
     * @param token
     * @param appID
     * @param createdIn
     * @return boolean
     * @throws SQLException
     */
    public boolean storeToken(String token, int appID, long createdIn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into token values(?,?,?);");
        stmt.setString(1, token);
        stmt.setInt(2, appID);
        stmt.setLong(3, createdIn);
        int reply = stmt.executeUpdate();
        stmt.close();
        return reply > 0;
    }
    
    /**
     * this method delete a token of the database and returns a boolean saying
     * if the data was deleted.
     *
     * @param token
     * @return boolean
     * @throws SQLException
     */
    public boolean removeToken(String token) throws SQLException {
        if (token.equals("")) {
            return false;
        }
        PreparedStatement stmt = conn.prepareStatement("delete from token where tokenValue = ?;");
        stmt.setString(1, token);
        int reply = stmt.executeUpdate();
        stmt.close();
        return reply > 0;
    }

    /**
     * this method return the time when the given token was created.
     *
     * @param token
     * @return long
     * @throws SQLException
     */
    public long getTokenTime(String token) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select createdIn from token where tokenValue = ?;");
        stmt.setString(1, token);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            long createdIn = rs.getLong(1);
            rs.close();
            stmt.close();
            return createdIn;
        }
        rs.close();
        stmt.close();
        return 0;
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
