package modeloperations;

import model.*;
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
    Seance getSeance(long seanceId);
    SeatSeanceStatusMapper getSeatSeanceStatusMapper(Seat seat, Seance seance);
    Theater getTheaterBySeance(Seance seance);
    User getUser(long userId);
    Theater getTheaterByLine(Line lineForSeat);
    User getUserByLoginAndPassword(String login, String password);
    Line getLineBySeat(Seat seat);
    Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersBySeance(Seance seance);
    Collection<Theater> getAllTheaters();
    Collection<Seance> getAllSeances();
    Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersByKey(String code);
    Collection<Film> getAllFilms();
    Collection<FilmType> getAllFilmTypes();
    Collection<User> getAllOperators();
    void updateTheater(Theater theater);
    void updateSeatSeanceMapper(SeatSeanceStatusMapper mapper);
    void updateSeance(Seance seance);
}
