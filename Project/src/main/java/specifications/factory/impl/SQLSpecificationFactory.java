package specifications.factory.impl;

import model.Line;
import model.Theater;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;
import specifications.sql.impl.LineByTheaterIdSqlSpecification;
import specifications.sql.impl.TheaterByIdSqlSpecification;

public class SQLSpecificationFactory implements SpecificationFactory
{
    public SqlSpecification<Line> getLineByTheaterSpecification(Theater theater){
        return new LineByTheaterIdSqlSpecification(theater);
    }

    public SqlSpecification<Theater> getTheaterByIdSpecification(long theaterId){
        return new TheaterByIdSqlSpecification(theaterId);
    }
}
