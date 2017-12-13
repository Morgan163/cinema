package specifications.factory;

import model.Line;
import model.Theater;
import specifications.Specification;

public interface SpecificationFactory
{
    Specification<Line> getLineByTheaterSpecification(Theater theater);
    Specification<Theater> getTheaterByIdSpecification(long theaterId);
}
