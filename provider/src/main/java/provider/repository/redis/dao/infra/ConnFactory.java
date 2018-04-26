/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.repository.redis.dao.infra;

import redis.clients.jedis.Jedis;

/**
 *
 * @author juan
 */
public class ConnFactory {

    private static Jedis conn;

    public static Jedis getConnection() {
        if(conn == null)
            return conn = new Jedis("prredis", 6379);
        return conn;
    }
}
