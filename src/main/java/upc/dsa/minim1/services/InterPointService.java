package upc.dsa.minim1.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import upc.dsa.minim1.UserImplementation;
import upc.dsa.minim1.UserManager;
import upc.dsa.minim1.exceptions.InterestPointNotFoundException;
import upc.dsa.minim1.exceptions.UserNotFoundException;
import upc.dsa.minim1.models.ElemType;
import upc.dsa.minim1.models.InterPoint;
import upc.dsa.minim1.models.User;
import upc.dsa.minim1.models.UserPasses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/InterPoints", description = "Endpoint to Interest Points Service")
@Path("/IPoints")
public class InterPointService {
    private UserManager tm;
    public InterPointService() throws UserNotFoundException, InterestPointNotFoundException {
        this.tm = UserImplementation.getInstance();
        if (tm.Psize()==0 ) {
            this.tm.addIPoint(15,15, ElemType.TREE);
            this.tm.addIPoint(10,10, ElemType.WALL);

            this.tm.addUPP("1",15,15);
            this.tm.addUPP("2",15,15);
            this.tm.addUPP("3",10,10);
        }
    }


    @GET
    @ApiOperation(value = "get a list of interest points have been through by a user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = InterPoint.class),
            @ApiResponse(code = 404, message = "Interest Point not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIPs(@PathParam("id") String id) {
        List<InterPoint> t = this.tm.UpassIP(id);
        if (t.isEmpty()) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @GET
    @ApiOperation(value = "get a list of users  have been through a Interest Point", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = InterPoint.class),
            @ApiResponse(code = 404, message = "Interest Point not found")
    })
    @Path("/{x},{y},{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@PathParam("x") int x,@PathParam("y") int y,@PathParam("type") ElemType type) {
        List<User> t = this.tm.UssersIP(x,y,type);
        if (t.isEmpty()) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Interest Point", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{x}{y}")
    public Response deleteIP(@PathParam("x") int x, @PathParam("y") int y) {
        InterPoint t = this.tm.getIP(x,y);
        if (t == null) return Response.status(404).build();
        else this.tm.deleteIPoint(x,y);
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "create a new InterPoint", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = InterPoint.class),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 415, message = "Unsupported Media Type"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInterPoint(InterPoint interPoint) {
        try {
            InterPoint newInterPoint = this.tm.addIPoint(interPoint);
            return Response.status(201).entity(newInterPoint).build();
        } catch (Exception e) {
            return Response.status(500).entity("Internal Server Error").build();
        }
    }

    @POST
    @ApiOperation(value = "Registrar paso de usuario por un InterPoint", notes = "Registro de paso")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = UserPasses.class),
            @ApiResponse(code = 404, message = "User or InterPoint not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/pass")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserPass(UserPasses userPass) {
        try {
            UserPasses newUserPass = this.tm.addUPP(userPass);
            return Response.status(201).entity(newUserPass).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity("User not found").build();
        } catch (InterestPointNotFoundException e) {
            return Response.status(404).entity("InterPoint not found").build();
        } catch (Exception e) {
            return Response.status(500).entity("Internal Server Error").build();
        }
    }


}
