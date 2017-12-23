package specifications.sql.impl;

import db.DataBaseNames;
import model.Film;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class FilmByIdSqlSpecification implements SqlSpecification<Film>{
    private long filmId;
    private String tableName;

    public FilmByIdSqlSpecification(long filmId) {
        this.filmId = filmId;
        tableName = DataBaseNames.FILMS;
    }

    @Override
    public boolean specified(Film source) {
        return filmId == source.getFilmID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.FILM_ID = %d", tableName, filmId);
    }
}
