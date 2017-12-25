package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import model.Film;
import model.FilmType;
import model.Seance;
import model.Theater;
import model.user.User;
import modeloperations.DataManager;
import ui.windows.ErrorWindow;
import ui.windows.InfoWindow;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Locale;

@CDIUI("admin")
@Theme("mytheme")
public class AdministratorUI extends UI {
    private static final String MAIN_INFO = "Форма администратора, где вы можете управлять имеющимися объектами" +
            " кинотеатра";
    private static final String THEATER_INFO = "Редактируйте и удаляйте имеющиеся залы";
    private static final String FILM_TYPE_INFO = "Редактируйте и удаляйте имеющиеся жанры";
    private static final String FILM_INFO = "Редактируйте и удаляйте имеющиеся фильмы";
    private static final String SEANCE_INFO = "Редактируйте и удаляйте имеющиеся сеансы";
    private static final String OPERATOR_INFO = "Редактируйте и удаляйте операторов системы";

    @Inject
    private User user;

    private final HorizontalLayout headerLayout = new HorizontalLayout();
    private final VerticalLayout mainLayout = new VerticalLayout();
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
    private final Label seanceLabel = new Label("Сеансы");
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

    @Inject
    private DataManager dataManager;

    public AdministratorUI() {
        nameLabel = new Label();
        theaterCheckBoxGroup = new CheckBoxGroup<>();
        filmTypeCheckBoxGroup = new CheckBoxGroup<>();
        seanceCheckBoxGroup = new CheckBoxGroup<>();
        filmCheckBoxGroup = new CheckBoxGroup<>();
        operatorCheckBoxGroup = new CheckBoxGroup<>();
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

        seanceHeaderLayout.addComponentsAndExpand(seanceLabel,seanceHelpButton);
        seanceLayout.addComponentsAndExpand(seanceHeaderLayout, seanceCheckBoxGroup);
        initSeances();

        filmHeaderLayout.addComponentsAndExpand(filmLabel,filmHelpButton);
        filmLayout.addComponentsAndExpand(filmHeaderLayout, filmCheckBoxGroup);
        initFilms();

        operatorHeaderLayout.addComponentsAndExpand(operatorLabel,operatorHelpButton);
        operatorLayout.addComponentsAndExpand(operatorHeaderLayout, operatorCheckBoxGroup);
        initOperators();

        headerLayout.addComponentsAndExpand(createButton,setButton,deleteButton, searchField,nameLabel,exitButton);
        headerLayout.setSizeUndefined();
        objectsLayout.addComponentsAndExpand(theaterLayout,filmTypeLayout,seanceLayout,filmLayout,operatorLayout);
        objectsLayout.setSizeUndefined();
        mainLayout.addComponentsAndExpand(headerLayout,objectsLayout);
        mainLayout.setSizeUndefined();

        setContent(mainLayout);
    }

    private void initTheaters(){
        Collection<Theater> theaters = dataManager.getAllTheaters();
        theaterCheckBoxGroup.setItemCaptionGenerator(item->"Зал "+item.getTheaterNumber());

        theaterCheckBoxGroup.setItems(theaters);

    }

    private void initFilmTypes(){
        Collection<FilmType> filmTypes = dataManager.getAllFilmTypes();
        filmTypeCheckBoxGroup.setItemCaptionGenerator(item -> item.getFilmTypeName());
        filmTypeCheckBoxGroup.setItems(filmTypes);
    }

    private void initSeances(){
        Locale locale = Locale.getDefault();
        Collection<Seance> seances = dataManager.getAllSeances();
        seanceCheckBoxGroup.setItemCaptionGenerator(item -> "Сеанс "+item.getFilm().getFilmName()+"\n" +
                item.getSeanceStartDate().getTime());
        seanceCheckBoxGroup.setItems(seances);
    }

    private void initFilms(){
        Collection<Film> films = dataManager.getAllFilms();
        filmCheckBoxGroup.setItemCaptionGenerator(item ->  item.getFilmName());
        filmCheckBoxGroup.setItems(films);
    }

    private void initOperators(){
        Collection<User> operators = dataManager.getAllOperators();
        operatorCheckBoxGroup.setItemCaptionGenerator(item -> item.getLogin());
        operatorCheckBoxGroup.setItems(operators);
    }

    private void createButtonListener(){

    }

    private void setButtonListener(){

    }

    private void deleteButtonListener(){

    }

    private void helpButtonListener(){
        showInfoWindow(MAIN_INFO);
    }

    private void exitButtonListener(){

    }

    private void theaterHelpButtonListener(){
        showInfoWindow(THEATER_INFO);
    }

    private void filmTypeHelpButtonListener(){
        showInfoWindow(FILM_TYPE_INFO);
    }

    private void seanceHelpButtonListener(){
        showInfoWindow(SEANCE_INFO);
    }

    private void filmHelpButtonListener(){
        showInfoWindow(FILM_INFO);
    }

    private void operatorHelpButtonListener(){
        showInfoWindow(OPERATOR_INFO);
    }

    private void showErrorWindow(String message) {
        Window errorWindow = new ErrorWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }

    private void showInfoWindow(String message) {
        Window errorWindow = new InfoWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }



}
