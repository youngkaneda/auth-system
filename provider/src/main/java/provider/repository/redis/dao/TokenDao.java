/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.repository.redis.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import provider.repository.redis.dao.infra.ConnFactory;
import redis.clients.jedis.Jedis;

/**
 *
 * @author juan
 */
public class TokenDao {

    private Jedis conn;

    public TokenDao() {
        conn = ConnFactory.getConnection();
    }

    /**
     * this method store a token in the database and returns a String saying if
     * the data was stored.
     *
     * @param token
     * @param createdIn
     * @return boolean
     */
    public String storeToken(String token, String createdIn) {
        return conn.set(token, createdIn);
    }

    /**
     * this method remove a token of the database and returns a String saying if
     * the data was removed.
     *
     * @param token
     * @return long
     */
    public long removeToken(String token) {
        return conn.del(token);
    }

    /**
     * this method returns a list with all tokens stored in the database.
     *
     * @return List
     */
    public List<String> getTokens() {
        Set<String> keys = conn.keys("*[A-z]*[0-9]*");
        String[] array = keys.toArray(new String[keys.size()]);
        return Arrays.stream(array).filter((k) -> !k.contains("@")).collect(Collectors.toList());
    }
}
