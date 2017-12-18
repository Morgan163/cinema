package modeloperations;

import model.Film;
import model.FilmType;
import model.Seance;
import model.Theater;
import model.user.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by niict on 18.12.2017.
 */
public interface DataManager {
    void createTheater(Theater theater);
    void createFilmType(FilmType filmType);
    void createFilm(Film film);
    void createSeanceForTheater(Seance seance, Theater theater);
    void createUser(User user);
    Theater getTheater(long theaterId);
    Collection<Theater> getAllTheaters();
    Seance getSeance(long seanceId);
    Collection<Seance> getAllSeances();
    User getUser(long userId);
    void updateTheater(Theater theater);
}
