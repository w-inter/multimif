package com.multimif.dao;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class permettant de créer la persistence
 */
class DAO {
    private static final Logger LOGGER = Logger.getLogger(DAO.class.getName());
    private EntityManager em;

    /**
     * Cette méthode retourne une nouvelle instance de l'entity manager s'il n'y a pas
     * aucun déjà ouvert, sinon retourne la même
     *
     * @return entity manager
     */
    EntityManager getEntityManager(){
        if (em == null || !em.isOpen()){
            em = EntityFactoryManager.getEntityManagerFactory()
                    .createEntityManager();
        }
        return em;
    }

    /**
     * Cette méthode ferme l'entite manager.
     * Pour convention on ouvre et ferme l'entity manager dans méthode du DAO.
     */
    void closeEntityManager(){
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }catch (Exception e){
            LOGGER.log(Level.OFF, e.getMessage(), e);
        }
    }
}
