package specifications.factory;

import model.Line;
import model.Seat;
import model.Theater;
import specifications.Specification;
import specifications.sql.impl.SqlCompositeSpecification;

public interface SpecificationFactory
{
    Specification getCompositeSpecification(Specification leftOperand, Specification rightOperand);
    Specification<Seat> getSeatByLineIdSpecification(long lineId);
    Specification<Line> getLineByTheaterIdSpecification(long theaterId);
    Specification<Theater> getTheaterByIdSpecification(long theaterId);
}
