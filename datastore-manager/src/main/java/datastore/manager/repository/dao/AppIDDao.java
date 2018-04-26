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

/**
 *
 * @author juan
 */
public class AppIDDao {

    private final Connection conn;

    public AppIDDao() {
        try {
            conn = ConnFactory.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get a connection with the database:" + ex.getMessage());
        }
    }
    /**
     * this method is used for store an appID with a related URL, returns a boolean
     * saying if the data was stored.
     * 
     * @param appID
     * @param URL
     * @return boolean
     * @throws SQLException 
     */
    public boolean storeAppID(int appID, String URL) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into host(appID, URL) values(?,?);");
        stmt.setInt(1, appID);
        stmt.setString(2, URL);
        int reply = stmt.executeUpdate();
        stmt.close();
        return reply > 0;
    }
    
    /**
     * this method returns a URL related with the given appID
     * 
     * @param appID
     * @return String
     * @throws SQLException 
     */
    public String getURL(int appID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select URL from host where appID = ?;");
        stmt.setInt(1, appID);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            String URL = rs.getString(1);
            rs.close();
            stmt.close();
            return URL;
        }
        rs.close();
        stmt.close();
        return "";
    }
}
