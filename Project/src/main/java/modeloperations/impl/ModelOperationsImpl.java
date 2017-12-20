package modeloperations.impl;

import model.*;
import model.user.User;
import modeloperations.*;
import org.apache.commons.collections4.CollectionUtils;

import javax.inject.Inject;
import java.util.ArrayList;
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

    public void bookSeatsForSeance(Collection <Seat> seats, Seance seance, String contacts){
        Collection<SeatSeanceStatusMapper> mappersToUpdate = new ArrayList<SeatSeanceStatusMapper>();
        String bookingKey = bookingCodeGenerator.generateCode();
        for (Seat seat: seats){
            SeatSeanceStatusMapper mapper = dataManager.getSeatSeanceStatusMapper(seat, seance);
            if (SeatSeanceStatus.FREE.equals(mapper.getSeatSeanceStatus())){
                mapper.setBookKey(bookingKey);
                mapper.setSeatSeanceStatus(SeatSeanceStatus.RESERVED);
                mappersToUpdate.add(mapper);
            }
        }
       if (CollectionUtils.isNotEmpty(mappersToUpdate)){
            dataManager.updateSeatSeanceMappers(mappersToUpdate);
            bookingNotifier.sendKeyToContacts(bookingKey, contacts);
       }
    }

    public Collection<Seat> redeemSeatsByCode(String code){
        //Collection<S>
        Collection<SeatSeanceStatusMapper> mappersToUpdate = dataManager.getSeatSeanceStatusMappersByCode(code);
        for (SeatSeanceStatusMapper mapper : mappersToUpdate){
            if (SeatSeanceStatus.RESERVED.equals(mapper.getSeatSeanceStatus())){

            }
        }
    }

    public void buySeatForSeance(Seat seat){

    }

    public void refund(Collection <Seat> seats, Seance seance){

    }

    public double calculatePriceForSeatsAndSeance(Collection <Seat> seats, Seance seance){
        return 0;
    }

    public boolean isUserExists(User user){
        return false;
    }

    public void authorize(User user){

    }
}

