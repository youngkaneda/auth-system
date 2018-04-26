/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider;

import provider.repository.redis.Repository;
import provider.repository.redis.RepositorySingleton;

/**
 *
 * @author juan
 */
public class Lixo {
    public static void main(String[] args) {
        Repository repo = RepositorySingleton.getInstance();
        repo.getTokens().forEach(System.out::println);
        repo.getTokens().forEach(repo::removeToken);
    }
}
