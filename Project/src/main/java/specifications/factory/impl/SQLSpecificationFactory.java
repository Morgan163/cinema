package specifications.factory.impl;

import model.Line;
import model.Seat;
import model.Theater;
import specifications.Specification;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;
import specifications.sql.impl.LineByTheaterIdSqlSpecification;
import specifications.sql.impl.SeatByLineIdSqlSpecification;
import specifications.sql.impl.TheaterByIdSqlSpecification;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SQLSpecificationFactory implements SpecificationFactory
{
    public Specification<Seat> getSeatByLineIdSpecification(long lineId){
        return new SeatByLineIdSqlSpecification(lineId);
    }

    public SqlSpecification<Line> getLineByTheaterIdSpecification(long theaterId){
        return new LineByTheaterIdSqlSpecification(theaterId);
    }

    public SqlSpecification<Theater> getTheaterByIdSpecification(long theaterId){
        return new TheaterByIdSqlSpecification(theaterId);
    }
}
