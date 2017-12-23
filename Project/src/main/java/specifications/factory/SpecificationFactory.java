package specifications.factory;

import model.*;
import model.user.User;
import oracle.sql.OPAQUE;
import specifications.CompositeSpecification;
import specifications.Specification;
import specifications.sql.impl.SqlCompositeSpecification;

public interface SpecificationFactory
{
    Specification getRoleIdEqualsUserRoleIdSpecification();
    Specification<Seance> getSeanceByIdSpecification(long seanceId);
    CompositeSpecification getCompositeSpecification(Specification leftOperand, Specification rightOperand);
    Specification<Theater> getAnyTheaterSpecification();
    Specification<Seance> getAnySeanceSpecification();
    Specification<User> getUserByLoginSpecification(String name);
    Specification<User> getUserByPasswordSpecification(String password);
    Specification<Seat> getSeatByLineIdSpecification(long lineId);
    Specification<Line> getLineByTheaterIdSpecification(long theaterId);
    Specification<Theater> getTheaterByIdSpecification(long theaterId);
    Specification<SeatSeanceStatusMapper> getMapperBySeanceIdSpecification(long seanceId);
    Specification<SeatSeanceStatusMapper> getMapperBySeatIdSpecification(long seatId);
    Specification<Line> getLineByIdSpecification(long lineID);
    Specification<Film> getFilmByIdSpecification(long filmID);
    Specification<Seat> getSeatByIdSpecification(long seatID);
    Specification<FilmType> getFilmTypeByIdSpecification(long filmTypeID);
    Specification<SeatSeanceStatusMapper> getMapperByIdSpecification(long mappingId);
    Specification<User> getUserByIdSpecification(long userID);
}
