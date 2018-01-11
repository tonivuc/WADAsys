package server;

import server.database.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Hallvard on 08.01.2018.
 */
@Path("/brukere/")
public class Rest {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<ArrayList<String>> getBrukere() {
        System.out.println("Getter");
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        try (Result result = DatabaseConnection.fetchData("Select * from bruker", new String[0])) {
            ResultSet resultSet = result.getResultSet();
            int ant = resultSet.getRow();
            for (int i = 0; i < ant; i++) {
                System.out.println(resultSet.next());
                arrayList.add((java.util.ArrayList) resultSet.getArray(i));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("forste element");
        arrayList.add(arrayList2);
        return arrayList;
    }
}

class Test {
    public static void main(String[] args) {
        try(Result result = DatabaseConnection.fetchData("Select * from bruker", new String[0])){
            ResultSet resultSet = result.getResultSet();
            System.out.println(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}