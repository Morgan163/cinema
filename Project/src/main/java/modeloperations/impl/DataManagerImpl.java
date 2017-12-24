package modeloperations.impl;

import model.*;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import modeloperations.DataUtils;
import repository.Repository;
import specifications.CompositeSpecification;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataManagerImpl implements DataManager
{
    @Inject
    private Repository<User> userRepository;
    @Inject
    private Repository<Film> filmRepository;
    @Inject
    private Repository<FilmType> filmTypeRepository;
    @Inject
    private Repository<Line> lineRepository;
    @Inject
    private Repository<Seance> seanceRepository;
    @Inject
    private Repository<Seat> seatRepository;
    @Inject
    private Repository<SeatSeanceStatusMapper> seatSeanceStatusMapperRepository;
    @Inject
    private Repository<Theater> theaterRepository;

    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataUtils dataUtils;

    public DataManagerImpl() {
    }

    public void createTheater(Theater theater){
        theaterRepository.add(theater);
        createLines(theater.getLines());
    }

    public void createFilmType(FilmType filmType){
        filmTypeRepository.add(filmType);
    }

    public void createFilm(Film film){
        filmRepository.add(film);
    }

    public void createSeanceForTheater(Seance seance, Theater theater){
        bindSeanceForTheater(seance, theater);
        seanceRepository.add(seance);
    }

    public void createUser(User user){
        userRepository.add(user);
    }

    public Theater getTheater(long theaterId){
        SqlSpecification theaterByIdSpecification = (SqlSpecification) specificationFactory.getTheaterByIdSpecification(theaterId);
        Theater theater = theaterRepository.query(theaterByIdSpecification).get(0);
        return theater;
    }

    public Collection<Theater> getAllTheaters(){
        SqlSpecification anyTheaterSpecification = (SqlSpecification) specificationFactory.getAnyTheaterSpecification();
        Collection<Theater> theaters = theaterRepository.query(anyTheaterSpecification);
        return theaters;
    }

    public Seance getSeance(long seanceId){
        SqlSpecification seanceSpecification = (SqlSpecification) specificationFactory.getSeanceByIdSpecification(seanceId);
        return seanceRepository.query(seanceSpecification).get(0);
    }

    public Collection<Seance> getAllSeances(){
        SqlSpecification seanceSpecification = (SqlSpecification) specificationFactory.getAnySeanceSpecification();
        return seanceRepository.query(seanceSpecification);
    }

    public SeatSeanceStatusMapper getSeatSeanceStatusMapper(Seat seat, Seance seance){
        SqlSpecification mapperSpecification = buildSpecificationForStatusMapper(seat, seance);
        return seatSeanceStatusMapperRepository.query(mapperSpecification).get(0);
    }

    //todo остановился тут
    public Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersByKey(String code){
        return null;
    }

    public User getUser(long userId){
        return null;
    }

    public void updateTheater(Theater theater){
        theaterRepository.update(theater);
        updateLinesOrCreateNewOnes(theater);
    }

    public void updateSeatSeanceMappers(Collection<SeatSeanceStatusMapper> mappers){
        for(SeatSeanceStatusMapper mapper : mappers)
        {
            if (dataUtils.isObjectContainedInDataBase(mapper))
            {
                seatSeanceStatusMapperRepository.update(mapper);
            }
        }
    }

    public User getUserByLoginAndPassword(String login, String password) {
         SqlSpecification userSpecification = buildSpecificationForUser(login, password);
         return userRepository.query(userSpecification).get(0);
    }

    @Override
    public Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersBySeance(Seance seance) {
        SqlSpecification mappersBySeaSpecification = (SqlSpecification)specificationFactory.getMapperBySeanceIdSpecification(seance.getSeanceID());
        return seatSeanceStatusMapperRepository.query(mappersBySeaSpecification);
    }

    @Override
    public Line getLineBySeat(Seat seat) {
        SqlSpecification lineBySeatSpecification = buildSpecificationForLineBySeat(seat);
        return lineRepository.query(lineBySeatSpecification).get(0);
    }

    private SqlSpecification buildSpecificationForLineBySeat(Seat seat) {
        SqlSpecification seatSpecification = (SqlSpecification)specificationFactory.getSeatByIdSpecification(seat.getSeatID());
        SqlSpecification lineIdEqualsSeatLineIdSpecification = (SqlSpecification)specificationFactory.getLineIdEqualsSeatLineIdSpecification();
        CompositeSpecification resultSpecification = specificationFactory.getCompositeSpecification(seatSpecification, lineIdEqualsSeatLineIdSpecification);
        resultSpecification.setOperation(CompositeSpecification.Operation.AND);
        return (SqlSpecification)resultSpecification;
    }

    private void createLines(Iterable<Line> lines){
        for (Line line : lines)
        {
            createLine(line);
        }
    }

    private void createLine(Line line){
        lineRepository.add(line);
        createSeats(line.getSeats());
    }

    private void createSeats(Iterable<Seat> seats){
        for (Seat seat : seats)
        {
            createSeat(seat);
        }
    }

    private void createSeat(Seat seat){
        seatRepository.add(seat);
    }

    private void bindSeanceForTheater(Seance seance, Theater theater){
        List<Seat> seats = getSeatsForTheater(theater);
        for (Seat seat : seats)
        {
            createSeatSeanceStatusMapper(seat, seance);
        }
    }

    private List<Seat> getSeatsForTheater(Theater theater){
        List<Line> lines = getLinesForTheater(theater);
        List<Seat> seats = new ArrayList<Seat>();
        for (Line line : lines)
        {
            seats.addAll(getSeatsForLine(line));
        }
        return seats;
    }

    private void createSeatSeanceStatusMapper(Seat seat, Seance seance){
        SeatSeanceStatusMapper mapper = new SeatSeanceStatusMapper(-1, seat, seance, SeatSeanceStatus.FREE);
        seatSeanceStatusMapperRepository.add(mapper);
    }

    private void updateLinesOrCreateNewOnes(Theater theater){
        List<Line> incomingLines = theater.getLines();
        for (Line incomingLine : incomingLines)
        {
            if (dataUtils.isObjectContainedInDataBase(incomingLine))
            {
                lineRepository.add(incomingLine);
            } else
            {
                lineRepository.update(incomingLine);
            }
        }
        updateSeatsOrCreateNewOnes(theater);
    }

    private void updateSeatsOrCreateNewOnes(Theater theater){
        List<Seat> seats = new ArrayList<Seat>();
        for (Line line : theater.getLines())
        {
            seats.addAll(line.getSeats());
        }
        for (Seat seat : seats)
        {
            if (dataUtils.isObjectContainedInDataBase(seat))
            {
                seatRepository.add(seat);
            } else
            {
                seatRepository.update(seat);
            }
        }
    }

    private List<Line> getLinesForTheater(Theater theater){
        SqlSpecification lineByTheaterIdSpecification = (SqlSpecification) specificationFactory.getLineByTheaterIdSpecification(theater.getTheaterID());
        List<Line> lines = lineRepository.query(lineByTheaterIdSpecification);
        return lines;
    }

    private List<Seat> getSeatsForLine(Line line){
        SqlSpecification seatByLineIdSpecification = (SqlSpecification) specificationFactory.getSeatByLineIdSpecification(line.getLineID());
        List<Seat> seats = seatRepository.query(seatByLineIdSpecification);
        return seats;
    }

    private SqlSpecification buildSpecificationForUser(String login, String password){
        SqlSpecification loginSpecification = (SqlSpecification)specificationFactory.getUserByLoginSpecification(login);
        SqlSpecification passwordSpecification = (SqlSpecification)specificationFactory.getUserByPasswordSpecification(password);
        CompositeSpecification userSpecification = specificationFactory.getCompositeSpecification(loginSpecification, passwordSpecification);
        userSpecification.setOperation(CompositeSpecification.Operation.AND);
        return (SqlSpecification)userSpecification;
    }

    private SqlSpecification buildSpecificationForStatusMapper(Seat seat, Seance seance){
        SqlSpecification mapperBySeatSpecification = (SqlSpecification)specificationFactory.getMapperBySeatIdSpecification(seat.getSeatID());
        SqlSpecification mapperBySeanceSpecification = (SqlSpecification)specificationFactory.getMapperBySeanceIdSpecification(seance.getSeanceID());
        CompositeSpecification mapperSpecification = specificationFactory.getCompositeSpecification(mapperBySeatSpecification, mapperBySeanceSpecification);
        mapperSpecification.setOperation(CompositeSpecification.Operation.AND);
        return (SqlSpecification)mapperSpecification;
    }
}
