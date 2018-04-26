/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.repository.redis.dao;

import provider.repository.redis.dao.infra.ConnFactory;
import redis.clients.jedis.Jedis;

/**
 *
 * @author juan
 */
public class UserDao {

    private final Jedis conn;

    public UserDao() {
        conn = ConnFactory.getConnection();
    }

    /**
     * this method store a user in the database, returns a boolean saying if the
     * data was stored.
     *
     * @param email
     * @param senha
     * @return String
     */
    public String newUser(String email, String senha) {
        return conn.set(email, senha);
    }

    /**
     * this method check if a given email and password combination exist in the
     * database.
     *
     * @param email
     * @param senha
     * @return boolean
     */
    public boolean isSigned(String email, String senha) {
        String reply = conn.get(email);
        if(reply == null)
            return false;
        else if(!reply.equals(senha))
            return false;
        return true;
    }
}
