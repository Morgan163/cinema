package specifications.factory;

import model.*;
import model.user.User;
import model.user.UserRole;
import oracle.sql.OPAQUE;
import specifications.CompositeSpecification;
import specifications.Specification;
import specifications.sql.SqlSpecification;
import specifications.sql.impl.SqlCompositeSpecification;

import java.util.Calendar;

public interface SpecificationFactory
{
    Specification getRoleIdEqualsUserRoleIdSpecification();
    Specification getLineIdEqualsSeatLineIdSpecification();
    Specification getTheaterIdEqualsLineTheaterIdSpecification();
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
    Specification<Seance> getSeanceEarlierThanSpecification(Calendar date);
    Specification<Seance> getSeanceLaterThanSpecification(Calendar date);
    Specification<SeatSeanceStatusMapper> getMapperByKeySpecification(String code);
    Specification<Film> getFilmByTypeIdSpecification(long filmTypeId);
    Specification<FilmType> getAnyFilmTypeSpecification();
    Specification<Film> getAnyFilmSpecification();
    Specification<User> getUserByRoleIdSpecification(long roleId);
}
