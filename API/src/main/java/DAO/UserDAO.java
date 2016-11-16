package main.java.DAO;

import main.java.Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAO {
        boolean addEntity(User user);
        User getEntityByMail(String mail);
        List getEntityList();
        boolean deleteEntity(String mail);
        boolean authEntity(String username, String password);
}
