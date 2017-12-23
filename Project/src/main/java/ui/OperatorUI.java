package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import model.user.User;
import model.user.UserRole;

import javax.inject.Inject;


/**
 * Created by niict on 24.12.2017.
 */
@CDIUI("operator")
@Theme("mytheme")
public class OperatorUI extends UI {
    @Inject
    private User user;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        checkUserRoleAndRedirectIfNeeded();

    }

    private void checkUserRoleAndRedirectIfNeeded(){
        if (user.getUserRole() == null || !user.getUserRole().equals(UserRole.OPERATOR)){
            redirectToMainPage();
        }
    }

    private void redirectToMainPage(){
        String currentLocation = getUI().getPage().getLocation().toString();
        String newLocation = currentLocation.substring(0, currentLocation.lastIndexOf("/"));
        getUI().getPage().setLocation(newLocation);
    }
}
