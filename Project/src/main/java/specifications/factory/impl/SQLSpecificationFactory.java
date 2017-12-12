package specifications.factory.impl;

import model.Line;
import model.Theater;
import specifications.Specification;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;
import specifications.sql.impl.LineByTheaterIdSqlSpecification;
import specifications.sql.impl.TheaterByNumberSqlSpecification;

public class SQLSpecificationFactory implements SpecificationFactory
{
    public SqlSpecification<Line> createLineByTheaterSpecification(Theater theater){
        return new LineByTheaterIdSqlSpecification(theater);
    }

    public SqlSpecification<Theater> createTheaterByNumberSqlSpecification(int theaterNumber){
        return new TheaterByNumberSqlSpecification(theaterNumber);
    }
}
