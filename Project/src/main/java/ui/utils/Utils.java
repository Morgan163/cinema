package ui.utils;

import model.user.User;
import model.user.UserRole;

public class Utils {

    public static boolean checkUserRoleAndRedirectIfNeeded(User user, UserRole userRole ){
        return user.getUserRole() == null || !user.getUserRole().equals(userRole);
    }

    public static String redirectToMainPage(String currentLocation){
        return currentLocation.substring(0, currentLocation.lastIndexOf("/"));
    }
}
