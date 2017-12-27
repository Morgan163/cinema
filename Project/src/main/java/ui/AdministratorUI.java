package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import exceptions.DependentObjectExistsException;
import model.Film;
import model.FilmType;
import model.Seance;
import model.Theater;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.lang3.StringUtils;
import ui.utils.Utils;
import ui.windows.*;

import javax.inject.Inject;
import java.util.ArrayList;
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
    private final Button theaterHelpButton = new Button(VaadinIcons.QUESTION_CIRCLE_O);

    //film type objects
    private final HorizontalLayout filmTypeHeaderLayout = new HorizontalLayout();
    private final Label filmTypeLabel = new Label("Жанры");
    private final Button filmTypeHelpButton = new Button(VaadinIcons.QUESTION_CIRCLE_O);

    //seance objects
    private final HorizontalLayout seanceHeaderLayout = new HorizontalLayout();
    private final Label seanceLabel = new Label("Сеансы");
    private final Button seanceHelpButton = new Button(VaadinIcons.QUESTION_CIRCLE_O);

    //film objects
    private final HorizontalLayout filmHeaderLayout = new HorizontalLayout();
    private final Label filmLabel = new Label("Фильмы");
    private final Button filmHelpButton = new Button(VaadinIcons.QUESTION_CIRCLE_O);

    //operator objects
    private final HorizontalLayout operatorHeaderLayout = new HorizontalLayout();
    private final Label operatorLabel = new Label("Операторы");
    private final Button operatorHelpButton = new Button(VaadinIcons.QUESTION_CIRCLE_O);

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
        if(Utils.checkUserRoleAndRedirectIfNeeded(user, UserRole.ADMIN)){
            getUI().getPage().setLocation(Utils.redirectToMainPage(getUI().getPage().getLocation().toString()));
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

        theaterHeaderLayout.addComponentsAndExpand(theaterLabel, theaterHelpButton);
        theaterLayout.addComponentsAndExpand(theaterHeaderLayout, theaterCheckBoxGroup);
        initTheaters("");

        filmTypeHeaderLayout.addComponentsAndExpand(filmTypeLabel, filmTypeHelpButton);
        filmTypeLayout.addComponentsAndExpand(filmTypeHeaderLayout, filmTypeCheckBoxGroup);
        initFilmTypes("");

        seanceHeaderLayout.addComponentsAndExpand(seanceLabel, seanceHelpButton);
        seanceLayout.addComponentsAndExpand(seanceHeaderLayout, seanceCheckBoxGroup);
        initSeances("");

        filmHeaderLayout.addComponentsAndExpand(filmLabel, filmHelpButton);
        filmLayout.addComponentsAndExpand(filmHeaderLayout, filmCheckBoxGroup);
        initFilms("");

        operatorHeaderLayout.addComponentsAndExpand(operatorLabel, operatorHelpButton);
        operatorLayout.addComponentsAndExpand(operatorHeaderLayout, operatorCheckBoxGroup);
        initOperators("");


        searchField.addValueChangeListener(e -> searchFieldChangeListener());
        headerLayout.addComponentsAndExpand(createButton, setButton, deleteButton, searchField, nameLabel, exitButton);
        headerLayout.setSizeUndefined();
        objectsLayout.addComponentsAndExpand(theaterLayout, filmTypeLayout, seanceLayout, filmLayout, operatorLayout);
        objectsLayout.setSizeUndefined();
        mainLayout.addComponentsAndExpand(headerLayout, objectsLayout);
        mainLayout.setSizeUndefined();

        setContent(mainLayout);
    }

    private void initTheaters(String filter) {
        Collection<Theater> theaters = dataManager.getAllTheaters();
        theaterCheckBoxGroup.setItemCaptionGenerator(item -> "Зал " + item.getTheaterNumber());
        if (StringUtils.isBlank(filter)) {
            theaterCheckBoxGroup.setItems(theaters);
        }else{
            Collection<Theater> filteredTheaters = new ArrayList<>();
            for(Theater theater:theaters){
                if(("Зал"+theater.getTheaterNumber()).contains(filter)){
                    filteredTheaters.add(theater);
                }
            }
            theaterCheckBoxGroup.setItems(filteredTheaters);
        }

    }

    private void initFilmTypes(String filter) {
        Collection<FilmType> filmTypes = dataManager.getAllFilmTypes();
        filmTypeCheckBoxGroup.setItemCaptionGenerator(item -> item.getFilmTypeName());
        if (StringUtils.isBlank(filter)) {
            filmTypeCheckBoxGroup.setItems(filmTypes);
        }else{
            Collection<FilmType> filteredFilmTypes = new ArrayList<>();
            for(FilmType filmType:filmTypes){
                if(filmType.getFilmTypeName().contains(filter)){
                    filteredFilmTypes.add(filmType);
                }
            }
            filmTypeCheckBoxGroup.setItems(filteredFilmTypes);
        }
    }

    private void initSeances(String filter) {
        Collection<Seance> seances = dataManager.getAllSeances();
        seanceCheckBoxGroup.setItemCaptionGenerator(item -> "Сеанс " + item.getFilm().getFilmName() + "\n" +
                item.getSeanceStartDate().getTime());
        if (StringUtils.isBlank(filter)) {
            seanceCheckBoxGroup.setItems(seances);
        }else{
            Collection<Seance> filteredSeances = new ArrayList<>();
            for (Seance seance:seances){
                if(("Сеанс"+seance.getFilm().getFilmName()+seance.getSeanceStartDate().getTime().toString()).
                        contains(filter)){
                    filteredSeances.add(seance);
                }
            }
            seanceCheckBoxGroup.setItems(filteredSeances);
        }
    }

    private void initFilms(String filter) {
        Collection<Film> films = dataManager.getAllFilms();
        filmCheckBoxGroup.setItemCaptionGenerator(item -> item.getFilmName());
        if (StringUtils.isBlank(filter)) {
            filmCheckBoxGroup.setItems(films);
        }else{
            Collection<Film> filteredFilms = new ArrayList<>();
            for (Film film:films){
                if(film.getFilmName().contains(filter)){
                    filteredFilms.add(film);
                }
            }
            filmCheckBoxGroup.setItems(filteredFilms);
        }
    }

    private void initOperators(String filter) {
        Collection<User> operators = dataManager.getAllOperators();
        operatorCheckBoxGroup.setItemCaptionGenerator(item -> item.getLogin());
        if (StringUtils.isBlank(filter)) {
            operatorCheckBoxGroup.setItems(operators);
        }else{
            Collection<User> filteredOperators = new ArrayList<>();
            for (User operator:operators){
                if(operator.getLogin().contains(filter)){
                    filteredOperators.add(operator);
                }
            }
            operatorCheckBoxGroup.setItems(filteredOperators);
        }
    }


    private void createButtonListener() {
        Window chooseObjectWindow = new ChooseObjectWindow(UI.getCurrent(), user, dataManager);
        UI.getCurrent().addWindow(chooseObjectWindow);
    }

    private void setButtonListener() {
        boolean isSelected = false;
        for (Theater theater : theaterCheckBoxGroup.getSelectedItems()) {
            isSelected = true;
            Window changeTheaterWindow = new TheaterWindow(UI.getCurrent(), user, dataManager, theater);
            UI.getCurrent().addWindow(changeTheaterWindow);
        }
        for (FilmType filmType : filmTypeCheckBoxGroup.getSelectedItems()) {
            isSelected = true;
            Window changeFilmTypeWindow = new FilmTypeWindow(UI.getCurrent(), user, dataManager, filmType);
            UI.getCurrent().addWindow(changeFilmTypeWindow);
        }
        for (Seance seance : seanceCheckBoxGroup.getSelectedItems()) {
            isSelected = true;
            Window changeSeanceWindow = new SeanceWindow(UI.getCurrent(), user, dataManager, seance);
            UI.getCurrent().addWindow(changeSeanceWindow);
        }
        for (Film film : filmCheckBoxGroup.getSelectedItems()) {
            isSelected = true;
            Window changeFilmWindow = new FilmWindow(UI.getCurrent(), user, dataManager, film);
            UI.getCurrent().addWindow(changeFilmWindow);
        }
        for (User operator : operatorCheckBoxGroup.getSelectedItems()) {
            isSelected = true;
            Window changeOperatorWindow = new OperatorWindow(UI.getCurrent(), user, dataManager, operator);
            UI.getCurrent().addWindow(changeOperatorWindow);
        }
        if (!isSelected) {
            showErrorWindow("Для редактирования должен быть выбран как минимум один объект");
        }

    }

    private void deleteButtonListener() {
        boolean isSelected = false;
        try {
            for (Theater theater : theaterCheckBoxGroup.getSelectedItems()) {
                isSelected = true;
                dataManager.removeTheater(theater);
                initTheaters("");
            }
            for (FilmType filmType : filmTypeCheckBoxGroup.getSelectedItems()) {
                isSelected = true;
                dataManager.removeFilmType(filmType);
                initFilmTypes("");
            }
            for (Seance seance : seanceCheckBoxGroup.getSelectedItems()) {
                isSelected = true;
                dataManager.removeSeance(seance);
                initSeances("");
            }
            for (Film film : filmCheckBoxGroup.getSelectedItems()) {
                isSelected = true;
                dataManager.removeFilm(film);
                initFilms("");
            }
            for (User operator : operatorCheckBoxGroup.getSelectedItems()) {
                isSelected = true;
                dataManager.removeUser(operator);
                initOperators("");
            }
        } catch (DependentObjectExistsException e) {
            showErrorWindow(e.getMessage());
            isSelected = true;
        }
        if (!isSelected) {
            showErrorWindow("Для удаления должен быть выбран как минимум один объект");
        }
    }

    private void helpButtonListener() {
        showInfoWindow(MAIN_INFO);
    }

    private void exitButtonListener() {
        Utils.redirectToMainPage(getUI().getPage().getLocation().toString());
    }

    private void theaterHelpButtonListener() {
        showInfoWindow(THEATER_INFO);
    }

    private void filmTypeHelpButtonListener() {
        showInfoWindow(FILM_TYPE_INFO);
    }

    private void seanceHelpButtonListener() {
        showInfoWindow(SEANCE_INFO);
    }

    private void filmHelpButtonListener() {
        showInfoWindow(FILM_INFO);
    }

    private void operatorHelpButtonListener() {
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

    private void searchFieldChangeListener() {
        if (searchField.getValue() != null) {
            String filter = searchField.getValue();
            initFilms(filter);
            initFilmTypes(filter);
            initOperators(filter);
            initSeances(filter);
            initTheaters(filter);
        }
    }


}
