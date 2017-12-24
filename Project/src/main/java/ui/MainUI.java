package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import model.AgeLimitType;
import model.Film;
import model.FilmType;
import model.Seance;
import model.user.User;
import modeloperations.DataManager;
import modeloperations.ModelOperations;
import ui.utils.FilterContext;
import ui.utils.MainUIUtils;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by niict on 24.12.2017.
 */
@CDIUI("")
@Theme("mytheme")
public class MainUI extends UI {
    @Inject
    private User user;
    @Inject
    private DataManager dataManager;
    @Inject
    private ModelOperations modelOperations;
    @Inject
    private MainUIUtils mainUIUtils;
    @Inject
    private FilterContext filterContext;
    private Collection<Seance> seances;

    private VerticalLayout mainLayout;

    public MainUI(){
        mainLayout = new VerticalLayout();
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
//        System.out.println("initializing");
//        seances = dataManager.getAllSeances();
//        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.groupSeancesByFilms(seances);
//        Set<FilmType> filmTypes = mainUIUtils.selectAllGenresFromFilms(filmSeancesMap.keySet());
//        initTopBar(seances, filmTypes);
        mainLayout.addComponentsAndExpand(new Label("Фильтры"));
        setContent(mainLayout);
    }
//
//    private void initTopBar(Collection<Seance> seances, Set<FilmType> filmTypes) {
//        System.out.println("initializing top bar");
//        Collection<Component> components = new ArrayList<>();
//        components.add(new Label("Фильтры:"));
//        components.add(buildFilmTypeComboBox(filmTypes));
//        components.add(buildTimeComboBox());
//        components.add(buildAgeLimitsComboBox());
//        components.add(buildLoginButton());
//        HorizontalLayout topBarLayout = new HorizontalLayout();
//        topBarLayout.addComponentsAndExpand((Component[])components.toArray());
//        mainLayout.addComponent(topBarLayout);
//    }
//
//    private Button buildLoginButton() {
//        Button loginButton = new Button("Вход");
//        loginButton.addClickListener((Button.ClickListener) clickEvent -> {
//            Window loginWindow = new LoginWindow(user, modelOperations, MainUI.this);
//            addWindow(loginWindow);
//        });
//        return loginButton;
//    }
//
//    private ComboBox<FilmType> buildFilmTypeComboBox(Collection<FilmType> filmTypes){
//        Set<FilmType> filmTypesWithEmptyValue = new HashSet<>(filmTypes);
//        filmTypesWithEmptyValue.add(null);
//        ComboBox<FilmType> filmTypeComboBox = new ComboBox<>("Жанр: ");
//        filmTypeComboBox.setItems(filmTypes);
//        filmTypeComboBox.setItemCaptionGenerator(filmType -> filmType == null? "Любой" : filmType.getFilmTypeName());
//        filmTypeComboBox.addValueChangeListener((HasValue.ValueChangeListener<FilmType>) this::handleFilmTypeValueChangedEvent);
//        return filmTypeComboBox;
//    }
//
//    private ComboBox<MainUIUtils.CalendarPair> buildTimeComboBox() {
//        Collection<MainUIUtils.CalendarPair> calendarPairs = mainUIUtils.buildCalendarPairs();
//        calendarPairs.add(null);
//        ComboBox<MainUIUtils.CalendarPair> calendarPairComboBox = new ComboBox<>("Время: ");
//        calendarPairComboBox.setItems(calendarPairs);
//        calendarPairComboBox.setItemCaptionGenerator(calendarPair -> calendarPair == null?
//                                                                    "Любое" :
//                                                                    String.format("%s - %s", calendarPair.getLeftCalendar().get(Calendar.HOUR_OF_DAY),
//                                                                                              calendarPair.getRightCalendar().get(Calendar.HOUR_OF_DAY)));
//        calendarPairComboBox.addValueChangeListener((HasValue.ValueChangeListener<MainUIUtils.CalendarPair>) this::handleTimeValueChangedEvent);
//        return calendarPairComboBox;
//    }
//
//    private ComboBox<AgeLimitType> buildAgeLimitsComboBox() {
//        Collection<AgeLimitType> ageLimitTypes = new ArrayList<>(Arrays.asList(AgeLimitType.values()));
//        ageLimitTypes.add(null);
//        ComboBox<AgeLimitType> ageLimitTypeComboBox = new ComboBox<>();
//        ageLimitTypeComboBox.setItemCaptionGenerator(ageLimitType -> ageLimitType ==  null? "Любое" : String.valueOf(ageLimitType.getAgeLimitValue())+"+");
//        ageLimitTypeComboBox.addValueChangeListener((HasValue.ValueChangeListener<AgeLimitType>) this::handleAgeLimitValueChangedEvent);
//        return ageLimitTypeComboBox;
//    }
//
//    private void handleFilmTypeValueChangedEvent(HasValue.ValueChangeEvent<FilmType> valueChangeEvent){
//        filterContext.setFilterParameter(FilterContext.FILM_TYPE_PARAMETER, valueChangeEvent.getValue());
//        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.selectFilmSeanceByContext(filterContext);
//        displayFilmSeancesMap(filmSeancesMap);
//    }
//
//    private void handleTimeValueChangedEvent(HasValue.ValueChangeEvent<MainUIUtils.CalendarPair> valueChangeEvent){
//        filterContext.setFilterParameter(FilterContext.TIME_RANGE_PARAMETER, valueChangeEvent.getValue());
//        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.selectFilmSeanceByContext(filterContext);
//        displayFilmSeancesMap(filmSeancesMap);
//    }
//
//    private void handleAgeLimitValueChangedEvent(HasValue.ValueChangeEvent<AgeLimitType> valueChangeEvent) {
//        filterContext.setFilterParameter(FilterContext.AGE_LIMIT_PARAMETER, valueChangeEvent.getValue());
//        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.selectFilmSeanceByContext(filterContext);
//        displayFilmSeancesMap(filmSeancesMap);
//    }
//
//    private void displayFilmSeancesMap(Map<Film, Collection<Seance>> filmSeancesMap) {
//    }
}
