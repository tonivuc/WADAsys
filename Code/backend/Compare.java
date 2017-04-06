package backend;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Created by tvg-b on 03.04.2017.
 */
public class Compare {

    public Compare () {

    }

    public static Comparator<Athlete> NationalityComparator = new Comparator<Athlete>() {

        public int compare(Athlete athlete1, Athlete athlete2) {
            String athleteNation1 = athlete1.getNationality();
            String athleteNation2 = athlete2.getNationality();

            return athleteNation1.compareTo(athleteNation2);
        }
    };

    public static Comparator<Athlete> SportComparator = new Comparator<Athlete>() {

        public int compare(Athlete athlete1, Athlete athlete2) {

            String athleteSport1 = athlete1.getSport();
            String athleteSport2 = athlete2.getSport();

            return athleteSport1.compareTo(athleteSport2);
        }
    };

    public static Comparator<Athlete> FirstNameComparator = new Comparator<Athlete>() {

        public int compare(Athlete athlete1, Athlete athlete2) {

            String athletename1 = athlete1.getFirstname().toUpperCase();
            String athletename2 = athlete2.getFirstname().toUpperCase();

            //ascending order
            return athletename1.compareTo(athletename2);

            //descending order
            //return fruitName2.compareTo(fruitName1);
        }

    };

    public static Comparator<Athlete> HaemoglobinComparator = new Comparator<Athlete>() {

        public int compare(Athlete athlete1, Athlete athlete2) {

            double haemoglobinLevel1 = athlete1.getGlobinDeviation(LocalDate.now());
            double haemoglobinLevel2 = athlete2.getGlobinDeviation(LocalDate.now());

            if (haemoglobinLevel1 > haemoglobinLevel2)  {
                return 1;
            } else if (haemoglobinLevel1 == haemoglobinLevel2) {
                return 0;
            } else {
                return -1;
            }
        }
    };

}
