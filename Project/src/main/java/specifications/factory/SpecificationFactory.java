package specifications.factory;

import model.Line;
import model.Theater;
import specifications.Specification;

public interface SpecificationFactory
{
    Specification<Line> createLineByTheaterSpecification(Theater theater);
    Specification<Theater> createTheaterByNumberSqlSpecification(int theaterNumber);
}
