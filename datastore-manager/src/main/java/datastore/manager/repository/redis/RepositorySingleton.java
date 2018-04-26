/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository.redis;

/**
 *
 * @author juan
 */
public class RepositorySingleton {
    private static Repository instance;
    
    public static Repository getInstance() {
        if(instance == null)
            return instance = new Repository();
        return instance;
    }
}
