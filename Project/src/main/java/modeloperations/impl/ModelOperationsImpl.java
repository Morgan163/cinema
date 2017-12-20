package modeloperations.impl;

import model.*;
import model.user.User;
import modeloperations.DataManager;
import modeloperations.DataUtils;
import modeloperations.ModelOperations;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by niict on 18.12.2017.
 */
public class ModelOperationsImpl implements ModelOperations {
    @Inject
    private DataManager dataManager;
    @Inject
    private DataUtils dataUtils;


    public void bookSeatsForSeance(Collection <Seat> seats, Seance seance, String contacts){

    }

    public Collection <Seat> redeemSeatsForSeance(Seance seance, String code){
        return null;
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

