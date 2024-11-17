package upc.dsa.minim1.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import upc.dsa.minim1.UserImplementation;
import upc.dsa.minim1.UserManager;
import upc.dsa.minim1.models.ElemType;
import upc.dsa.minim1.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Api(value = "/Users", description = "Endpoint to Users Service")
@Path("/Users")
public class UserService {

    private UserManager tm;

    public UserService() {
        this.tm = UserImplementation.getInstance();
        if (tm.Usize()==0) {
            this.tm.addUser("1","Toni","Oller Arcas","Toni.Oller@dsa.m1","1/1/2000");
            this.tm.addUser("2","Juan","Lopez Rubio","Juan.Lopez@dsa.m1","2/1/2000");
            this.tm.addUser("3","Sergio","Machado Sanchez","Sergio.Machado@dsa.m1","3/1/2000");
        }
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks() {

        List<User> tracks = this.tm.findAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(tracks) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrack(@PathParam("id") String id) {
        User t = this.tm.getUser(id);

        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    public Response deleteTrack(@PathParam("id") String id) {
        User t = this.tm.getUser(id);
        if (t == null) return Response.status(404).build();
        else this.tm.deleteUser(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/")
    public Response updateTrack(User track) {

        User t = this.tm.updateUser(track);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }


    @POST
    @ApiOperation(value = "create a new User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 400, message = "Validation Error: Todos los campos (name, sname, date, mail) son obligatorios.")

    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User track) {
        if (track.getName() == null || track.getName().isEmpty() ||
                track.getSname() == null || track.getSname().isEmpty() ||
                track.getDate() == null || track.getDate().isEmpty() ||
                track.getMail() == null || track.getMail().isEmpty()) {

            return Response.status(400)
                    .entity("Validation Error: Todos los campos (name, sname, date, mail) son obligatorios.")
                    .build();
        }

        this.tm.addUser(track);
        return Response.status(201).entity(track).build();
    }


}
