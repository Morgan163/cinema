package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import model.user.User;
import model.user.UserRole;

import javax.inject.Inject;

@CDIUI("administrator")
@Theme("mytheme")
public class AdministratorUI extends UI {
    @Inject
    private User user;

    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final HorizontalLayout objectsLayout = new HorizontalLayout();
    private final VerticalLayout theaterLayout = new VerticalLayout();
    private final VerticalLayout filmTypeLayout = new VerticalLayout();
    private final VerticalLayout seanceLayout = new VerticalLayout();
    private final VerticalLayout filmLayout = new VerticalLayout();
    private final VerticalLayout operatorLayout = new VerticalLayout();

    //header objects
    private final Button createButton = new Button();
    private final Button setButton = new Button();
    private final Button deleteButton = new Button();
    private final Button exitButton = new Button();
    private final Button helpButton = new Button();
    private final Label nameLabel = new Label(user.getLogin());

    //theater objects
    private final HorizontalLayout theaterHeaderLayout = new HorizontalLayout();
    private final Label theaterLabel = new Label("Залы");
    private final Button theaterHelpButton = new Button();

    //film type objects
    private final HorizontalLayout filmTypeHeaderLayout = new HorizontalLayout();
    private final Label filmTypeLabel = new Label("Жанры");
    private final Button filmTypeHelpButton = new Button();

    //seance objects
    private final HorizontalLayout seanceHeaderLayout = new HorizontalLayout();
    private final Label seanceLabel = new Label("Жанры");
    private final Button seanceHelpButton = new Button();

    //film objects
    private final HorizontalLayout filmHeaderLayout = new HorizontalLayout();
    private final Label filmLabel = new Label("Фильмы");
    private final Button filmHelpButton = new Button();

    //operator objects
    private final HorizontalLayout operatorHeaderLayout = new HorizontalLayout();
    private final Label operatorLabel = new Label("Операторы");
    private final Button operatorHelpButton = new Button();


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        if (!user.getUserRole().equals(UserRole.ADMIN)){
            String currentLocation = getUI().getPage().getLocation().toString();
            String newLocation = currentLocation.substring(0, currentLocation.lastIndexOf("/") - 1);
            getUI().getPage().setLocation(newLocation);
        }

        createButton.addClickListener(e -> createButtonListener());
        setButton.addClickListener(e -> setButtonListener());
        deleteButton.addClickListener(e -> deleteButtonListener());
        helpButton.addClickListener(e -> helpButtonListener());
        exitButton.addClickListener(e -> exitButtonListener());
        theaterHelpButton.addClickListener(e -> theaterHelpButtonListener());
        filmTypeHelpButton.addClickListener(e -> filmTypeHelpButtonListener());
        seanceHelpButton.addClickListener(e -> seanceHelpButtonListener());
        filmHelpButton.addClickListener(e -> filmHelpButtonListener());
        operatorHelpButton.addClickListener(e -> operatorHelpButtonListener());

        theaterHeaderLayout.addComponentsAndExpand(theaterLabel,theaterHelpButton);
        theaterLayout.addComponentsAndExpand(theaterHeaderLayout);
        initTheaters();

        filmTypeHeaderLayout.addComponentsAndExpand(filmTypeLabel,filmTypeHelpButton);
        filmTypeLayout.addComponentsAndExpand(filmTypeHeaderLayout);
        initFilmTypes();

        seanceLayout.addComponentsAndExpand(seanceLabel,seanceHelpButton);
        seanceLayout.addComponentsAndExpand(seanceHeaderLayout);
        initSeances();

        filmHeaderLayout.addComponentsAndExpand(filmLabel,filmHelpButton);
        filmLayout.addComponentsAndExpand(filmHeaderLayout);
        initFilms();

        operatorHeaderLayout.addComponentsAndExpand(operatorLabel,operatorHelpButton);
        operatorLayout.addComponentsAndExpand(operatorHeaderLayout);
        initOperators();

        headerLayout.addComponentsAndExpand(createButton,setButton,deleteButton,nameLabel,exitButton);
        objectsLayout.addComponentsAndExpand(theaterLayout,filmTypeLayout,seanceLayout,filmLayout,operatorLayout);


    }

    private void initTheaters(){

    }

    private void initFilmTypes(){

    }

    private void initSeances(){

    }

    private void initFilms(){

    }

    private void initOperators(){

    }

    private void createButtonListener(){

    }

    private void setButtonListener(){

    }

    private void deleteButtonListener(){

    }

    private void helpButtonListener(){

    }

    private void exitButtonListener(){

    }

    private void theaterHelpButtonListener(){

    }

    private void filmTypeHelpButtonListener(){

    }

    private void seanceHelpButtonListener(){

    }

    private void filmHelpButtonListener(){

    }

    private void operatorHelpButtonListener(){

    }

}
