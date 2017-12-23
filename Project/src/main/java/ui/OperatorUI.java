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
        if (!user.getUserRole().equals(UserRole.OPERATOR)){
            String currentLocation = getUI().getPage().getLocation().toString();
            String newLocation = currentLocation.substring(0, currentLocation.lastIndexOf("/") - 1);
            getUI().getPage().setLocation(newLocation);
        }
    }
}
