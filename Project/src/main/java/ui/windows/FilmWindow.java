package ui.windows;

import com.vaadin.ui.*;
import model.AgeLimitType;
import model.Film;
import model.FilmType;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class FilmWindow extends AbstractCreateWindow {
    private Film film;

    private final TextField nameField = new TextField("Название фильма");
    private final ComboBox<FilmType> filmTypeComboBox = new ComboBox<>("Жанр");
    private final NativeSelect<AgeLimitType> ageLimitTypeNativeSelect = new NativeSelect<>("Возрастные " +
            "ограничения");
    private final FormLayout formLayout = new FormLayout();
    private final Button saveButton = new Button("Сохранить");

    public FilmWindow(String caption, UI rootUI, User user, DataManager dataManager) {
        super(caption, rootUI, user, dataManager);
        initComponents();
        init();
    }

    public FilmWindow(String caption, UI rootUI, User user, DataManager dataManager, Film film) {
        super(caption, rootUI, user, dataManager);
        this.film = film;
        initComponents();
        initValues();
        init();
    }

    private void init() {
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
        formLayout.addComponents(nameField,filmTypeComboBox,ageLimitTypeNativeSelect,saveButton);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setContent(formLayout);
    }

    private void initComponents() {
        Collection<FilmType> filmTypes = super.getDataManager().getAllFilmTypes();
        filmTypeComboBox.setItems(filmTypes);
        filmTypeComboBox.setItemCaptionGenerator(FilmType::getFilmTypeName);
        ageLimitTypeNativeSelect.setItems(AgeLimitType.values());
        saveButton.addClickListener(e -> saveButtonClickLiistener());
    }

    private void initValues(){
        filmTypeComboBox.setValue(film.getFilmType());
        nameField.setValue(film.getFilmName());
        ageLimitTypeNativeSelect.setValue(film.getAgeLimitType());
    }

    private void saveButtonClickLiistener() {
        if(StringUtils.isBlank(nameField.getValue())){
            showErrorWindow("Название фильма не должно быт пустым");
        }else if(filmTypeComboBox.getValue()==null){
            showErrorWindow("Жанр не должен быть пустым");
        }else if(ageLimitTypeNativeSelect.getValue()==null){
            showErrorWindow("Возрастные ограничения не должна быть пустыми");
        }else{
            super.getDataManager().createFilm(new Film(nameField.getValue(), filmTypeComboBox.getValue(),
                    ageLimitTypeNativeSelect.getValue()));
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
