package Controller;

import Model.User;
import Service.APIService;
import Util.Util;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by amaia.nazabal on 11/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
    private UserController userController = new UserController();
    private static User user;

    @BeforeClass
    public static void init(){
        APIService.persistance();
        user = new User();
        user.setPseudo("test");
        user.setHashkey("password-test");
        user.setMail("test-test@test.fr");
    }


    @Test
    public void addTest(){
        userController.init();
        Exception exception = null;
        ResponseEntity<User> responseEntity = null;
        try{
            responseEntity = userController
                    .add(user.getPseudo(), user.getMail(), user.getHashkey());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
    }

    @Test
    public void getTest(){
        userController.init();
        Exception exception = null;
        ResponseEntity<User> responseEntity = null;
        try{
            responseEntity = userController
                    .get(user.getMail());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
    }

    @Test
    public void getAllTest(){
        List<User> userList;
        User usr;
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;

        userController.init();

        try{
            //responseEntity = userController.getAll();
        }catch (Exception e){
            exception = e;
        }
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(responseEntity.getBody().toString());

        Util<User> util = new Util();
        List<User> listUsers = util.convertToObjectJSON(responseEntity.getBody().toString());
        List<User> list = mapper.convertValue(listUsers,
                new TypeReference<List<User>>() { });

        /* java.util.LinkedHashMap cannot be cast to Model.User*/
        usr = list.get(0);
        usr = list.stream().filter(u -> u.getId().equals(user.getId()))
                .findFirst().get();

        assertEquals(user.getId(), usr.getId());
        assertEquals(user.getMail(), usr.getMail());
        assertEquals(user.getPseudo(), usr.getPseudo());
        assertEquals(user.getHashkey(), usr.getHashkey());

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
    }

    @AfterClass
    public static void close(){
        APIService.close();
    }

}