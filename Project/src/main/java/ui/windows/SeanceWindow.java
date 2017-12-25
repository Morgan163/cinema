package ui.windows;

import com.vaadin.ui.*;
import model.Film;
import model.Seance;
import model.Theater;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.lang3.StringUtils;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.GregorianCalendar;

public class SeanceWindow extends AbstractCreateWindow {
    private Seance seance;

    private final ComboBox<Film> filmComboBox = new ComboBox<>("Название фильма");
    private final ComboBox<Theater> theaterComboBox = new ComboBox<>("Зал");
    private final DateTimeField dateTimeField = new DateTimeField("Время");
    private final TextField priceField = new TextField("Базовая цена");
    private final Button saveButton = new Button("Сохранить");
    private final FormLayout formLayout = new FormLayout();

    public SeanceWindow( UI rootUI, User user, DataManager dataManager) {
        super("Создание сеанса", rootUI, user, dataManager);
        initComponents();
        init();
    }

    public SeanceWindow( UI rootUI, User user, DataManager dataManager, Seance seance) {
        super("Редактирование сеанса", rootUI, user, dataManager);
        this.seance = seance;
        initComponents();
        initValues();
        init();
    }

    private void init(){
        addCloseListener((CloseListener) closeEvent -> {
            UserRole role = super.getUser().getUserRole();
            if (role != null) {
                redirectRoot();
            }
        });
        setModal(true);
        setResizable(false);
        center();
        setSizeUndefined();
        formLayout.addComponents(filmComboBox,theaterComboBox,dateTimeField,priceField,saveButton);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setContent(formLayout);
    }

    private void initComponents(){
        Collection<Film> films = super.getDataManager().getAllFilms();
        filmComboBox.setItems(films);
        filmComboBox.setItemCaptionGenerator(Film::getFilmName);
        Collection<Theater> theaters = super.getDataManager().getAllTheaters();
        theaterComboBox.setItems(theaters);
        theaterComboBox.setItemCaptionGenerator(item->"Зал #" +item.getTheaterNumber());
        saveButton.addClickListener(e->saveButtonClickListener());
    }

    private void initValues(){
        filmComboBox.setValue(seance.getFilm());
        theaterComboBox.setValue(super.getDataManager().getTheaterBySeance(seance));
        dateTimeField.setValue(LocalDateTime.ofInstant(seance.getSeanceStartDate().toInstant(),
                ZoneId.systemDefault()));
        priceField.setValue(String.valueOf(seance.getPriceValue()));
    }

    private void saveButtonClickListener(){
        if(filmComboBox.getValue()==null){
            showErrorWindow("Фильм должен быть выбран");
        }else if(theaterComboBox.getValue()==null){
            showErrorWindow("Зал должен быть выбран");
        }else if(dateTimeField.getValue()==null){
            showErrorWindow("Время сеанса должно быть выбрано");
        }else if(StringUtils.isBlank(priceField.getValue())){
            showErrorWindow("Цена должна быть указана");
        }else{
            super.getDataManager().createSeanceForTheater(new Seance(filmComboBox.getValue(),
                    Double.valueOf(priceField.getValue()),
            GregorianCalendar.from(dateTimeField.getValue().atZone(ZoneId.systemDefault()))),
                    theaterComboBox.getValue());
            UserRole role = super.getUser().getUserRole();
            if (role != null) {
                redirectRoot();
            }
        }
    }

    private void redirectRoot() {
        String currentLocation = super.getRootUI().getPage().getLocation().toString();
        super.getRootUI().getPage().setLocation(currentLocation.substring(0, currentLocation.lastIndexOf("/") + 1)
                + super.getUser().getUserRole().getRoleName().toLowerCase());
    }
    private void showErrorWindow(String message) {
        Window errorWindow = new ErrorWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }
}
