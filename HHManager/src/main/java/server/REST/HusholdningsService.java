package server.REST;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;

@Path("/HHService")
public class HusholdningsService {

    @POST
    @Path("/husholdning/{navn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public int lagreNyHusholdning(@PathParam("navn") String navn) {
        //Sende husholdningsnavn til database
        //for å lagre ny Husholdning. SQL lager ny ID, vi returnerer den
        int husholdningsId = 1337;
        return husholdningsId;
    }

    @PUT
    @Path("/husholdning/{navn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public int endreHusholdningsNavn(@PathParam("navn") String navn) {
        //Sende husholdningsnavn til database
        //for å endre navn på husholdning. SQL lager ny ID, vi returnerer den
        int husholdningsId = 1337;
        return husholdningsId;
    }
/*
    @DELETE
    @Path("/husholdning/{navn}")

*/

}
