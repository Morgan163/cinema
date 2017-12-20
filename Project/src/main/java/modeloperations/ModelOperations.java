package modeloperations;

import model.Seance;
import model.Seat;
import model.user.User;

import java.util.Collection;

public interface ModelOperations
{
    void bookSeatsForSeance(Collection<Seat> seats, Seance seance, String contacts);
    Collection<Seat> redeemSeatsForSeance(Seance seance, String code);
    void buySeatForSeance(Seat seat);
    void refund(Collection<Seat> seats, Seance seance);
    double calculatePriceForSeatsAndSeance(Collection<Seat> seats, Seance seance);
    boolean isUserExists(User user);
    void authorize(User user);
}
