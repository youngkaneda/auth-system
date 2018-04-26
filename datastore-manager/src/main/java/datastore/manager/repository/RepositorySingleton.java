/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository;

/**
 *
 * @author juan
 */
public class RepositorySingleton {
    private static Repository instance;
    
    private RepositorySingleton() {
    }
    
    /**
     * this method has the responsibility of ensure that will exist one
     * instance.
     *
     * @return Repository
     */    
    public static Repository getInstance() {
        if(instance == null)
            return instance = new Repository();
        else
            return instance;
    }
}
