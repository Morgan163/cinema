package specifications.factory.impl;

import model.*;
import model.user.User;
import model.user.UserRole;
import specifications.CompositeSpecification;
import specifications.Specification;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;
import specifications.sql.impl.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.Calendar;

@ApplicationScoped
public class SQLSpecificationFactory implements SpecificationFactory
{
    public SQLSpecificationFactory() {
    }

    public Specification getRoleIdEqualsUserRoleIdSpecification(){
        return new RoleIdEqualsUserRoleIdSqlSpecification();
    }

    @Override
    public Specification getLineIdEqualsSeatLineIdSpecification() {
        return new LineIdEqualsSeatLineIdSqlSpecification();
    }

    public Specification<Seance> getSeanceByIdSpecification(long seanceId){
        return new SeanceByIdSqlSpecification(seanceId);
    }

    public CompositeSpecification getCompositeSpecification(Specification leftOperand, Specification rightOperand) {
        return new SqlCompositeSpecification((SqlSpecification) leftOperand, (SqlSpecification) rightOperand);
    }

    public Specification<Theater> getAnyTheaterSpecification(){
        return new AnyTheaterSqlSpecification();
    }

    public Specification<Seance> getAnySeanceSpecification(){
        return new AnySeanceSqlSpecification();
    }

    public Specification<User> getUserByLoginSpecification(String name){
        return new UserByLoginSqlSpecification(name);
    }

    public Specification<User> getUserByPasswordSpecification(String password){
        return new UserByPasswordSqlSpecification(password);
    }

    public Specification<Seat> getSeatByLineIdSpecification(long lineId){
        return new SeatByLineIdSqlSpecification(lineId);
    }

    public Specification<Line> getLineByTheaterIdSpecification(long theaterId){
        return new LineByTheaterIdSqlSpecification(theaterId);
    }

    public Specification<Theater> getTheaterByIdSpecification(long theaterId){
        return new TheaterByIdSqlSpecification(theaterId);
    }

    public Specification<SeatSeanceStatusMapper> getMapperBySeanceIdSpecification(long seanceId){
        return new MapperBySeanceIdSqlSpecification(seanceId);
    }

    public Specification<SeatSeanceStatusMapper> getMapperBySeatIdSpecification(long seatId){
        return new MapperBySeatIdSqlSpecification(seatId);
    }

    @Override
    public Specification<Line> getLineByIdSpecification(long lineID) {
        return new LineByIdSqlSpecification(lineID);
    }

    @Override
    public Specification<Film> getFilmByIdSpecification(long filmID) {
        return new FilmByIdSqlSpecification(filmID);
    }

    @Override
    public Specification<Seat> getSeatByIdSpecification(long seatID) {
        return new SeatByIdSqlSpecification(seatID);
    }

    @Override
    public Specification<FilmType> getFilmTypeByIdSpecification(long filmTypeID) {
        return new FilmTypeByIdSqlSpecification(filmTypeID);
    }

    @Override
    public Specification<SeatSeanceStatusMapper> getMapperByIdSpecification(long mappingId) {
        return new MapperByIdSqlSpecification(mappingId);
    }

    @Override
    public Specification<User> getUserByIdSpecification(long userID) {
        return new UserByIdSqlSpecification(userID);
    }

    @Override
    public Specification<Seance> getSeanceEarlierThanSpecification(Calendar date) {
        return new SeanceEarlierThanSqlSpecification(date);
    }

    @Override
    public Specification<Seance> getSeanceLaterThanSpecification(Calendar date) {
        return new SeanceLaterThanSqlSpecification(date);
    }

    @Override
    public Specification<SeatSeanceStatusMapper> getMapperByKeySpecification(String code) {
        return new MapperByKeySqlSpecification(code);
    }

    @Override
    public Specification<Film> getFilmByTypeIdSpecification(long filmTypeId) {
        return new FilmByTypeIdSqlSpecification(filmTypeId);
    }

    @Override
    public Specification<FilmType> getAnyFilmTypeSpecification() {
        return new AnyFilmTypeSqlSpecification();
    }

    @Override
    public Specification<Film> getAnyFilmSpecification() {
        return new AnyFilmSqlSpecification();
    }

    @Override
    public Specification<User> getUserByRoleIdSpecification(long roleId) {
        return new UserByRoleIdSqlSpecification(roleId);
    }
}
