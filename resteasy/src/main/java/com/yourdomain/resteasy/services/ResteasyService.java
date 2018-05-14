package com.yourdomain.resteasy.services;

import com.yourdomain.resteasy.daos.ResteasyDao;
import com.yourdomain.resteasy.entities.OurUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ResteasyService {

    private ResteasyDao dao;

    @Autowired
    public ResteasyService(ResteasyDao dao) {
        this.dao = dao;
    }

    public List<OurUser> getAllUsers() {
        return dao.getUsers();
    }

    public OurUser insertUser(@Valid OurUser user) throws Exception{
        return dao.insertUser(user);
    }
}
