package specifications.sql.impl;

import db.DataBaseNames;
import model.FilmType;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 25.12.2017.
 */
public class AnyFilmTypeSqlSpecification implements SqlSpecification<FilmType> {

    String tableName;

    public AnyFilmTypeSqlSpecification(){
        this.tableName = DataBaseNames.FILM_TYPES;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        return "1 = 1";
    }

    public boolean specified(FilmType source){
        return true;
    }
}
