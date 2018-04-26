/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository;

import datastore.manager.repository.dao.AppIDDao;
import datastore.manager.repository.dao.TokenDao;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juan
 */
public class Repository {

    private final AppIDDao aiDao;
    private final TokenDao tkDao;

    Repository() {
        aiDao = new AppIDDao();
        tkDao = new TokenDao();
    }

    /**
     * call the storeAppID method of AppIDDao.
     *
     * @param appID
     * @param URL
     * @return boolean
     */
    public boolean storeAppID(int appID, String URL) {
        try {
            return aiDao.storeAppID(appID, URL);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to store a appID in database: " + ex.getMessage());
        }
    }

    /**
     * call the getURL method of AppIDDao.
     *
     * @param appID
     * @return String
     */
    public String getURL(int appID) {
        try {
            return aiDao.getURL(appID);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to store a URL of database: " + ex.getMessage());
        }
    }

    /**
     * call the storeToken method of TokenDao.
     *
     * @param token
     * @param appID
     * @param createdIn
     * @return boolean
     */
    public boolean storeToken(String token, int appID, long createdIn) {
        try {
            return tkDao.storeToken(token, appID, createdIn);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to store a token in database: " + ex.getMessage());
        }
    }

    /**
     * call the removeToken method of TokenDao.
     *
     * @param token
     * @return boolean
     */
    public boolean removeToken(String token) {
        try {
            return tkDao.removeToken(token);
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to remove a token in database: " + ex.getMessage());
        }
    }

    /**
     * call the getTokenTime method of TokenDao, because if this column value of
     * database can be returned with this token value, this means that the given
     * token still be in the database. If the expire time have come, the token
     * don't still stored in th database. 
     * 
     * @param token
     * @return boolean
     */
    public boolean isValid(String token) {
        try {
            long tokenTime = tkDao.getTokenTime(token);
            return tokenTime > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to check if a token is valid in database: " + ex.getMessage());
        }
    }

    /**
     * this method verify if the expire time of a given token has come.
     * 
     * @param token
     * @return boolean 
     */
    public boolean tokenIsOld(String token) {
        try {
            long time = tkDao.getTokenTime(token);
            Date dt = new Date(time);
            //plus the created date with a given value;
            LocalDateTime plusDt = LocalDateTime.from(dt.toInstant().atZone(ZoneId.systemDefault())).plusSeconds(10);
            // if the update date is equal or already passed, then returns true, else false
            return plusDt.isEqual(LocalDateTime.now()) || plusDt.isBefore(LocalDateTime.now());
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to check if the token life is old to he stay in database: " + ex.getMessage());
        }
    }

    /**
     * call the getTokens method of TokenDao.
     * 
     * @return List 
     */
    public List<String> getTokens() {
        try {
            return tkDao.getTokens();
        } catch (SQLException ex) {
            throw new RuntimeException("an error occurred trying to get all tokens in database: " + ex.getMessage());
        }
    }
}
