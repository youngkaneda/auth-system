/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.repository;

import client.repository.dao.TokenDao;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author juan
 */
public class Repository {
    private final TokenDao dao;
    
    public Repository() {
        dao = new TokenDao();
    }
    
    /**
     * call the storeToken method of DAO.
     * 
     * @param token
     * @param appId
     * @return boolean
     */
    public boolean storeToken(String token, int appId) {
        try {
            return dao.storeToken(token, appId);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to store a token in database: " + ex.getMessage());
        }
    }
    
    /**
     * call the removeToken method of DAO.
     * 
     * @param token
     * @return boolean
     */
    public boolean removeToken(String token) {
        try {
            return dao.removeToken(token);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to remove a token in database: " + ex.getMessage());
        }
    }
    
    /**
     * call the getTokens method of DAO.
     * 
     * @return List
     */
    public List<String> getTokens() {
        try {
            return dao.getTokens();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying get the tokens in database: " + ex.getMessage());
        }
    }
}
