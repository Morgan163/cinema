package ui.windows;

import com.vaadin.ui.*;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.lang3.StringUtils;

public class OperatorWindow extends AbstractCreateWindow {

    private User user;

    private final TextField surnameField = new TextField("Фамилия");
    private final TextField nameField = new TextField("Имя");
    private final TextField secondNameField = new TextField("Отчество");
    private final TextField login = new TextField("Логин");
    private final PasswordField passwordField = new PasswordField("Пароль");
    private final Button saveButton = new Button("Сохранить");
    private final FormLayout formLayout = new FormLayout();


    public OperatorWindow(UI rootUI, User user, DataManager dataManager, User user1) {
        super("Редактирование оператора", rootUI, user, dataManager);
        this.user = user1;
        initValues();
        init();
    }

    public OperatorWindow(UI rootUI, User user, DataManager dataManager) {
        super("Создание оператора", rootUI, user, dataManager);
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
        saveButton.addClickListener(e -> saveButtonClickListener());
        formLayout.addComponents(login, passwordField, saveButton);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setContent(formLayout);
    }

    private void initValues() {
        login.setValue(user.getLogin());
        passwordField.setValue("111111");
    }

    private void saveButtonClickListener() {
        if (StringUtils.isBlank(login.getValue())) {
            showErrorWindow("Логин должен быть указан");
        } else if (StringUtils.isBlank(passwordField.getValue())) {
            showErrorWindow("Пароль должен быть указанё");
        } else {
            super.getDataManager().createUser(new User(login.getValue(), passwordField.getValue(), UserRole.OPERATOR));
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
