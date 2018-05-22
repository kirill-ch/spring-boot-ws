package com.yourdomain.resteasy;

import com.yourdomain.resteasy.client.UserResourceV1;
import com.yourdomain.resteasy.entities.OurUser;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.BadRequestException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ResteasyIntegrationTests {

    @Autowired
    private UserResourceV1 userResourceV1;

    @Test
    public void shouldInsertUser() {

        OurUser user = new OurUser();
        user.setId(1000);
        user.setName("Anna");
        user.setEmail("anna@gmail.com");

        OurUser userSaved = userResourceV1.insertUser(user);

        Assertions.assertThat(userSaved).isEqualToComparingFieldByField(user);

        userResourceV1.deleteAllUsers();

    }

    //test proper validation of User Email
    @Test
    public void shouldThrowExceptionByInsertUserEmail() {

        OurUser user = new OurUser();
        user.setId(1000);
        user.setName("Anna");
        user.setEmail("annagmail.com");

        Assertions.assertThatThrownBy(() -> userResourceV1.insertUser(user)).isInstanceOf(BadRequestException.class);

    }

    //test proper validation of User Id
    @Test
    public void shouldThrowExceptionByInsertUserId() {

        OurUser user = new OurUser();
        user.setId(10);
        user.setName("Anna");
        user.setEmail("anna@gmail.com");

        Assertions.assertThatThrownBy(() -> userResourceV1.insertUser(user)).isInstanceOf(BadRequestException.class);

    }

    @Test
    public void shouldGetAllUsers() {

        OurUser user = new OurUser();
        user.setId(100);
        user.setName("Anna");
        user.setEmail("anna@gmail.com");

        userResourceV1.insertUser(user);

        List<OurUser> users = userResourceV1.getUsers();

        Assertions.assertThat(users).hasSize(1);
        Assertions.assertThat(users.get(0).getId()).isEqualTo(100);
        Assertions.assertThat(users.get(0).getName()).isEqualTo("Anna");
        Assertions.assertThat(users.get(0).getEmail()).isEqualTo("anna@gmail.com");

        OurUser user2 = new OurUser();
        user2.setId(100);
        user2.setName("Anna");

        Assertions.assertThat(users.get(0)).isEqualToIgnoringGivenFields(user2, "email");

        Assertions.assertThat(users.get(0).getId()).isNotNull();
        Assertions.assertThat(users.get(0).getId()).isInstanceOf(Integer.class);

        userResourceV1.deleteAllUsers();

    }

    @Test
    public void shouldGetUser() {

        int id = 100;

        OurUser user = new OurUser();
        user.setId(id);

        OurUser user1 = userResourceV1.getUser(id);
        Assertions.assertThat(user1).isEqualToComparingFieldByField(user);
    }

    @Test
    public void shouldInsertAndExtract(){

        OurUser user1 = new OurUser();
        user1.setId(100);
        user1.setName("Olga");

        OurUser user2 = new OurUser();
        user2.setId(1000);
        user2.setName("Andrey");

        userResourceV1.insertUser(user1);
        userResourceV1.insertUser(user2);

        List<OurUser> users = userResourceV1.getUsers();

        Assertions.assertThat(users).extracting("id").contains(user1.getId());
        Assertions.assertThat(users).extracting("id").contains(user2.getId());

        userResourceV1.deleteAllUsers();

    }

    @Test
    public void shouldDeleteAllUsers(){
        OurUser user1 = new OurUser();
        user1.setId(100);
        user1.setName("Olga");

        userResourceV1.insertUser(user1);

        userResourceV1.deleteAllUsers();

        List<OurUser> users = userResourceV1.getUsers();

        Assertions.assertThat(users).hasSize(0);
    }

}
