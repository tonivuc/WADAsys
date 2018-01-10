package server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Hallvard on 08.01.2018.
 */
@Path("/brukere/")
public class Rest {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getSpillere() {
        return null;
    }
}
