package mhdb;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class JPAVista {

    static EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();//crea conexión
    static MonsterJpaController daoMonster = new MonsterJpaController(emf);
    static TypemonsterJpaController daoTipoMonstruo = new TypemonsterJpaController(emf);

    public static void desconectar() {
        emf.close();
    }

    public static void verMonstruosNamedQuery() {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consulta
                    = em.createNamedQuery("Monster.findAll", Monster.class);
            List<Monster> monstruos = consulta.getResultList();
            System.out.println("+----+---------------------------+----------------------+------------+----------+----------+");
            System.out.println("| ID | Name                      |Type                  |  Size      |Size max  | Size min |");
            System.out.println("+----+---------------------------+----------------------+------------+----------+----------+");
            Typemonster tipo = null;
            for (Monster monster : monstruos) {
                System.out.printf("| %2d | %-25s | %-20s | %-10s | %8d | %8d |\n", monster.getIdmonster(),
                        monster.getName(),(monster.getType() != null) ? monster.getType().getTypeeng() : null, monster.getSize(),
                        monster.getSizemax(), monster.getSizemin());
                tipo = null;
            }
            System.out.println("+----+---------------------------+----------------------+------------+----------+----------+");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
