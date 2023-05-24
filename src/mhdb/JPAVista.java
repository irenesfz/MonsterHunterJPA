package mhdb;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class JPAVista {

    static EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();//crea conexión
    static MonsterJpaController daoMonster = new MonsterJpaController(emf);
    static TypemonsterJpaController daoTipoMonstruo = new TypemonsterJpaController(emf);

    public static void desconectar() {
        emf.close();
    }

    public static void verCabeceraTablaMonstruos(List<Monster> monstruos) {
        Typemonster tipo = null;
        System.out.println("+----+---------------------------+----------------------+------------+----------+----------+");
        System.out.println("| ID | Name                      |Type                  |  Size      |Size max  | Size min |");
        System.out.println("+----+---------------------------+----------------------+------------+----------+----------+");
        for (Monster monster : monstruos) {
            System.out.printf("| %2d | %-25s | %-20s | %-10s | %8d | %8d |\n", monster.getIdmonster(),
                    monster.getName(), (monster.getType() != null) ? monster.getType().getTypeeng() : null, monster.getSize(),
                    monster.getSizemax(), monster.getSizemin());
            tipo = null;
        }
        System.out.println("+----+---------------------------+----------------------+------------+----------+----------+");
    }

    public static void verMonstruosNamedQuery() {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consulta
                    = em.createNamedQuery("Monster.findAll", Monster.class);
            List<Monster> monstruos = consulta.getResultList();
            JPAVista.verCabeceraTablaMonstruos(monstruos);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verTextoMonstruosFiltrados() {
        String cadenaMenu = "Escribe un número según quieras filtrar: ";
        String[] opcionesMenu = {"Nombre", "Tamaño", "Tipo", "Tamaño mínimo", "Tamaño máximo", "Salir"};
        List<String> listaOpciones = Arrays.asList(opcionesMenu);
        int num = 1;
        for (String opcion : listaOpciones) {
            System.out.println(num + "-" +opcion);
            num++;
        }
    }

    public static void verMonstruosFiltradosPorNombre(String dato) {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consulta
                    = em.createNamedQuery("Monster.findByName", Monster.class);
            consulta.setParameter("name", dato);
            List<Monster> monstruos = consulta.getResultList();
            JPAVista.verCabeceraTablaMonstruos(monstruos);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verMonstruosFiltradosPorTamaño(String dato) {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consulta
                    = em.createNamedQuery("Monster.findBySize", Monster.class);
            consulta.setParameter("size", "%" + dato + "%");
            List<Monster> monstruos = consulta.getResultList();
            JPAVista.verCabeceraTablaMonstruos(monstruos);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verMonstruosFiltradosPorTipo(String dato) {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consultaMonstruo
                    = em.createNamedQuery("Monster.findAll", Monster.class);
            List<Monster> monstruos = consultaMonstruo.getResultList();
            for (Monster monstruo : monstruos) {
                if (!monstruo.getType().getTypeesp().equals(dato)) {
                    monstruos.remove(monstruo);
                }
            }
            JPAVista.verCabeceraTablaMonstruos(monstruos);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verMonstruosFiltradosPorSizeMax(String dato) {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consulta
                    = em.createNamedQuery("Monster.findBySizemin", Monster.class);
            consulta.setParameter("sizemin", "%" + dato + "%");
            List<Monster> monstruos = consulta.getResultList();
            JPAVista.verCabeceraTablaMonstruos(monstruos);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verMonstruosFiltradosPorSizeMin(String dato) {
        EntityManager em = null;
        try {
            em = daoMonster.getEntityManager();
            TypedQuery<Monster> consulta
                    = em.createNamedQuery("Monster.findBySizemax", Monster.class);
            consulta.setParameter("sizemax", "%" + dato + "%");
            List<Monster> monstruos = consulta.getResultList();
            JPAVista.verCabeceraTablaMonstruos(monstruos);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void verMonstruosFiltrados() {
        JPAVista.verTextoMonstruosFiltrados();
        Scanner entrada = new Scanner(System.in);
        int numero = entrada.nextInt();
        while (numero < 1 || numero > 6) {
            JPAVista.verTextoMonstruosFiltrados();
            numero = entrada.nextInt();
        }
        if (numero == 6) {
            System.out.println("Has elegido Salir");
        } else {
            System.out.print("Escribe la palabra a filtrar: ");
            entrada.nextLine();
            String palabra = entrada.nextLine();
            switch (numero) {
                case 1:
                    JPAVista.verMonstruosFiltradosPorNombre(palabra);
                    break;
                case 2:
                    JPAVista.verMonstruosFiltradosPorTamaño(palabra);
                    break;
                case 3:
                    JPAVista.verMonstruosFiltradosPorTipo(palabra);
                    break;
                case 4:
                    JPAVista.verMonstruosFiltradosPorSizeMax(palabra);
                    break;
                case 5:
                    JPAVista.verMonstruosFiltradosPorSizeMin(palabra);
                    break;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
        }
    }

}
