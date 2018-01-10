package server;

import server.database.DatabaseConnection;
import server.database.Result;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;
import java.util.Collection;

/**
 * Created by Hallvard on 08.01.2018.
 */
@Path("/brukere/")
public class Rest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getBrukere() {
        try (Result result = DatabaseConnection.fetchData("Select * from bruker", new String[0])) {
            ResultSet resultSet = result.getResultSet();
            while (resultSet.next()) {
                System.out.println(resultSet.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
