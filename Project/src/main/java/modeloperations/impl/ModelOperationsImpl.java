package modeloperations.impl;

import model.Film;
import model.FilmType;
import model.Seance;
import model.Theater;
import model.user.User;
import modeloperations.DataManager;
import modeloperations.ModelOperations;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by niict on 18.12.2017.
 */
public class ModelOperationsImpl implements ModelOperations {
    @Inject
    private DataManager dataManager;


    public void createTheater(Theater theater) {
        dataManager.createTheater(theater);
    }

    public void createFilmType(FilmType filmType) {
        dataManager.createFilmType(filmType);
    }

    public void createFilm(Film film) {
        dataManager.createFilm(film);
    }

    public void createSeanceForTheater(Seance seance, Theater theater) {
        dataManager.createSeanceForTheater(seance, theater);
    }

    public void createUser(User user) {
        dataManager.createUser(user);
    }

    public Theater getTheater(long theaterId) {
        return dataManager.getTheater(theaterId);
    }

    public Collection<Theater> getAllTheaters() {
        return dataManager.getAllTheaters();
    }

    public Seance getSeance(long seanceId) {
        return dataManager.getSeance(seanceId);
    }

    public Collection<Seance> getAllSeances() {
        return dataManager.getAllSeances();
    }

    public User getUser(long userId) {
        return dataManager.getUser(userId);
    }

    public void updateTheater(Theater theater) {
        dataManager.updateTheater(theater);
    }
}

