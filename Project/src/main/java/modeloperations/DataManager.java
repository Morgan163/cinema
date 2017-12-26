package modeloperations;

import exceptions.DependentObjectExistsException;
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
    Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersBySeat(Seat seat);
    Collection<Theater> getAllTheaters();
    Collection<Seance> getAllSeances();
    Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersByKey(String code);
    Collection<Film> getAllFilms();
    Collection<FilmType> getAllFilmTypes();
    Collection<User> getAllOperators();
    void updateTheater(Theater theater);
    void updateSeatSeanceMapper(SeatSeanceStatusMapper mapper);
    void updateSeance(Seance seance);
    void updateFilm(Film film);
    void updateFilmType(FilmType filmType);
    void updateUser(User user);
    void removeTheater(Theater theater) throws DependentObjectExistsException;
    void removeSeatSeanceMapper(SeatSeanceStatusMapper mapper);
    void removeSeance(Seance seance) throws DependentObjectExistsException;
    void removeFilm(Film film) throws DependentObjectExistsException;
    void removeFilmType(FilmType filmType) throws DependentObjectExistsException;
    void removeUser(User user) throws DependentObjectExistsException;
}
