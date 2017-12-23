package modeloperations;

import exceptions.SendMailException;
import model.Seance;
import model.Seat;
import model.user.User;

import java.util.Collection;

public interface ModelOperations
{
    void bookSeatsForSeance(Collection<Seat> seats, Seance seance, String contacts) throws SendMailException;
    Collection<Seat> getSeatsByBookingKey(String code);
    void closeReservationByKey(String key);
    void buySeatsForSeance(Collection<Seat> seat, Seance seance);
    void refund(Collection<Seat> seats, Seance seance);
    double calculatePriceForSeatsAndSeance(Collection<Seat> seats, Seance seance);
    boolean isUserExists(User user);
    void authorize(User user);
}
