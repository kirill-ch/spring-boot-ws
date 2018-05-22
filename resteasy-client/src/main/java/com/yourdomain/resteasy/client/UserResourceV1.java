package com.yourdomain.resteasy.client;

import com.yourdomain.resteasy.entities.OurUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface UserResourceV1 {

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    List<OurUser> getUsers();

    @POST
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    OurUser insertUser(OurUser user);

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    OurUser getUser(@PathParam("userId") Integer userUID);

    @GET
    @Path("filter")
    @Produces(MediaType.TEXT_PLAIN)
    String getUser(@QueryParam("userId") Integer userUID, @QueryParam("gender") String gender) ;

    @DELETE
    @Path("deleteall")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteAllUsers();

}
