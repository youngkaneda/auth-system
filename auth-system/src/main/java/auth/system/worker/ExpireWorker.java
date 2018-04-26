/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.system.worker;

import datastore.manager.RepositoryManager;
import java.util.Set;

/**
 *
 * @author juan
 */
public class ExpireWorker extends Thread {

    private final RepositoryManager manager;

    public ExpireWorker(String name, RepositoryManager manager) {
        super(name);
        this.manager = manager;
    }

    /**
     * this method have the task to check periodically if the tokens stored in
     * the database have to expire, if the token list returned by the
     * managerRepository is empty, then the method sleep for 10 seconds and
     * start the loop again.
     */
    @Override
    public void run() {
        Set<String> tokens;
        while (true) {
            try {
                tokens = manager.getTokens();
                System.out.println(tokens);
                if (tokens.isEmpty()) {
                    Thread.sleep(10000);
                    continue;
                }
                tokens.stream().filter(manager::tokenIsOld).forEach(manager::removeToken);
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                throw new RuntimeException("an error occured in worker thread: " + ex.getMessage());
            }
        }
    }
}
