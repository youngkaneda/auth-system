/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.repository;

import java.sql.SQLException;
import java.util.List;
import provider.repository.dao.TokenDao;
import provider.repository.dao.UserDao;

/**
 *
 * @author juan
 */
public class Repository {

    private final UserDao usDao;
    private final TokenDao tkDao;

    Repository() {
        this.usDao = new UserDao();
        this.tkDao = new TokenDao();
    }

    /**
     * this method call the newUser method of UserDao.
     *
     * @param email
     * @param senha
     * @return boolean
     */
    public boolean newUser(String email, String senha) {
        try {
            return usDao.newUser(email, senha);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to add a new user to database: " + ex.getMessage());
        }
    }

    /**
     * this method call the isSigned method of UserDao.
     *
     * @param email
     * @param senha
     * @return boolean
     */
    public boolean isSigned(String email, String senha) {
        try {
            return usDao.isSigned(email, senha);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to verify if a login exist in database: " + ex.getMessage());
        }
    }

    /**
     * this method call the storeToken method of TokenDao.
     *
     * @param token
     * @param createdIn
     * @return boolean
     */
    public boolean storeToken(String token, long createdIn) {
        try {
            return tkDao.storeToken(token, createdIn);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to store a token in database: " + ex.getMessage());
        }
    }

    /**
     * this method call the removeToken method of TokenDao.
     *
     * @param id
     * @return boolean
     */
    public boolean removeToken(int id) {
        try {
            return tkDao.removeToken(id);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to remove a token of database: " + ex.getMessage());
        }
    }

    /**
     * this method call the getToken method of TokenDao.
     *
     * @param id
     * @return String
     */
    public String getToken(int id) {
        try {
            return tkDao.getToken(id);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get a token with a given id in database: " + ex.getMessage());
        }
    }

    /**
     * this method call the getTokens method of TokenDao.
     *
     * @return List
     */
    public List<String> getTokens() {
        try {
            return tkDao.getTokens();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get all tokens stored in database: " + ex.getMessage());
        }
    }
}
