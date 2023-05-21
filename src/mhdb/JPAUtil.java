/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mhdb;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    public static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("MonsterHunterJPAPU");
            }
        } catch (Throwable t) {
            System.out.println("Error al iniciar el Entity Manager factory");
            t.printStackTrace();
            throw new ExceptionInInitializerError();
        }
        return emf;
    }
}
