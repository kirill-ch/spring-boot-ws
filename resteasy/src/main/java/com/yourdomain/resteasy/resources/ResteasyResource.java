package com.yourdomain.resteasy.resources;

import com.yourdomain.resteasy.entities.ErrorMessage;
import com.yourdomain.resteasy.entities.OurUser;
import com.yourdomain.resteasy.services.ResteasyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/api/v1")
public class ResteasyResource {

    private ResteasyService resteasyService;

    @Autowired
    public ResteasyResource(ResteasyService resteasyService) {
        this.resteasyService = resteasyService;
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OurUser> getUsers() {
        return resteasyService.getAllUsers();
    }

    @POST
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertUser(OurUser user) {
//        Objects.requireNonNull(user.getName(),"Require name not to be null");
//        Objects.requireNonNull(user.getEmail(),"Require email not to be null");
        try {
            resteasyService.insertUser(user);
            return Response.ok(user).build();
        } catch (ConstraintViolationException e) {
            ErrorMessage errorMessage;
            if(e.getMessage()==null){
                errorMessage = new ErrorMessage("User not valid");
            }else {
                errorMessage = new ErrorMessage(e.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage("Internal error")).build();
        }
    }

    //url:port/api/v1/11232134
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public OurUser getUser(@PathParam("userId") Integer userUID) {
        OurUser user = new OurUser();
        user.setId(userUID);

        return user;
    }

    //url:port/api/v1/filter?userId=11232134&gender=MALE
    @GET
    @Path("filter")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@QueryParam("userId") Integer userUID, @QueryParam("gender") String gender) {
        return "userId = " + userUID + "\nGender: " + gender;
    }

}

