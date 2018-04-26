/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager;

import datastore.manager.repository.redis.Repository;
import datastore.manager.repository.redis.RepositorySingleton;
import java.util.Set;

/**
 * this class is used by the projects that use this system, to not have
 * free access to the Repository and others object.
 * @author juan
 */
public class RepositoryManager {
    private final Repository repo;
    
    public RepositoryManager() {
        repo = RepositorySingleton.getInstance();
    }
    
    /**
     * 
     * @param appID
     * @param URL
     * @return boolean
     */
    public boolean storeAppID(int appID, String URL) {
        return repo.storeAppID(appID, URL);
    }

    /**
     * 
     * @param appID
     * @return String
     */
    public String getURL(int appID) {
        return repo.getURL(appID);
    }

    /**
     * 
     * @param token
     * @param appID
     * @param user_email
     * @param createdIn
     * @return boolean 
     */
    public boolean storeToken(String token, int appID, String user_email, long createdIn) {
        return repo.storeToken(token, appID, user_email, createdIn);
    }
    
    /**
     * 
     * @param token
     * @return boolean
     */
    public boolean removeToken(String token) {
        return repo.removeToken(token);
    }
    
    /**
     * 
     * @param token
     * @return boolean
     */
    public boolean isValid(String token) {
        return repo.isValid(token);
    }
    
    /**
     * 
     * @param token
     * @return boolean 
     */
    public boolean tokenIsOld(String token) {
        return repo.tokenIsOld(token);
    }
    
    /**
     * 
     * @return List
     */
    public Set<String> getTokens() {
        return repo.getTokens();
    }
}
