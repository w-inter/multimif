package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.util.DataException;
import com.multimif.util.TestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 *
 * Classe de test de TempoyraryFileDAO.
 *
 * On a appliqué de test pour vérifier:
 * <ul>
 *  <li>L'absence des exceptions</li>
 *  <li>L'action qui a fait la classe DAO.</li>
 *  <li>La cohérence des données </li>
 * </ul>
 *
 * Ces test sont fait contre la base de données.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/17/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserGrantDAOTest extends TestUtil{
    private UserGrantDAO userGrantDAO = new UserGrantDAOImpl();
    private ProjectDAO projectDAO = new ProjectDAOImpl();
    private UserDAO userDAO = new UserDAOImp();
    private static UserGrant userGrant1 = new UserGrant();
    private static UserGrant userGrant2 = new UserGrant();

    @Test
    public void addEntity(){
        Exception exception = null;
        try {
            newAdmin();
            userDAO.addEntity(admin);

            newDeveloper();
            userDAO.addEntity(developer);

            newProject();
            projectDAO.addEntity(project);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);

        /* On ajout un admin */

        userGrant1.setProject(project);
        userGrant1.setUser(admin);
        userGrant1.setPermissionType(UserGrant.PermissionType.ADMIN);

        try {
            userGrantDAO.addEntity(userGrant1);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(userGrant1);

        /* On ajout un developper */

        userGrant2.setProject(project);
        userGrant2.setUser(developer);
        userGrant2.setPermissionType(UserGrant.PermissionType.DEVELOPER);

        try {
            userGrantDAO.addEntity(userGrant2);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(userGrant2);
    }

    @Test
    public void getProjectsByEntity () {
        List<UserGrant> permissionsList = new ArrayList();
        UserGrant permission = null;
        Exception exception = null;

        try {
            permissionsList = userGrantDAO.getProjectsByEntity(admin);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertTrue(permissionsList.size() > 0);

        try {
            permission = permissionsList.stream().filter(p -> p.getProjectId().equals(project.getIdProject()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(permission);
        assertEquals(permission.getUserId(), admin.getIdUser());
        assertEquals(permission.getProjectId(), project.getIdProject());
    }

    @Test
    public void getDevelopersByEntity() {
        List<UserGrant> permissionsList = new ArrayList();
        UserGrant permission = null;
        Exception exception = null;

        try{
            permissionsList = userGrantDAO.getDevelopersByEntity(project.getIdProject());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(permissionsList.size() > 0);

        try {
            permission = permissionsList.stream().filter(p -> p.getUserId().equals(developer.getIdUser()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(permission);
        assertEquals(permission.getUserId(), developer.getIdUser());
        assertEquals(permission.getProjectId(), project.getIdProject());
    }

    @Test
    public void getAdminByEntity() {
        UserGrant permission = null;
        User adm;
        Exception exception = null;

        try{
            permission = userGrantDAO.getAdminByEntity(project.getIdProject());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);

        adm = permission.getUser();
        assertNotNull(adm);
        assertEquals(adm.getIdUser(), admin.getIdUser());
        assertEquals(adm.getUsername(), admin.getUsername());
        assertEquals(adm.getMail(), admin.getMail());

    }

    @Test
    public void getEntityById() {
        UserGrant permission = null;
        Exception exception = null;

        try {
            permission = userGrantDAO.getEntityById(admin.getIdUser(), project.getIdProject());
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(permission);

        assertEquals(permission.getProjectId(), userGrant1.getProjectId());
        assertEquals(permission.getUserId(), userGrant1.getUserId());
    }

    @Test
    public void suppressEntity() {
        Exception exception = null;
        UserGrant permission1 = null;
        UserGrant permission2 = null;
        try{
            userGrantDAO.deleteEntity(userGrant1);
            userGrantDAO.deleteEntity(userGrant2);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);

        try {
            permission1 = userGrantDAO.getEntityById(userGrant1.getUserId(), userGrant1.getProjectId());
            permission2 = userGrantDAO.getEntityById(userGrant2.getUserId(), userGrant2.getProjectId());
        } catch (DataException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNull(permission1);
        assertNull(permission2);

        exception = null;

        try {
            deleteProject();
            deleteUser();
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }

    private void deleteUser() throws DataException {
        userDAO.deleteEntity(admin);
        userDAO.deleteEntity(developer);
    }

    private void deleteProject() throws DataException {
        projectDAO.deleteEntity(project);
    }
}
