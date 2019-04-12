package com.cor.pucmm.cor.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;

public class GeneralService<T> {

    private static EntityManagerFactory emf;
    private Class<T> claseEntidadGeneral;


    public GeneralService(Class<T> claseEntidad) {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        this.claseEntidadGeneral = claseEntidad;

    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * @param entidad
     * @return
     */
    private Object getValorCampo(T entidad) {
        if (entidad == null) {
            return null;
        }
        for (Field f : entidad.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                try {
                    f.setAccessible(true);
                    Object valorCampo = f.get(entidad);

                    return valorCampo;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * @param entidad
     */
    public void crear(T entidad) {
        EntityManager em = getEntityManager();

        try {
            if (em.find(claseEntidadGeneral, getValorCampo(entidad)) != null) {
                System.out.println("La entidad a guardar existe, no creada.");
                return;
            }
        } catch (IllegalArgumentException ie) {
            //
            System.out.println("Parametro aceptado. No agregado");
        }

        em.getTransaction().begin();
        try {
            em.persist(entidad);
            em.getTransaction().commit();

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    /**
     * @param entidad
     */
    public void editar(T entidad) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    /**
     * @param codigo
     */
    public void eliminar(Object codigo) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(claseEntidadGeneral, codigo);
            em.remove(entidad);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    /**
     * @param codigo
     * @return
     */
    public T find(Object codigo) {
        EntityManager em = getEntityManager();
        try {
            return em.find(claseEntidadGeneral, codigo);
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    /**
     * @return
     */
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidadGeneral);
            criteriaQuery.select(criteriaQuery.from(claseEntidadGeneral));
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally {
            em.close();
        }
    }

    public T findSegunColumna(List<String> column, List<String> valor) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidadGeneral);
            Root<T> obtenido = criteriaQuery.from(claseEntidadGeneral);
            criteriaQuery.select(obtenido);

            for (int i = 0; i < column.size(); i++) {
                criteriaQuery.where(em.getCriteriaBuilder().equal(obtenido.get(column.get(i)), valor.get(i)));
            }
            List<T> resultado = em.createQuery(criteriaQuery).getResultList();
            if (resultado.size() > 0) {
                return resultado.get(0);
            } else {
                return null;
            }
        } catch (Exception ex) {
            em.close();
            throw ex;
        } finally {
            em.close();
        }
    }
}