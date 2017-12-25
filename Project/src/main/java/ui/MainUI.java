package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.HasValue;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import model.AgeLimitType;
import model.Film;
import model.FilmType;
import model.Seance;
import model.user.User;
import modeloperations.DataManager;
import modeloperations.ModelOperations;
import org.apache.log4j.Logger;
import ui.utils.FilterContext;
import ui.utils.MainUIUtils;
import ui.windows.BookingWindow;
import ui.windows.LoginWindow;

import javax.inject.Inject;
import java.io.File;
import java.util.*;

/**
 * Created by niict on 24.12.2017.
 */
@CDIUI("")
@Theme("mytheme")
public class MainUI extends UI {
    private static final Logger LOG = Logger.getLogger(MainUI.class);
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
    private VerticalLayout filmLayout;

    public MainUI() {
        mainLayout = new VerticalLayout();
        filmLayout = new VerticalLayout();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        System.out.println("initializing");
        seances = dataManager.getAllSeances();
        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.groupSeancesByFilms(seances);
        Set<FilmType> filmTypes = mainUIUtils.selectAllGenresFromFilms(filmSeancesMap.keySet());
        initTopBar(filmTypes);
        // mainLayout.addComponentsAndExpand(new Label("Фильтры"));
        displayFilmSeancesMap(filmSeancesMap);
        mainLayout.addComponentsAndExpand(filmLayout);
        setContent(mainLayout);
    }

    private void initTopBar(Set<FilmType> filmTypes) {
        System.out.println("initializing top bar");
        Collection<Component> components = new ArrayList<>();
        HorizontalLayout topBarLayout = new HorizontalLayout();
        topBarLayout.addComponentsAndExpand(new Label("Фильтры:"));
        topBarLayout.addComponentsAndExpand(buildFilmTypeComboBox(filmTypes));
        topBarLayout.addComponentsAndExpand(buildTimeComboBox());
        topBarLayout.addComponentsAndExpand(buildAgeLimitsComboBox());
        topBarLayout.addComponentsAndExpand(buildLoginButton());
        mainLayout.addComponent(topBarLayout);
    }

    private Button buildLoginButton() {
        Button loginButton = new Button("Вход");
        loginButton.addClickListener((Button.ClickListener) clickEvent -> {
            Window loginWindow = new LoginWindow(user, modelOperations, MainUI.this);
            addWindow(loginWindow);
        });
        return loginButton;
    }

    private ComboBox<FilmType> buildFilmTypeComboBox(Collection<FilmType> filmTypes) {
        Set<FilmType> filmTypesWithEmptyValue = new HashSet<>(filmTypes);
        filmTypesWithEmptyValue.add(null);
        ComboBox<FilmType> filmTypeComboBox = new ComboBox<>("Жанр: ");
        filmTypeComboBox.setItems(filmTypes);
        filmTypeComboBox.setItemCaptionGenerator(filmType -> filmType == null ? "Любой" : filmType.getFilmTypeName());
        filmTypeComboBox.addValueChangeListener((HasValue.ValueChangeListener<FilmType>) this::handleFilmTypeValueChangedEvent);
        return filmTypeComboBox;
    }

    private ComboBox<MainUIUtils.CalendarPair> buildTimeComboBox() {
        Collection<MainUIUtils.CalendarPair> calendarPairs = mainUIUtils.buildCalendarPairs();
        MainUIUtils.CalendarPair mock = mainUIUtils.getEmptyPair();
        ComboBox<MainUIUtils.CalendarPair> calendarPairComboBox = new ComboBox<>("Время: ");
        calendarPairComboBox.setItems(calendarPairs);
        calendarPairComboBox.setItemCaptionGenerator(calendarPair -> String.format("%s - %s", calendarPair.getLeftCalendar().get(Calendar.HOUR_OF_DAY),
                calendarPair.getRightCalendar().get(Calendar.HOUR_OF_DAY)));
        calendarPairComboBox.addValueChangeListener((HasValue.ValueChangeListener<MainUIUtils.CalendarPair>) this::handleTimeValueChangedEvent);
        return calendarPairComboBox;
    }

    private ComboBox<AgeLimitType> buildAgeLimitsComboBox() {
        Collection<AgeLimitType> ageLimitTypes = new ArrayList<>(Arrays.asList(AgeLimitType.values()));
        ComboBox<AgeLimitType> ageLimitTypeComboBox = new ComboBox<>("Возрастные ограничения: ");
        ageLimitTypeComboBox.setItems(ageLimitTypes);
        ageLimitTypeComboBox.setItemCaptionGenerator(ageLimitType -> String.valueOf(ageLimitType.getAgeLimitValue()) + "+");
        ageLimitTypeComboBox.addValueChangeListener((HasValue.ValueChangeListener<AgeLimitType>) this::handleAgeLimitValueChangedEvent);
        return ageLimitTypeComboBox;
    }

    private void handleFilmTypeValueChangedEvent(HasValue.ValueChangeEvent<FilmType> valueChangeEvent) {
        filterContext.setFilterParameter(FilterContext.FILM_TYPE_PARAMETER, valueChangeEvent.getValue());
        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.selectFilmSeanceByContext(filterContext);
        displayFilmSeancesMap(filmSeancesMap);
    }

    private void handleTimeValueChangedEvent(HasValue.ValueChangeEvent<MainUIUtils.CalendarPair> valueChangeEvent) {
        filterContext.setFilterParameter(FilterContext.TIME_RANGE_PARAMETER, valueChangeEvent.getValue());
        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.selectFilmSeanceByContext(filterContext);
        displayFilmSeancesMap(filmSeancesMap);
    }

    private void handleAgeLimitValueChangedEvent(HasValue.ValueChangeEvent<AgeLimitType> valueChangeEvent) {
        filterContext.setFilterParameter(FilterContext.AGE_LIMIT_PARAMETER, valueChangeEvent.getValue());
        Map<Film, Collection<Seance>> filmSeancesMap = mainUIUtils.selectFilmSeanceByContext(filterContext);
        displayFilmSeancesMap(filmSeancesMap);
    }

    private void displayFilmSeancesMap(Map<Film, Collection<Seance>> filmSeancesMap) {
        filmLayout.removeAllComponents();
        int filmsCount = filmSeancesMap.size();
        Iterator<Film> filmIterator = filmSeancesMap.keySet().iterator();
        VerticalLayout filmColumns = new VerticalLayout();
        for (int rows = 0; rows < filmsCount; rows++) {
            HorizontalLayout filmRow = new HorizontalLayout();
            for (int columns = 0; columns < 2; columns++) {
                if (filmIterator.hasNext()) {
                    Film currentFilm = filmIterator.next();
                    filmRow.addComponentsAndExpand(buildFilmBlock(currentFilm, filmSeancesMap.get(currentFilm)));
                }
            }
            filmLayout.addComponentsAndExpand(filmRow);
        }
    }

    private HorizontalLayout buildFilmBlock(Film currentFilm, Collection<Seance> seances) {
        HorizontalLayout filmBlock = new HorizontalLayout();
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();
        FileResource resource = new FileResource(new File(basepath +
                "/WEB-INF/images/picture.png"));
        Image image = new Image("", resource);
        filmBlock.addComponents(image);
        VerticalLayout verticalLayout = buildFilmDescription(currentFilm, seances);
        filmBlock.addComponents(verticalLayout);
        return filmBlock;
    }

    private VerticalLayout buildFilmDescription(Film currentFilm, Collection<Seance> seances) {
        VerticalLayout description = new VerticalLayout();
        description.addComponentsAndExpand(new Label(currentFilm.getFilmName()),
                new Label(currentFilm.getFilmType().getFilmTypeName()),
                new Label(currentFilm.getAgeLimitType().getAgeLimitValue() + "+"));
        for (Seance seance : seances) {
            Button seanceButton = new Button(getTimeCaptureForSeance(seance));
            seanceButton.addClickListener(clickEvent -> showBookingWindowForSeance(seance));
            description.addComponent(seanceButton);
        }
        description.setSizeUndefined();
        return description;
    }

    private void showBookingWindowForSeance(Seance seance) {
        Window bookingWindow = new BookingWindow(seance, dataManager, modelOperations);
        addWindow(bookingWindow);
    }

    private String getTimeCaptureForSeance(Seance seance){
        String startMinutes = seance.getSeanceStartDate().get(Calendar.MINUTE) < 10 ?
                "0" + seance.getSeanceStartDate().get(Calendar.MINUTE) :
                String.valueOf(seance.getSeanceStartDate().get(Calendar.MINUTE));
        return String.format("%s:%s", seance.getSeanceStartDate().get(Calendar.HOUR_OF_DAY), startMinutes);
    }
}
