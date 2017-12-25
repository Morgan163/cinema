package modeloperations.impl;

import exceptions.SendMailException;
import model.*;
import model.user.User;
import modeloperations.*;
import org.apache.commons.collections4.CollectionUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by niict on 18.12.2017.
 */
public class ModelOperationsImpl implements ModelOperations {
    @Inject
    private DataManager dataManager;
    @Inject
    private DataUtils dataUtils;
    @Inject
    private BookingCodeGenerator bookingCodeGenerator;
    @Inject
    private BookingNotifier bookingNotifier;

    public ModelOperationsImpl() {
    }

    public void bookSeatsForSeance(Collection <Seat> seats, Seance seance, String contacts) throws SendMailException {
        if (CollectionUtils.isEmpty(seats))
            throw new RuntimeException("empty seats collection");
        Collection<SeatSeanceStatusMapper> mappersToUpdate = new ArrayList<SeatSeanceStatusMapper>();
        String bookingKey = generateUniqueBookingCode();
        for (Seat seat: seats){
            SeatSeanceStatusMapper mapper = dataManager.getSeatSeanceStatusMapper(seat, seance);
            if (SeatSeanceStatus.FREE.equals(mapper.getSeatSeanceStatus()) || mapper.getSeatSeanceStatus() == null){
                mapper.setBookKey(bookingKey);
                mapper.setSeatSeanceStatus(SeatSeanceStatus.RESERVED);
                mappersToUpdate.add(mapper);
            }
        }
        if (CollectionUtils.isNotEmpty(mappersToUpdate))
        {
            bookingNotifier.sendKeyToContacts(bookingKey, contacts);
            dataManager.updateSeatSeanceMappers(mappersToUpdate);
        }
    }

    private String generateUniqueBookingCode(){
        String code;
        do
        {
            code =  bookingCodeGenerator.generateCode();
        } while (isCodeExists(code));
        return code;
    }

    private boolean isCodeExists(String code){
        return dataManager.getSeatSeanceStatusMappersByKey(code).size() > 0;
    }

    public Collection<Seat> getSeatsByBookingKey(String key){
        Collection<Seat> reservedSeats = new ArrayList <Seat>();
        Collection<SeatSeanceStatusMapper> mappersWithNeededBookKey = dataManager.getSeatSeanceStatusMappersByKey(key);
        for (SeatSeanceStatusMapper mapper : mappersWithNeededBookKey){
            if (SeatSeanceStatus.RESERVED.equals(mapper.getSeatSeanceStatus())){
                reservedSeats.add(mapper.getSeat());
            }
        }
        return reservedSeats;
    }

    public void closeReservationByKey(String key){
        Collection <SeatSeanceStatusMapper> mappersToUpdate = new ArrayList <SeatSeanceStatusMapper>();
        Collection <SeatSeanceStatusMapper> mappersWithNeededBookKey = dataManager.getSeatSeanceStatusMappersByKey(key);
        for (SeatSeanceStatusMapper mapper : mappersWithNeededBookKey)
        {
            if (SeatSeanceStatus.RESERVED.equals(mapper.getSeatSeanceStatus()))
            {
                mapper.setSeatSeanceStatus(SeatSeanceStatus.SOLD_OUT);
                mapper.setBookKey("");
                mappersToUpdate.add(mapper);
            }
        }
        dataManager.updateSeatSeanceMappers(mappersToUpdate);
    }

    public void buySeatsForSeance(Collection<Seat> seats, Seance seance){
        Collection<SeatSeanceStatusMapper> mappersToUpdate = new ArrayList <SeatSeanceStatusMapper>();
        for (Seat seat: seats){
            SeatSeanceStatusMapper mapper = dataManager.getSeatSeanceStatusMapper(seat, seance);
            if (SeatSeanceStatus.FREE.equals(mapper.getSeatSeanceStatus())){
                mapper.setSeatSeanceStatus(SeatSeanceStatus.SOLD_OUT);
                mappersToUpdate.add(mapper);
            }
        }
        dataManager.updateSeatSeanceMappers(mappersToUpdate);
    }

    public void refund(Collection <Seat> seats, Seance seance){
        if (seance.getSeanceStartDate().compareTo(Calendar.getInstance()) > 0){
            throw new RuntimeException("Impossible to make a refund after seance Start Date");
        }
        Collection<SeatSeanceStatusMapper> mappersToUpdate = new ArrayList <SeatSeanceStatusMapper>();
        for (Seat seat: seats){
            SeatSeanceStatusMapper mapper = dataManager.getSeatSeanceStatusMapper(seat, seance);
            if (SeatSeanceStatus.SOLD_OUT.equals(mapper.getSeatSeanceStatus())){
                mapper.setSeatSeanceStatus(SeatSeanceStatus.FREE);
                mappersToUpdate.add(mapper);
            }
        }
        dataManager.updateSeatSeanceMappers(mappersToUpdate);
    }

    public double calculatePriceForSeatsAndSeance(Collection <Seat> seats, Seance seance){
        double seanceBasePrice = seance.getPriceValue();
        double totalPrice = 0;
        for (Seat seat : seats){
            totalPrice += SeatType.VIP.equals(seat.getSeatType()) ? seanceBasePrice*1.5 : seanceBasePrice;
        }
        return totalPrice;
    }

    public boolean isUserExists(User user){
        return dataUtils.isObjectContainedInDataBase(user);
    }

    public void authorize(User user){
        User foundUser = dataManager.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        user.setUserRole(foundUser.getUserRole());
    }
}

