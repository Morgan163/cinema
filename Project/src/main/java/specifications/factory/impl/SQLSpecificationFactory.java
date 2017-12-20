package specifications.factory.impl;

import model.Line;
import model.Seat;
import model.Theater;
import model.user.User;
import specifications.CompositeSpecification;
import specifications.Specification;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;
import specifications.sql.impl.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SQLSpecificationFactory implements SpecificationFactory
{
    public CompositeSpecification getCompositeSpecification(Specification leftOperand, Specification rightOperand) {
        return new SqlCompositeSpecification((SqlSpecification) leftOperand, (SqlSpecification) rightOperand);
    }

    public Specification<Theater> getAnyTheaterSpecification(){
        return new AnyTheaterSqlSpecification();
    }

    public Specification<User> getUserByLoginSpecification(String name){
        return null;
    }

    public Specification<User> getUserByPasswordSpecification(String password){
        return null;
    }

    public SqlSpecification<Seat> getSeatByLineIdSpecification(long lineId){
        return new SeatByLineIdSqlSpecification(lineId);
    }

    public SqlSpecification<Line> getLineByTheaterIdSpecification(long theaterId){
        return new LineByTheaterIdSqlSpecification(theaterId);
    }

    public SqlSpecification<Theater> getTheaterByIdSpecification(long theaterId){
        return new TheaterByIdSqlSpecification(theaterId);
    }
}
