package modeloperations.impl;

import model.*;
import model.user.User;
import modeloperations.DataUtils;
import repository.Repository;
import specifications.CompositeSpecification;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;


/**
 * Created by niict on 18.12.2017.
 */
public class DataUtilsImpl implements DataUtils {
    @Inject
    private Repository<SeatSeanceStatusMapper> seatSeanceStatusMapperRepository;
    @Inject
    private Repository<User> userRepository;
    @Inject
    private SpecificationFactory specificationFactory;

    public DataUtilsImpl() {
    }

    public boolean isObjectContainedInDataBase(Object object) {
        if (object instanceof User){
            return isUserContainedInDataBase((User)object);
        }
        if (object instanceof Film){
            return isFilmContainedInDataBase((Film)object);
        }
        if (object instanceof FilmType){
            return isFilmTypeContainedInDataBase((FilmType)object);
        }
        if (object instanceof Line){
            return isLineContainedInDataBase((Line)object);
        }
        if (object instanceof Seance){
            return isSeanceContainedInDataBase((Seance)object);
        }
        if (object instanceof Seat){
            return isSeatContainedInDataBase((Seat)object);
        }
        if (object instanceof SeatSeanceStatusMapper){
            return isSeatSeanceStatusMapperContainedInDataBase((SeatSeanceStatusMapper)object);
        }
        if (object instanceof Theater){
            return isTheaterContainedInDataBase((Theater)object);
        }
        throw new IllegalArgumentException("Unsupported Object Type");
    }

    private boolean isFilmTypeContainedInDataBase(FilmType filmType){
        return filmType.getFilmTypeID() > 0;
    }

    private boolean isUserContainedInDataBase(User user) {
        SqlSpecification userSpecification = buildSpecificationForCheckUser(user);
        return userRepository.query(userSpecification).size() > 0;
    }

    private boolean isFilmContainedInDataBase(Film film) {
        return film.getFilmID() > 0;
    }

    private boolean isLineContainedInDataBase(Line line) {
        return line.getLineID() > 0;
    }

    private boolean isSeanceContainedInDataBase(Seance seance) {
        return seance.getSeanceID() > 0;
    }

    private boolean isSeatContainedInDataBase(Seat seat) {
        return seat.getSeatID() > 0;
    }

    private boolean isSeatSeanceStatusMapperContainedInDataBase(SeatSeanceStatusMapper mapper) {
        return mapper.getMappingId() > 0;
    }

    private boolean isTheaterContainedInDataBase(Theater theater) {
        return theater.getTheaterID() > 0;
    }

    private SqlSpecification buildSpecificationForCheckUser(User user){
        SqlSpecification loginSpecification = (SqlSpecification)specificationFactory.getUserByLoginSpecification(user.getLogin());
        SqlSpecification passwordSpecification = (SqlSpecification)specificationFactory.getUserByPasswordSpecification(user.getPassword());
        CompositeSpecification resultSpecification = specificationFactory.getCompositeSpecification(loginSpecification, passwordSpecification);
        resultSpecification.setOperation(CompositeSpecification.Operation.AND);
        return (SqlSpecification) resultSpecification;
    }

}
