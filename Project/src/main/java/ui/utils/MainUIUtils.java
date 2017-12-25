package ui.utils;

import model.AgeLimitType;
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

    public Map<Film, Collection<Seance>> selectFilmSeanceByContext(Map<Film, Collection<Seance>> filmSeancesMap, FilterContext filterContext){
        Map<Film, Collection<Seance>> filteredFilmSeancesMap = new HashMap<>();
        filteredFilmSeancesMap.putAll(filmSeancesMap);
        if(filterContext.getFilterParameter(FilterContext.FILM_TYPE_PARAMETER) != null){
            FilmType targetType = (FilmType) filterContext.getFilterParameter(FilterContext.FILM_TYPE_PARAMETER);
            filteredFilmSeancesMap = selectFilmSeanceByFilmType(filteredFilmSeancesMap, targetType);
        }
        if(filterContext.getFilterParameter(FilterContext.AGE_LIMIT_PARAMETER) != null){
            AgeLimitType targetLimit = (AgeLimitType) filterContext.getFilterParameter(FilterContext.AGE_LIMIT_PARAMETER);
            filteredFilmSeancesMap = selectFilmSeanceByAgeLimit(filteredFilmSeancesMap, targetLimit);
        }
        if(filterContext.getFilterParameter(FilterContext.TIME_RANGE_PARAMETER) != null){
            CalendarPair timeRange = (CalendarPair) filterContext.getFilterParameter(FilterContext.TIME_RANGE_PARAMETER);
            filteredFilmSeancesMap = selectFilmSeanceByTymeRange(filteredFilmSeancesMap, timeRange);
        }
        return filteredFilmSeancesMap;
    }

    private Map<Film, Collection<Seance>> selectFilmSeanceByTymeRange(Map<Film, Collection<Seance>> filmSeancesMap, CalendarPair timeRange) {
        Map<Film, Collection<Seance>> filteredFilmSeancesMap = new HashMap<>();
        for (Film film : filmSeancesMap.keySet()){
            Collection<Seance> filteredSeances = new ArrayList<>();
            for (Seance seance : filmSeancesMap.get(film)){
                Calendar seanceStartDate = seance.getSeanceStartDate();
                if (seanceStartDate.get(Calendar.HOUR_OF_DAY)>timeRange.leftCalendar.get(Calendar.HOUR_OF_DAY)
                        && seanceStartDate.get(Calendar.HOUR_OF_DAY) < timeRange.rightCalendar.get(Calendar.HOUR_OF_DAY)) {
                    filteredSeances.add(seance);
                }
            }
            if (filteredSeances.size() > 0){
                filteredFilmSeancesMap.put(film, filteredSeances);
            }
        }
        return filteredFilmSeancesMap;
    }

    private Map<Film, Collection<Seance>> selectFilmSeanceByAgeLimit(Map<Film, Collection<Seance>> filmSeancesMap, AgeLimitType targetLimit) {
        Map<Film, Collection<Seance>> filteredFilmSeancesMap = new HashMap<>();
        for (Film film : filmSeancesMap.keySet()){
            if (film.getAgeLimitType().equals(targetLimit)){
                filteredFilmSeancesMap.put(film, filmSeancesMap.get(film));
            }
        }
        return filteredFilmSeancesMap;
    }

    private Map<Film, Collection<Seance>> selectFilmSeanceByFilmType(Map<Film, Collection<Seance>> filmSeancesMap, FilmType targetType) {
        Map<Film, Collection<Seance>> filteredFilmSeancesMap = new HashMap<>();
        for (Film film : filmSeancesMap.keySet()){
            if (film.getFilmType().equals(targetType)){
                filteredFilmSeancesMap.put(film, filmSeancesMap.get(film));
            }
        }
        return filteredFilmSeancesMap;
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
        calendarPairs.add(new CalendarPair(calendar_19hours, calendar_22hours));
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
