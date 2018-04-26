/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository.redis;

import datastore.manager.repository.redis.dao.AppIDDao;
import datastore.manager.repository.redis.dao.TokenDao;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import org.json.JSONObject;

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
        return aiDao.storeAppID(Integer.toString(appID), URL).equals("OK");
    }

    /**
     * call the getURL method of AppIDDao.
     *
     * @param appID
     * @return String
     */
    public String getURL(int appID) {
        return aiDao.getURL(Integer.toString(appID));
    }

    /**
     * call the storeToken method of TokenDao.
     *
     * @param token
     * @param appID
     * @param user_email
     * @param createdIn
     * @return boolean
     */
    public boolean storeToken(String token, int appID, String user_email, long createdIn) {
        JSONObject object = new JSONObject();
        object.put("appID", appID);
        object.put("user_email", user_email);
        object.put("createdIn", createdIn);
        return tkDao.storeToken(token, object.toString()).equals("OK");
    }

    /**
     * call the removeToken method of TokenDao.
     *
     * @param token
     * @return boolean
     */
    public boolean removeToken(String token) {
        return tkDao.removeToken(token) > 0;
    }

    /**
     * call the getTokenTime method of TokenDao, because if this key value of
     * database can be returned with this token value, this means that the given
     * token still be in the database. If the expire time have come, the token
     * don't still stored in th database.
     *
     * @param token
     * @return boolean
     */
    public boolean isValid(String token) {
        long tokenTime = tkDao.getTokenTime(token);
        return tokenTime > 0;
    }

    /**
     * this method verify if the expire time of a given token has come.
     *
     * @param token
     * @return boolean
     */
    public boolean tokenIsOld(String token) {
        long time = tkDao.getTokenTime(token);
        Date dt = new Date(time);
        //plus the created date with a given value;
        LocalDateTime plusDt = LocalDateTime.from(dt.toInstant().atZone(ZoneId.systemDefault())).plusSeconds(10);
        // if the update date is equal or already passed, then returns true, else false
        return plusDt.isEqual(LocalDateTime.now()) || plusDt.isBefore(LocalDateTime.now());
    }

    /**
     * call the getTokens method of TokenDao.
     *
     * @return Set
     */
    public Set<String> getTokens() {
        return tkDao.getTokens();
    }

    /**
     * 
     * @param user_email
     * @return String
     */
    public String recoverUserToken(String user_email) {
        return tkDao.recoverUserToken(user_email);
    }
}
