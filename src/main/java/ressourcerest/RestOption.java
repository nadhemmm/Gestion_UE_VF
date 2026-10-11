package ressourcerest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class RestOption {
    private OptionBusiness optB = new OptionBusiness();

    // GET /options  and  GET /options?domaine=Mathématiques
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> l;
        if (domaine != null && !domaine.isEmpty()) {
            l = optB.getOptionsByDomaine(domaine);
        } else {
            l = optB.getListeOptions();
        }
        return Response.status(200).entity(l).build();
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOption(@PathParam("code") int code) {
        Option o = optB.getOptionByCode(code);
        if (o == null) return Response.status(404).build();
        return Response.status(200).entity(o).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (optB.addOption(option)) {
            return Response.status(200).entity(option).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (optB.updateOption(code, option)) {
            return Response.status(200).entity(option).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optB.deleteOption(code)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }
}