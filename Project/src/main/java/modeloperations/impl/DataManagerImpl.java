package modeloperations.impl;

import model.*;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DataManagerImpl
{
    @Inject Repository<AgeLimitType> ageLimitTypeRepository;
    @Inject Repository<Film> filmRepository;
    @Inject Repository<FilmType> filmTypeRepository;
    @Inject Repository<Line> lineRepository;
    @Inject Repository<Seance> seanceRepository;
    @Inject Repository<Seat> seatRepository;
    @Inject Repository<SeatSeanceStatus> seatSeanceStatusRepository;
    @Inject Repository<SeatSeanceStatusMapper> seatSeanceStatusMapperRepository;
    @Inject Repository<SeatType> seatTypeRepository;
    @Inject Repository<Theater> theaterRepository;

    @Inject
    SpecificationFactory specificationFactory;

    public void createTheater(Theater theater){
        theaterRepository.add(theater);
    }

    public void createLines(Iterable<Line> lines){
        for (Line line : lines){
            createLine(line);
        }
    }

    public void createLine(Line line){
        lineRepository.add(line);
    }

    public void createSeats(Iterable<Seat> seats){
        for (Seat seat : seats){
            createSeat(seat);
        }
    }

    public void createSeat(Seat seat){
        seatRepository.add(seat);
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

    public Theater getTheater(long theaterId){
        SqlSpecification theaterByIdSpecification = (SqlSpecification)specificationFactory.getTheaterByIdSpecification(theaterId);
        Theater theater = theaterRepository.query(theaterByIdSpecification).get(0);
        wireWithLines(theater);
        return theater;
    }

    public void updateTheater(Theater theater){
        theaterRepository.update(theater);
        updateLinesOrCreateNewOnes(theater);
    }

    private void updateLinesOrCreateNewOnes(Theater theater){
        List<Line> incomingLines = theater.getLines();
        List<Line> existingLines = getLinesForTheater(theater);
        for (Line incomingLine : incomingLines)
        {
            if(incomingLine.getLineID() < 0){
                lineRepository.add(incomingLine);
            }
            else {
                lineRepository.update(incomingLine);
            }
        }
        ///TODO updateSeats
    }

    private void updateSeatsOrCreateNewOnes(List <Seat> seats){
        for (Seat seat : seats)
        {
            seatRepository.update(seat);
        }
    }

    private void bindSeanceForTheater(Seance seance, Theater theater){
        List<Seat> seats = getSeatsForTheater(theater);
        for (Seat seat : seats)
        {
            createSeatSeanceStatusMapping(seat, seance);
        }
    }

    private List<Seat> getSeatsForTheater(Theater theater){
        List<Line> lines = getLinesForTheater(theater);
        List<Seat> seats = new ArrayList <Seat>();
        for (Line line : lines){
            seats.addAll(getSeatsForLine(line));
        }
        return seats;
    }

    private void createSeatSeanceStatusMapping(Seat seat, Seance seance){
        SeatSeanceStatusMapper mapper = new SeatSeanceStatusMapper(seat, seance , SeatSeanceStatus.FREE);
        seatSeanceStatusMapperRepository.add(mapper);
    }

    private void wireWithLines(Theater theater){
        List<Line> lines = getLinesForTheater(theater);
        wireWithSeats(lines);
        theater.addLines(lines);
    }

    private void wireWithSeats(List<Line> lines){
        for (Line line : lines){
            wireWithSeats(line);
        }
    }

    private void wireWithSeats(Line line){
        List<Seat> seats = getSeatsForLine(line);
        line.addSeats(seats);
    }

    private List<Line> getLinesForTheater(Theater theater){
        SqlSpecification lineByTheaterIdSpecification = (SqlSpecification)specificationFactory.getLineByTheaterIdSpecification(theater.getTheaterID());
        List<Line> lines = lineRepository.query(lineByTheaterIdSpecification);
        return lines;
    }

    private List<Seat> getSeatsForLine(Line line){
        SqlSpecification seatByLineIdSpecification = (SqlSpecification)specificationFactory.getSeatByLineIdSpecification(line.getLineID());
        List<Seat> seats = seatRepository.query(seatByLineIdSpecification);
        return seats;
    }
}
