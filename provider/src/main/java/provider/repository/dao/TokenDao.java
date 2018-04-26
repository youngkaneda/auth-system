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
            throw new RuntimeException("an error occurred trying to get a connection to the database: " + ex.getMessage());
        }
    }

    /**
     * this method store a token in the database and returns a boolean saying if
     * the data was stored.
     *
     * @param token
     * @param createdIn
     * @return boolean
     * @throws SQLException
     */
    public boolean storeToken(String token, long createdIn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into token(tokenValue, createdIn) values(?,?);");
        stmt.setString(1, token);
        stmt.setLong(2, createdIn);
        int isUpdated = stmt.executeUpdate();
        stmt.close();
        return isUpdated > 0;
    }
    
    /**
     * this method remove a token of the database and returns a boolean saying if
     * the data was removed.
     *
     * @param id
     * @return boolean
     * @throws SQLException
     */
    public boolean removeToken(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("delete from token where id=?;");
        stmt.setInt(1, id);
        int isUpdated = stmt.executeUpdate();
        stmt.close();
        return isUpdated > 0;
    }

    /**
     * this method returns a token corresponding to a given id.
     * 
     * @param id
     * @return String
     * @throws SQLException 
     */
    public String getToken(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select tokenValue from token where id=?;");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String tokenValue = rs.getString(1);
            System.out.println("token founded.");
            rs.close();
            stmt.close();
            return tokenValue;
        }
        rs.close();
        stmt.close();
        System.out.println("no token founded with the given ID.");
        return "";
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
