package ui.windows;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.FilmType;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.lang3.StringUtils;

public class FilmTypeWindow extends AbstractCreateWindow {
    private FilmType filmType;

    private final FormLayout formLayout = new FormLayout();
    private final TextField typeNameField = new TextField("Название жанра");
    private final Button okButton = new Button("OK");

    public FilmTypeWindow( UI rootUI, User user, DataManager dataManager, FilmType filmType) {
        super("Редактирование жанра", rootUI, user, dataManager);
        this.filmType = filmType;
        initFields();
        init();
    }

    public FilmTypeWindow(UI rootUI, User user, DataManager dataManager) {
        super("Создание жанра",  rootUI, user, dataManager);
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
        okButton.addClickListener(e -> okButtonClickListener());
        formLayout.addComponents(typeNameField, okButton);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setContent(formLayout);
    }

    private void initFields() {
        typeNameField.setValue(filmType.getFilmTypeName());
    }

    private void redirectRoot() {
        String currentLocation = super.getRootUI().getPage().getLocation().toString();
        super.getRootUI().getPage().setLocation(currentLocation.substring(0, currentLocation.lastIndexOf("/") + 1)
                + super.getUser().getUserRole().getRoleName().toLowerCase());
    }

    private void okButtonClickListener() {
        if(!StringUtils.isBlank(typeNameField.getValue())){
            FilmType newFilmType = new FilmType(typeNameField.getValue());
            if(filmType==null) {
                super.getDataManager().createFilmType(newFilmType);
            }else{
                super.getDataManager().updateFilmType(newFilmType);
            }
            UserRole role = super.getUser().getUserRole();
            if (role != null) {
                redirectRoot();
            }
        }else{
            showErrorWindow("Необходимо ввести название жанра");
        }
    }
    private void showErrorWindow(String message) {
        Window errorWindow = new ErrorWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }


}
