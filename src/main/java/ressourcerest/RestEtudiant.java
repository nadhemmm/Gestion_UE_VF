package ressourcerest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
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
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
public class RestEtudiant {
    private EtudiantBusiness etB = new EtudiantBusiness();
    private OptionBusiness optB = new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(200).entity(etB.getAllEtudiants()).build();
    }

    // GET /etudiants/option?codeOption=1  -> XML
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optB.getOptionByCode(codeOption);
        if (option == null) return Response.status(404).build();

        // copies without the option, to match the XML sample in the assignment
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant e : etB.getEtudiantsByOption(option)) {
            result.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(),
                    null, e.getAnneeEtude(), e.getEmail()));
        }
        return Response.status(200).entity(new EtudiantList(result)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        Etudiant e = etB.getEtudiantByIdentifiant(id);
        if (e == null) return Response.status(404).build();
        return Response.status(200).entity(e).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Etudiant e) {
        if (etB.addEtudiant(e)) {
            return Response.status(200).entity(e).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, Etudiant e) {
        if (etB.updateEtudiant(id, e)) {
            return Response.status(200).entity(e).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        if (etB.deleteEtudiant(id)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }
}