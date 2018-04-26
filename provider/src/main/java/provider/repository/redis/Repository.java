/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.repository.redis;

import java.util.List;
import java.util.Set;
import provider.repository.redis.dao.TokenDao;
import provider.repository.redis.dao.UserDao;

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
        return usDao.newUser(email, senha).equals("OK");
    }

    /**
     * this method call the isSigned method of UserDao.
     *
     * @param email
     * @param senha
     * @return boolean
     */
    public boolean isSigned(String email, String senha) {
        return usDao.isSigned(email, senha);
    }

    /**
     * this method call the storeToken method of TokenDao.
     *
     * @param token
     * @param createdIn
     * @return boolean
     */
    public boolean storeToken(String token, long createdIn) {
        return tkDao.storeToken(token, Long.toString(createdIn)).equals("OK");
    }

    /**
     * this method call the removeToken method of TokenDao.
     *
     * @param token
     * @return boolean
     */
    public boolean removeToken(String token) {
        return tkDao.removeToken(token) > 0;
    }

    /**
     * this method call the getTokens method of TokenDao.
     *
     * @return List
     */
    public List<String> getTokens() {
        return tkDao.getTokens();
    }
}
