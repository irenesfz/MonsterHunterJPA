/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhdb;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import mhdb.exceptions.NonexistentEntityException;

/**
 *
 * @author sh4dowplay
 */
public class TypemonsterJpaController implements Serializable {

    public TypemonsterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Typemonster typemonster) {
        if (typemonster.getMonsterCollection() == null) {
            typemonster.setMonsterCollection(new ArrayList<Monster>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Monster> attachedMonsterCollection = new ArrayList<Monster>();
            for (Monster monsterCollectionMonsterToAttach : typemonster.getMonsterCollection()) {
                monsterCollectionMonsterToAttach = em.getReference(monsterCollectionMonsterToAttach.getClass(), monsterCollectionMonsterToAttach.getIdmonster());
                attachedMonsterCollection.add(monsterCollectionMonsterToAttach);
            }
            typemonster.setMonsterCollection(attachedMonsterCollection);
            em.persist(typemonster);
            for (Monster monsterCollectionMonster : typemonster.getMonsterCollection()) {
                Typemonster oldTypeOfMonsterCollectionMonster = monsterCollectionMonster.getType();
                monsterCollectionMonster.setType(typemonster);
                monsterCollectionMonster = em.merge(monsterCollectionMonster);
                if (oldTypeOfMonsterCollectionMonster != null) {
                    oldTypeOfMonsterCollectionMonster.getMonsterCollection().remove(monsterCollectionMonster);
                    oldTypeOfMonsterCollectionMonster = em.merge(oldTypeOfMonsterCollectionMonster);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Typemonster typemonster) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Typemonster persistentTypemonster = em.find(Typemonster.class, typemonster.getIdtypemonster());
            Collection<Monster> monsterCollectionOld = persistentTypemonster.getMonsterCollection();
            Collection<Monster> monsterCollectionNew = typemonster.getMonsterCollection();
            Collection<Monster> attachedMonsterCollectionNew = new ArrayList<Monster>();
            for (Monster monsterCollectionNewMonsterToAttach : monsterCollectionNew) {
                monsterCollectionNewMonsterToAttach = em.getReference(monsterCollectionNewMonsterToAttach.getClass(), monsterCollectionNewMonsterToAttach.getIdmonster());
                attachedMonsterCollectionNew.add(monsterCollectionNewMonsterToAttach);
            }
            monsterCollectionNew = attachedMonsterCollectionNew;
            typemonster.setMonsterCollection(monsterCollectionNew);
            typemonster = em.merge(typemonster);
            for (Monster monsterCollectionOldMonster : monsterCollectionOld) {
                if (!monsterCollectionNew.contains(monsterCollectionOldMonster)) {
                    monsterCollectionOldMonster.setType(null);
                    monsterCollectionOldMonster = em.merge(monsterCollectionOldMonster);
                }
            }
            for (Monster monsterCollectionNewMonster : monsterCollectionNew) {
                if (!monsterCollectionOld.contains(monsterCollectionNewMonster)) {
                    Typemonster oldTypeOfMonsterCollectionNewMonster = monsterCollectionNewMonster.getType();
                    monsterCollectionNewMonster.setType(typemonster);
                    monsterCollectionNewMonster = em.merge(monsterCollectionNewMonster);
                    if (oldTypeOfMonsterCollectionNewMonster != null && !oldTypeOfMonsterCollectionNewMonster.equals(typemonster)) {
                        oldTypeOfMonsterCollectionNewMonster.getMonsterCollection().remove(monsterCollectionNewMonster);
                        oldTypeOfMonsterCollectionNewMonster = em.merge(oldTypeOfMonsterCollectionNewMonster);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = typemonster.getIdtypemonster();
                if (findTypemonster(id) == null) {
                    throw new NonexistentEntityException("The typemonster with id " + id + " no longer exists.");
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
            Typemonster typemonster;
            try {
                typemonster = em.getReference(Typemonster.class, id);
                typemonster.getIdtypemonster();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typemonster with id " + id + " no longer exists.", enfe);
            }
            Collection<Monster> monsterCollection = typemonster.getMonsterCollection();
            for (Monster monsterCollectionMonster : monsterCollection) {
                monsterCollectionMonster.setType(null);
                monsterCollectionMonster = em.merge(monsterCollectionMonster);
            }
            em.remove(typemonster);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Typemonster> findTypemonsterEntities() {
        return findTypemonsterEntities(true, -1, -1);
    }

    public List<Typemonster> findTypemonsterEntities(int maxResults, int firstResult) {
        return findTypemonsterEntities(false, maxResults, firstResult);
    }

    private List<Typemonster> findTypemonsterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Typemonster.class));
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

    public Typemonster findTypemonster(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Typemonster.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypemonsterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Typemonster> rt = cq.from(Typemonster.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    // *************************************
    // Métodos propios
    // *************************************
    public List<Typemonster> findTypeJPQL(int id) {
        EntityManager em = getEntityManager();
        try {
            Query consultaTipo = em.createQuery("SELECT m FROM TypeMonster m WHERE m.idtypemonster = :id");
            consultaTipo.setParameter("id", id);
            return consultaTipo.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }              
    }
}
