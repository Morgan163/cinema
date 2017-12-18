package modeloperations.impl;

import model.*;
import model.user.User;
import modeloperations.DataUtils;
import repository.Repository;
import specifications.factory.SpecificationFactory;

import javax.inject.Inject;


/**
 * Created by niict on 18.12.2017.
 */
public class DataUtilsImpl implements DataUtils {
    @Inject
    Repository<SeatSeanceStatusMapper> seatSeanceStatusMapperRepository;
    @Inject
    SpecificationFactory specificationFactory;

    public boolean isObjectContainedInDataBase(Object object) {
        if (object instanceof User){
            return isUserContainedInDataBase((User)object);
        }
        if (object instanceof Film){
            return isFilmContainedInDataBase((Film)object);
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

    private boolean isUserContainedInDataBase(User user) {
        return user.getUserID() > 0;
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
        return false; // TODO доделать, когда будут спецификации
    }

    private boolean isTheaterContainedInDataBase(Theater theater) {
        return theater.getTheaterID() > 0;
    }
}
