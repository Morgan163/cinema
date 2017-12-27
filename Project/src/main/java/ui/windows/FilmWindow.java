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
import java.util.HashSet;
import java.util.Set;

public class FilmWindow extends AbstractCreateWindow {
    private Film film;

    private final TextField nameField = new TextField("Название фильма");
    private final ComboBox<FilmType> filmTypeComboBox = new ComboBox<>("Жанр");
    private final NativeSelect<AgeLimitType> ageLimitTypeNativeSelect = new NativeSelect<>("Возрастные " +
            "ограничения");
    private final FormLayout formLayout = new FormLayout();
    private final Button saveButton = new Button("Сохранить");

    public FilmWindow(UI rootUI, User user, DataManager dataManager) {
        super("Создание фильма", rootUI, user, dataManager);
        initComponents();
        init();
    }

    public FilmWindow( UI rootUI, User user, DataManager dataManager, Film film) {
        super("Редактирование фильма", rootUI, user, dataManager);
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
        nameField.addValueChangeListener(e -> nameFieldChangeListener());
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
            Film newFilm = new Film(nameField.getValue(), filmTypeComboBox.getValue(),
                    ageLimitTypeNativeSelect.getValue());
            Set<Film> films = new HashSet<>(super.getDataManager().getAllFilms());
            if((films.add(newFilm))&&(checkNameValue())) {
                if (film == null) {
                    super.getDataManager().createFilm(newFilm);
                } else {
                    newFilm.setFilmID(film.getFilmID());
                    super.getDataManager().updateFilm(newFilm);
                }
                UserRole role = super.getUser().getUserRole();
                if (role != null) {
                    redirectRoot();
                }
            }else{
                showErrorWindow("Такой фильм уже существует или неверные данные");
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

    private void nameFieldChangeListener(){
        if(!checkNameValue()){
            showErrorWindow("Название может содержать только буквы, цифры и символ тире");
        }
    }

    private boolean checkNameValue(){
        return nameField.getValue().matches("^[А-яA-z0-9-]+$");
    }
}
