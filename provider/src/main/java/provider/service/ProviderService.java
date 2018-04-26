/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider.service;

import provider.repository.redis.Repository;
import provider.repository.redis.RepositorySingleton;

/**
 *
 * @author juan
 */
public class ProviderService {
    private final Repository repo;
    
    public ProviderService() {
        repo = RepositorySingleton.getInstance();
    }
    
    /**
     * this method call the checkLogin method of Repository.
     * 
     * @param email
     * @param senha
     * @return boolean
     */
    public boolean checkLogin(String email, String senha) {
        return repo.isSigned(email, senha);
    }
    
    /**
     * this method call the storeToken method of Repository.
     * 
     * @param token
     * @param createdIn
     * @return boolean
     */
    public boolean storeToken(String token, String createdIn) {
        return repo.storeToken(token, Long.parseLong(createdIn));
    }
}
