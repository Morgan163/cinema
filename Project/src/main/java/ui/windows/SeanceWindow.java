package ui.windows;

import com.vaadin.ui.*;
import model.Film;
import model.Seance;
import model.Theater;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.Collection;

public class SeanceWindow extends AbstractCreateWindow {
    private Seance seance;

    private final ComboBox<Film> filmComboBox = new ComboBox<>("Название фильма");
    private final ComboBox<Theater> theaterComboBox = new ComboBox<>("Зал");
    private final DateTimeField dateTimeField = new DateTimeField("Время");
    private final TextField priceField = new TextField("Базовая цена");
    private final Button saveButton = new Button("Сохранить");
    private final FormLayout formLayout = new FormLayout();

    public SeanceWindow(String caption, UI rootUI, User user, DataManager dataManager) {
        super(caption, rootUI, user, dataManager);
    }

    public SeanceWindow(String caption, UI rootUI, User user, DataManager dataManager, Seance seance) {
        super(caption, rootUI, user, dataManager);
        this.seance = seance;
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
        formLayout.addComponents();
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
        
    }

    private void saveButtonClickListener(){

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
