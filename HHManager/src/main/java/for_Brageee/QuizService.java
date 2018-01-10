package RESTfiler;

import quizspill.QuizSpill;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

//Liten kommentar:
//Vet vi ikke skal tenke så mye sikkerhet, men det er lett å jukse...
//To måter å ordne sjekking av svar på quiz.

//REMOTE:
//Serveren må vite hva hver eneste spiller valgte? Og gi poeng derretter?
//Kunne jo sendt den valgte indeksen lokalt til svar-array til serveren.
//Serveren kunne så oppdatert poengene til hver spiller på servern hvis de svarte riktig
//Returnert en array med SpillerPoeng-objekter.
//Så henter klienten ut sin egen poengsum

//LOKALT:
//Vi sjekker om valgt svarindeks matcher riktigSvarIndeks
//Hvis ja, poeng+10
//Send spillerens poengsum til serveren som en UPDATE.

//Velger dette siden det er enklere. Kan oppdateres senere.

@Path("/QuizService")
public class QuizService {

    @PUT
    @Path("/quiz/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public int leggInnPoeng(@PathParam("quizId") int quizId, Spiller spiller) {
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            if (QuizSpill.liveQuizes.get(i).getId() == quizId) {
                return QuizSpill.liveQuizes.get(i).oppdaterPoeng(spiller);
            }
        }
        return -1;
    }


    @GET
    @Path("/quiz")
    @Produces(MediaType.APPLICATION_JSON)
    public LiveQuiz[] getQuizzer() {
        //Produser tekstbeskjed
        LiveQuiz[] quizzer = new LiveQuiz[QuizSpill.liveQuizes.size()];
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            quizzer[i] = QuizSpill.liveQuizes.get(i);
        }
        return quizzer;
    }

    @GET
    @Path("/quiz/{quizId}/spillere")
    @Produces(MediaType.APPLICATION_JSON)
    public Spiller[] getSpillere(@PathParam("quizId") int id) {
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            if (QuizSpill.liveQuizes.get(i).getId() == id) {
                Spiller[] spillere = new Spiller[QuizSpill.liveQuizes.get(i).getSpillere().size()];
                spillere = QuizSpill.liveQuizes.get(i).getSpillere().toArray(spillere);
                return spillere;
            }
        }
        //Returnerer tom spiller-array
        return new Spiller[0];
    }

    @GET
    @Path("/quiz/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public LiveQuiz getQuiz(@PathParam("quizId") int quizId) {
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            if (QuizSpill.liveQuizes.get(i).getId() == quizId) {
                System.out.println("Returnerer quiz: "+ QuizSpill.liveQuizes.get(i).getTittel());
                return QuizSpill.liveQuizes.get(i);
            }
        }
        return null;
    }



    //Metoden oppdaterer quizzen på serveren så den vet hvilket spørsmål den er på
    @POST
    @Path("/sporsmaal")
    @Consumes(MediaType.APPLICATION_JSON)
    public void oppdaterSporsmaalNr(LiveQuiz liveQuizInn) {
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            if (liveQuizInn.getId() == QuizSpill.liveQuizes.get(i).getId()) {
                QuizSpill.liveQuizes.get(i).setSporsmaalNaa(liveQuizInn.getSporsmaalNaa());
            }
        }
    }

    @POST
    @Path("/quiz/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSpiller(@PathParam("quizId") int quizId, String kallenavn) {
        Spiller nySpiller = new Spiller();
        nySpiller.setKallenavn(kallenavn);
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            if (QuizSpill.liveQuizes.get(i).getId() == quizId) {
                QuizSpill.liveQuizes.get(i).addSpiller(nySpiller);
            }
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void leggInQuiz(LiveQuiz liveQuiz) {
        QuizSpill.addQuiz(liveQuiz);
    }

    @DELETE
    @Path("quiz/{quizId}")
    public int slettKunde(@PathParam("quizId") int quizId) {
        for (int i = 0; i < QuizSpill.liveQuizes.size(); i++) {
            if (QuizSpill.liveQuizes.get(i).getId() == quizId) {
                QuizSpill.liveQuizes.remove(i);
                return i;
            }
        }
        return -1;
    }

    @GET
    @Path("/ferdigquiz")
    @Produces(MediaType.APPLICATION_JSON)
    public FerdigQuiz[] getFerdigQuizer() {
        return QuizSpill.ferdigQuizes.toArray(new FerdigQuiz[0]);
    }

    @POST
    @Path("/ferdigquiz/{quizId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LiveQuiz startFerdigQuiz(@PathParam("quizId") int quizId, StartTid startTid) {
        LiveQuiz quiz = QuizSpill.startFerdigQuiz(quizId, startTid.getStartTid());
        if (quiz == null) {
            throw new NotFoundException();
        }
        return quiz;
    }

}
