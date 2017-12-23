package specifications.sql.impl;

import db.DataBaseNames;
import model.FilmType;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class FilmTypeByIdSqlSpecification implements SqlSpecification<FilmType> {
    private long filmTypeId;
    private String tableName;

    public FilmTypeByIdSqlSpecification(long filmTypeId) {
        this.filmTypeId = filmTypeId;
        tableName = DataBaseNames.FILM_TYPES;
    }

    @Override
    public boolean specified(FilmType source) {
        return filmTypeId == source.getFilmTypeID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.FILM_TYPE_ID = %d" , tableName, filmTypeId);
    }
}
