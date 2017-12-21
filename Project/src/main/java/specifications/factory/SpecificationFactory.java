package specifications.factory;

import model.Line;
import model.Seat;
import model.Theater;
import model.user.User;
import oracle.sql.OPAQUE;
import specifications.CompositeSpecification;
import specifications.Specification;
import specifications.sql.impl.SqlCompositeSpecification;

public interface SpecificationFactory
{
    Specification getRoleIdEqualsUserRoleIdSpecification();
    CompositeSpecification getCompositeSpecification(Specification leftOperand, Specification rightOperand);
    Specification<Theater> getAnyTheaterSpecification();
    Specification<User> getUserByLoginSpecification(String name);
    Specification<User> getUserByPasswordSpecification(String password);
    Specification<Seat> getSeatByLineIdSpecification(long lineId);
    Specification<Line> getLineByTheaterIdSpecification(long theaterId);
    Specification<Theater> getTheaterByIdSpecification(long theaterId);
}
