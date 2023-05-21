/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhdb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import mhdb.exceptions.NonexistentEntityException;

/**
 *
 * @author sh4dowplay
 */
public class MonsterJpaController implements Serializable {

    public MonsterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Monster monster) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Typemonster type = monster.getType();
            if (type != null) {
                type = em.getReference(type.getClass(), type.getIdtypemonster());
                monster.setType(type);
            }
            em.persist(monster);
            if (type != null) {
                type.getMonsterCollection().add(monster);
                type = em.merge(type);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Monster monster) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Monster persistentMonster = em.find(Monster.class, monster.getIdmonster());
            Typemonster typeOld = persistentMonster.getType();
            Typemonster typeNew = monster.getType();
            if (typeNew != null) {
                typeNew = em.getReference(typeNew.getClass(), typeNew.getIdtypemonster());
                monster.setType(typeNew);
            }
            monster = em.merge(monster);
            if (typeOld != null && !typeOld.equals(typeNew)) {
                typeOld.getMonsterCollection().remove(monster);
                typeOld = em.merge(typeOld);
            }
            if (typeNew != null && !typeNew.equals(typeOld)) {
                typeNew.getMonsterCollection().add(monster);
                typeNew = em.merge(typeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = monster.getIdmonster();
                if (findMonster(id) == null) {
                    throw new NonexistentEntityException("The monster with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Monster monster;
            try {
                monster = em.getReference(Monster.class, id);
                monster.getIdmonster();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The monster with id " + id + " no longer exists.", enfe);
            }
            Typemonster type = monster.getType();
            if (type != null) {
                type.getMonsterCollection().remove(monster);
                type = em.merge(type);
            }
            em.remove(monster);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Monster> findMonsterEntities() {
        return findMonsterEntities(true, -1, -1);
    }

    public List<Monster> findMonsterEntities(int maxResults, int firstResult) {
        return findMonsterEntities(false, maxResults, firstResult);
    }

    private List<Monster> findMonsterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Monster.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Monster findMonster(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Monster.class, id);
        } finally {
            em.close();
        }
    }

    public int getMonsterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Monster> rt = cq.from(Monster.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
