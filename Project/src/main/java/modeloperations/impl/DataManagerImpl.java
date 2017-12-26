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
import java.util.*;

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

    public Collection<SeatSeanceStatusMapper> getSeatSeanceStatusMappersByKey(String code){
        SqlSpecification mapperSpecification = (SqlSpecification) specificationFactory.getMapperByKeySpecification(code);
        return seatSeanceStatusMapperRepository.query(mapperSpecification);
    }

    public User getUser(long userId){
        SqlSpecification userSpecification = (SqlSpecification)specificationFactory.getUserByIdSpecification(userId);
        return userRepository.query(userSpecification).get(0);
    }

    public void updateTheater(Theater theater){
        theaterRepository.update(theater);
        updateLinesForTheater(theater);
    }

    private void updateLinesForTheater(Theater theater){
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
        if (dataUtils.isObjectContainedInDataBase(theater))
        {
            removeExtraLinesForTheterIfNeeded(theater);
        }
        updateSeatsForTheater(theater);
    }

    private void updateSeatsForTheater(Theater theater){
        List<Seat> incomingSeats = new ArrayList<Seat>();
        List<Line> incomingLines = theater.getLines();
        for (Line line : incomingLines)
        {
            incomingSeats.addAll(line.getSeats());
        }
        for (Seat seat : incomingSeats)
        {
            if (dataUtils.isObjectContainedInDataBase(seat))
            {
                seatRepository.add(seat);
            } else
            {
                seatRepository.update(seat);
            }
        }
        for (Line line : incomingLines){
            if (dataUtils.isObjectContainedInDataBase(line)){
                removeExtraSeatsForLineIfNeeded(line);
            }
        }
    }

    private void removeExtraLinesForTheterIfNeeded(Theater theater) {
        List<Line> incomingLines = theater.getLines();
        List<Line> existingLines = getLinesForTheater(theater);
        for (Line existingLine : existingLines){
            if (!incomingLines.contains(existingLine)){
                lineRepository.remove(existingLine);
            }
        }
    }

    public void updateSeatSeanceMapper(SeatSeanceStatusMapper mapper){
        if (dataUtils.isObjectContainedInDataBase(mapper))
        {
            seatSeanceStatusMapperRepository.update(mapper);
        }
    }

    public void updateSeance(Seance seance){
        if (dataUtils.isObjectContainedInDataBase(seance))
        {
            seanceRepository.update(seance);
        }
    }

    public void updateFilm(Film film){
        if (dataUtils.isObjectContainedInDataBase(film))
        {
            filmRepository.update(film);
        }
    }

    public void updateFilmType(FilmType filmType){
        if (dataUtils.isObjectContainedInDataBase(filmType))
        {
            filmTypeRepository.update(filmType);
        }
    }

    public void updateUser(User user){
        if (dataUtils.isObjectContainedInDataBase(user))
        {
            userRepository.update(user);
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
    @Override
    public Collection<FilmType> getAllFilmTypes(){
        SqlSpecification anyFilmTypeSpecification = (SqlSpecification)specificationFactory.getAnyFilmTypeSpecification();
        return filmTypeRepository.query(anyFilmTypeSpecification);
    }
    @Override
    public Collection<Film> getAllFilms(){
        SqlSpecification anyFilmSpecification = (SqlSpecification)specificationFactory.getAnyFilmSpecification();
        return filmRepository.query(anyFilmSpecification);
    }
    @Override
    public Collection<User> getAllOperators(){
        SqlSpecification userSpecification = (SqlSpecification)specificationFactory.getUserByRoleIdSpecification(UserRole.OPERATOR.getRoleID());
        return userRepository.query(userSpecification);
    }

    @Override
    public Theater getTheaterBySeance(Seance seance) {
        Collection<SeatSeanceStatusMapper> mappersForSeance = getSeatSeanceStatusMappersBySeance(seance);
        if (mappersForSeance.iterator().hasNext()){
            SeatSeanceStatusMapper mapper = mappersForSeance.iterator().next();
            Seat seatForMapper = mapper.getSeat();
            Line lineForSeat = getLineBySeat(seatForMapper);
            return getTheaterByLine(lineForSeat);
        }
        return new Theater();
    }

    public Theater getTheaterByLine(Line line){
        SqlSpecification theaterByLineSpecification = buildSpecificationForTheaterByLine(line);
        return theaterRepository.query(theaterByLineSpecification).get(0);
    }

    private SqlSpecification buildSpecificationForLineBySeat(Seat seat) {
        SqlSpecification seatSpecification = (SqlSpecification)specificationFactory.getSeatByIdSpecification(seat.getSeatID());
        SqlSpecification lineIdEqualsSeatLineIdSpecification = (SqlSpecification)specificationFactory.getLineIdEqualsSeatLineIdSpecification();
        CompositeSpecification resultSpecification = specificationFactory.getCompositeSpecification(seatSpecification, lineIdEqualsSeatLineIdSpecification);
        resultSpecification.setOperation(CompositeSpecification.Operation.AND);
        return (SqlSpecification)resultSpecification;
    }


    private SqlSpecification buildSpecificationForTheaterByLine(Line line) {
        SqlSpecification lineSpecification = (SqlSpecification)specificationFactory.getLineByIdSpecification(line.getLineID());
        SqlSpecification theaterIdEqualsLineTheaterIdSpecification = (SqlSpecification)specificationFactory.getTheaterIdEqualsLineTheaterIdSpecification();
        CompositeSpecification resultSpecification = specificationFactory.getCompositeSpecification(lineSpecification, theaterIdEqualsLineTheaterIdSpecification);
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

    private void removeExtraSeatsForLineIfNeeded(Line line) {
        List<Seat> incomingSeats = line.getSeats();
        List<Seat> existingSeats = getSeatsForLine(line);
        for (Seat existingSeat : existingSeats){
            if (!incomingSeats.contains(existingSeat)){
                seatRepository.remove(existingSeat);
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
