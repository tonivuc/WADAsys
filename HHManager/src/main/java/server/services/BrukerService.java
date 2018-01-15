package server.services;
//import server.controllers.BrukerController;
import server.controllers.BrukerController;
import server.restklasser.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BrageHalse on 10.01.2018.
 */
@Path("/BrukerService")
public class BrukerService {

    BrukerController bc = new BrukerController();

    /** Henter en Bruker frå klienten, sjekker om eposten er i bruk om den ikkje er i bruk blir det registrert en ny bruker
     * i databasen og returnerer True, dersom eposten allerede er i bruk vil det bli returnert False
     * @param nyBruker ny brukerinformasjon
     * @return boolean True om det blir laget en ny bruker, False om eposten allerede er i bruk
     */

    @POST
    @Path("/registrer")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean registrerBruker(Bruker nyBruker){
        return bc.registrerBruker(nyBruker);
    }

    /*
    /** Sjekker om passordet er rett mot det i databasen, dersom det er rett vil det bli returnert True,

        return bc.registrerBruker(nyBruker);
    }

    /* Sjekker om passordet er rett mot det i databasen, dersom det er rett vil det bli returnert True,

     * dersom passordet er feil blir det returnert False
     *
     *@param epost eposten som blir skriven inn av bruker
     *@param hashPass en hash av passordet brukeren skirver inn
     *@return boolean som er true om passordet stemmer, ellers feil
     */


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean loginGodkjent(Bruker bruker){
        //må ha en plass der en finne ut om d e rett
        return bc.loginOk(bruker.getEpost(), bruker.getPassord());
    }
    /*
    @PUT
    @Path("/{brukerId}/endrePassord")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean setNewPassword(@PathParam("brukerId") String brukerId, String gammeltPassord, String nyttPassord){
        // sjekker om det gamle paassordet er likt det som er lagret i databasen, dersom det er likt skal det gamle
        // passordet i databasen bli erstattet med det nye og returnere true
        return false;
    }
    */

    /**
     * Endrer favhusholdning i Databasen til brukerIden som er gitt
     * @param brukerId
     * @param favHusholdningsId
     */
    @PUT
    @Path("/{brukerId}/favHusholdning")
    @Consumes(MediaType.TEXT_PLAIN)
    public void setFavHusholdning(@PathParam("brukerId") String brukerId, String favHusholdningsId){
        // sett favHusholdningsId som favHusholdning i DataBasen der brukerId-en i DB er lik brukerId
    }

    /**
     * Endrer Eposten i DataBasen til brukeren med gitt brukerId dersom eposten er
     * @param nyEpos
     * @return
     */
    @PUT
    @Path("/{brukerId}/endreEpost")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean endreEpost(@PathParam("brukerId") String brukerId ,String nyEpos){
        // sjekk om den nye Epostadressa innholder @ , . , com/no. Dersom epostadressen er gyldig skal Epostadressen i
        // Databasen der brukerIden er lik den som gitt i parameteret
        return false;
    }


    @GET
    @Path("/{epost}/hhData")
    @Produces(MediaType.APPLICATION_JSON)
    public Bruker getHhData(@PathParam("epost") String brukerEpost){
        return bc.getBrukerData(brukerEpost);
    }
}
