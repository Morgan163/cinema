package specifications.sql.impl;

import db.DataBaseNames;
import model.Film;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 25.12.2017.
 */
public class AnyFilmSqlSpecification implements SqlSpecification<Film> {

    String tableName;

    public AnyFilmSqlSpecification(){
        this.tableName = DataBaseNames.FILMS;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        return "1 = 1";
    }

    public boolean specified(Film source){
        return true;
    }
}
