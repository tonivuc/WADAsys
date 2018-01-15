package server.services;

import server.controllers.HusholdningController;
<<<<<<< HEAD
=======
import server.restklasser.Husholdning;
>>>>>>> bab936c52d782dccd174b9ab5c7fcdb985abe5cd

import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
/**
 * <h1>Service for de fleste funksjoner innad i programmet</h1>
 * Gateway til å hente og legge inn all data i databasen.
 *
 * @author  Toni Vucic
 * @version 1.0
 * @since   10.01.2018
 */

@Path("/hhservice")
public class HusholdningsService {
    HusholdningController hHController = new HusholdningController();

    /**
     * Legger inn en ny husholdning og returnerer dens ID
     * @param navn Husholdningens nye navn
     * @return int Husholdningens ID i databasen og til bruk på nettsiden.
     */
    @POST
    @Path("/husholdning/{navn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public int lagreNyHusholdning(@PathParam("navn") String navn) {
<<<<<<< HEAD
        return HusholdningController.ny(navn);
=======
        //Sende husholdningsnavn til database
        //for å lagre ny Husholdning. SQL lager ny ID, vi returnerer den
        int husholdningId = 1337;
        return husholdningId;
>>>>>>> bab936c52d782dccd174b9ab5c7fcdb985abe5cd
    }


    /**
     * Endrer navnet på en husholdning. Sender nytt navn til databasen vha. IDen.
     * @param id Husholdningens ID
     * @param navn Husholdningens nye navn
     * @return boolean True hvis det lykkes, false hvis det ikke lykkes.
     */
    @PUT
    @Path("/husholdning/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean endreHusholdningsNavn(@PathParam("id") int id, String navn) {
        //Sende husholdningsnavn til database
        //for å endre navn på husholdning. SQL lager ny ID, vi returnerer den
        return false;
    }

    /**
     * Brukes for å slette en husholdning fra databasen.
     * @param id Husholdningens ID
     * @return boolean True hvis det lykkes, false hvis det ikke lykkes.
     */
    @DELETE
    @Path("/husholdning/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean slettHusholdning(@PathParam("id") int id) {
        return false;
    }

    @GET
    @Path("/{epost}/husholdningData")
    @Produces(MediaType.APPLICATION_JSON)
    public Husholdning getHhData(@PathParam("epost") String epost){
        return hHController.getHusholdningData(epost);
    }
}
