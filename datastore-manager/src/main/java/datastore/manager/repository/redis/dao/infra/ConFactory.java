/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository.redis.dao.infra;

import redis.clients.jedis.Jedis;

/**
 *
 * @author juan
 */
public class ConFactory {

    private static Jedis conn;

    public static Jedis getConnection() {
        String host = System.getenv("JUAN_REDIS_DT_HOST");
        String port = System.getenv("JUAN_REDIS_DT_PORT");
        if(host == null ) 
            host = "dtredis";
        if(port == null ) 
            port = "6379";
        if (conn == null) {
            return conn = new Jedis(host, Integer.parseInt(port));
        }
        return conn;
    }
}
