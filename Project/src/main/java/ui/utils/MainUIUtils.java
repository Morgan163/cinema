package ui.utils;

import model.Film;
import model.FilmType;
import model.Seance;

import java.util.*;

/**
 * Created by niict on 24.12.2017.
 */
public class MainUIUtils {

    public MainUIUtils(){

    }

    public Map<Film,Collection<Seance>> groupSeancesByFilms(Collection<Seance> seances) {
        Map<Film, Collection<Seance>> resultMap = new HashMap<>();
        Set<Film> films = getAllFilmsFromSeances(seances);
        for (Film film : films){
            Collection<Seance> seancesByFilm = selectAllSeancesByFilm(seances, film);
            resultMap.put(film, seancesByFilm);
        }
        return resultMap;
    }

    private Set<Film> getAllFilmsFromSeances(Collection<Seance> seances){
        Set<Film> films = new HashSet<>();
        for (Seance seance : seances){
            films.add(seance.getFilm());
        }
        return films;
    }

    private Collection<Seance> selectAllSeancesByFilm(Collection<Seance> seances, Film film){
        List<Seance> seancesByFilm = new ArrayList<>();
        for (Seance seance : seances){
            if (seance.getFilm().equals(film)){
                seancesByFilm.add(seance);
            }
        }
        return seancesByFilm;
    }

    public Set<FilmType> selectAllGenresFromFilms(Set<Film> films) {
        Set<FilmType> filmTypes = new HashSet<>();
        for (Film film : films){
            filmTypes.add(film.getFilmType());
        }
        return filmTypes;
    }

    public Map<Film, Collection<Seance>> selectFilmSeanceByContext(FilterContext filmType){
        return new HashMap<>();
    }

    public Collection<CalendarPair> buildCalendarPairs() {
        Collection<CalendarPair> calendarPairs = new ArrayList<>();
        Calendar calendar_0hours = Calendar.getInstance();
        Calendar calendar_4hours = Calendar.getInstance();
        Calendar calendar_10hours = Calendar.getInstance();
        Calendar calendar_13hours = Calendar.getInstance();
        Calendar calendar_16hours = Calendar.getInstance();
        Calendar calendar_19hours = Calendar.getInstance();
        Calendar calendar_22hours = Calendar.getInstance();
        calendar_0hours.set(Calendar.HOUR_OF_DAY, 0);
        calendar_4hours.set(Calendar.HOUR_OF_DAY, 4);
        calendar_10hours.set(Calendar.HOUR_OF_DAY, 10);
        calendar_13hours.set(Calendar.HOUR_OF_DAY, 13);
        calendar_16hours.set(Calendar.HOUR_OF_DAY, 16);
        calendar_19hours.set(Calendar.HOUR_OF_DAY, 19);
        calendar_22hours.set(Calendar.HOUR_OF_DAY, 22);
        calendarPairs.add(new CalendarPair(calendar_0hours, calendar_4hours));
        calendarPairs.add(new CalendarPair(calendar_10hours, calendar_13hours));
        calendarPairs.add(new CalendarPair(calendar_13hours, calendar_16hours));
        calendarPairs.add(new CalendarPair(calendar_16hours, calendar_19hours));
        calendarPairs.add(new CalendarPair(calendar_22hours, calendar_0hours));
        return calendarPairs;

    }

    public CalendarPair getEmptyPair(){
        return new CalendarPair();
    }

    public class CalendarPair{
        private Calendar leftCalendar;
        private Calendar rightCalendar;

        public CalendarPair(){

        }

        public CalendarPair(Calendar leftCalendar, Calendar rightCalendar) {
            this.leftCalendar = leftCalendar;
            this.rightCalendar = rightCalendar;
        }

        public Calendar getLeftCalendar() {
            return leftCalendar;
        }

        public Calendar getRightCalendar() {
            return rightCalendar;
        }
    }
}
