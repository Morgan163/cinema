package specifications.sql.impl;

import db.DataBaseNames;
import model.Film;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 25.12.2017.
 */
public class FilmByTypeIdSqlSpecification implements SqlSpecification<Film> {
    private long filmTypeId;
    private String tableName;

    public FilmByTypeIdSqlSpecification(long filmTypeId) {
        this.filmTypeId = filmTypeId;
        tableName = DataBaseNames.FILMS;
    }

    @Override
    public boolean specified(Film source) {
        return filmTypeId == source.getFilmType().getFilmTypeID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.FILM_TYPE_ID = %d", tableName, filmTypeId);
    }
}
