package com.yourdomain.resteasy.daos;

import com.yourdomain.resteasy.entities.OurUser;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ResteasyDao {

    private List<OurUser> userList = new ArrayList<>();

    public List<OurUser> getUsers() {
        return userList;
    }

    public OurUser insertUser(OurUser user){
        //validate conditions - user==null
        Set<ConstraintViolation<OurUser>> violations = new HashSet<>();

        if(user==null){
            throw new ConstraintViolationException("User is null", violations);
        }

        //identify objects - users
        for(OurUser userSaved: userList){
            if(userSaved.getId()==user.getId()){
                throw new ConstraintViolationException("User with id = " + user.getId() + " already exists", violations);
            }
        }


        userList.add(user);
        return user;
    }

    public void deleteAllUsers() {
        userList.clear();
    }
}
