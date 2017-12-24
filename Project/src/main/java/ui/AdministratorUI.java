package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import model.Film;
import model.FilmType;
import model.Seance;
import model.Theater;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import modeloperations.impl.DataManagerImpl;
import ui.utils.Utils;

import javax.inject.Inject;
import java.util.Collection;

@CDIUI("admin")
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
    private final Button createButton = new Button("Создать");
    private final Button setButton = new Button("Изменить");
    private final Button deleteButton = new Button("Удалить");
    private final Button exitButton = new Button("Выйти");
    private final Button helpButton = new Button("Справка");
    private final Label nameLabel;
    private final TextField searchField = new TextField("Поиск");

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

    private CheckBoxGroup<Theater> theaterCheckBoxGroup;
    private CheckBoxGroup<FilmType> filmTypeCheckBoxGroup;
    private CheckBoxGroup<Seance> seanceCheckBoxGroup;
    private CheckBoxGroup<Film> filmCheckBoxGroup;
    private CheckBoxGroup<User> operatorCheckBoxGroup;

    private DataManager dataManager;

    public AdministratorUI() {
        nameLabel = new Label();
        theaterCheckBoxGroup = new CheckBoxGroup<>();
        filmTypeCheckBoxGroup = new CheckBoxGroup<>();
        seanceCheckBoxGroup = new CheckBoxGroup<>();
        filmCheckBoxGroup = new CheckBoxGroup<>();
        operatorCheckBoxGroup = new CheckBoxGroup<>();

        dataManager = new DataManagerImpl();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
       /* if(Utils.checkUserRoleAndRedirectIfNeeded(user,UserRole.ADMIN)){
            getUI().getPage().setLocation(Utils.redirectToMainPage(getUI().getPage().getLocation().toString()));
        }*/

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
        theaterLayout.addComponentsAndExpand(theaterHeaderLayout, theaterCheckBoxGroup);
        initTheaters();

        filmTypeHeaderLayout.addComponentsAndExpand(filmTypeLabel,filmTypeHelpButton);
        filmTypeLayout.addComponentsAndExpand(filmTypeHeaderLayout, filmTypeCheckBoxGroup);
        initFilmTypes();

        seanceLayout.addComponentsAndExpand(seanceLabel,seanceHelpButton);
        seanceLayout.addComponentsAndExpand(seanceHeaderLayout, seanceCheckBoxGroup);
        initSeances();

        filmHeaderLayout.addComponentsAndExpand(filmLabel,filmHelpButton);
        filmLayout.addComponentsAndExpand(filmHeaderLayout, filmCheckBoxGroup);
        initFilms();

        operatorHeaderLayout.addComponentsAndExpand(operatorLabel,operatorHelpButton);
        operatorLayout.addComponentsAndExpand(operatorHeaderLayout, operatorCheckBoxGroup);
        initOperators();

        headerLayout.addComponentsAndExpand(createButton,setButton,deleteButton, searchField,nameLabel,exitButton);
        objectsLayout.addComponentsAndExpand(headerLayout,theaterLayout,filmTypeLayout,seanceLayout,filmLayout,operatorLayout);

        setContent(objectsLayout);
    }

    private void initTheaters(){
      /*  Collection<Theater> theaters = dataManager.getAllTheaters();
        theaterCheckBoxGroup.setItemCaptionGenerator(item->"Зал "+item.getTheaterNumber());

        theaterCheckBoxGroup.setItems(theaters);*/

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
