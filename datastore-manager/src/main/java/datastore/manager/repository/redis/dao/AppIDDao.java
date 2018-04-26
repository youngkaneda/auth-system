/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository.redis.dao;

import datastore.manager.repository.redis.dao.infra.ConFactory;
import redis.clients.jedis.Jedis;

/**
 *
 * @author juan
 */
public class AppIDDao {
    
    private final Jedis conn;
    
    public AppIDDao() {
        conn = ConFactory.getConnection();
    }
    
    /**
     * 
     * @param appID
     * @param URL
     * @return String
     */
    public String storeAppID(String appID, String URL) {
        return conn.set(appID, URL);
    }
    
    /**
     * 
     * @param appID
     * @return String 
     */
    public String getURL(String appID) {
        return conn.get(appID);
    }
}
