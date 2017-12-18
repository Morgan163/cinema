package specifications.factory;

import model.Line;
import model.Seat;
import model.Theater;
import specifications.Specification;

public interface SpecificationFactory
{
    Specification<Seat> getSeatByLineIdSpecification(long lineId);
    Specification<Line> getLineByTheaterIdSpecification(long theaterId);
    Specification<Theater> getTheaterByIdSpecification(long theaterId);
}
