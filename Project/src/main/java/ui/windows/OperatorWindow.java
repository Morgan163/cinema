package ui.windows;

import com.vaadin.ui.*;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class OperatorWindow extends AbstractCreateWindow {

    private User operator;

    private final TextField surnameField = new TextField("Фамилия");
    private final TextField nameField = new TextField("Имя");
    private final TextField secondNameField = new TextField("Отчество");
    private final TextField login = new TextField("Логин");
    private final PasswordField passwordField = new PasswordField("Пароль");
    private final Button saveButton = new Button("Сохранить");
    private final FormLayout formLayout = new FormLayout();


    public OperatorWindow(UI rootUI, User user, DataManager dataManager, User user1) {
        super("Редактирование оператора", rootUI, user, dataManager);
        this.operator = user1;
        initValues();
        init();
    }

    public OperatorWindow(UI rootUI, User operator, DataManager dataManager) {
        super("Создание оператора", rootUI, operator, dataManager);
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
        surnameField.addValueChangeListener(e -> surnameFieldChangeListener());
        secondNameField.addValueChangeListener(e -> secondnameFieldChangeListener());
        login.addValueChangeListener(e -> loginFieldChangeListener());
        passwordField.addValueChangeListener(e -> passwordFieldChangeListener());
        saveButton.addClickListener(e -> saveButtonClickListener());
        formLayout.addComponents(nameField, surnameField, secondNameField, login, passwordField, saveButton);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setContent(formLayout);
    }

    private void initValues() {
        nameField.setValue(operator.getName());
        surnameField.setValue(operator.getSecondName());
        secondNameField.setValue(operator.getMiddleName());
        login.setValue(operator.getLogin());
        passwordField.setValue("111111");
    }

    private void saveButtonClickListener() {
        if (StringUtils.isBlank(nameField.getValue())) {
            showErrorWindow("Имя должно быть указано");
        } else if (StringUtils.isBlank(surnameField.getValue())) {
            showErrorWindow("Фамилия должна быть указана");
        } else if (StringUtils.isBlank(secondNameField.getValue())) {
            showErrorWindow("Отчество должно быть указано");
        } else if (StringUtils.isBlank(login.getValue())) {
            showErrorWindow("Логин должен быть указан");
        } else if (StringUtils.isBlank(passwordField.getValue())) {
            showErrorWindow("Пароль должен быть указан");
        } else {
            User newUser = new User(nameField.getValue(), surnameField.getValue(), secondNameField.getValue(),
                    login.getValue(), passwordField.getValue(), UserRole.OPERATOR);
            Set<User> operators = new HashSet<>(super.getDataManager().getAllOperators());
            if (checkValues()) {
                if (operator == null) {
                    if (operators.add(newUser)) {
                        super.getDataManager().createUser(newUser);
                    } else {
                        showErrorWindow("Такой оператор уже существует");
                    }
                } else {
                    newUser.setUserID(operator.getUserID());
                    super.getDataManager().updateUser(newUser);
                }
                UserRole role = super.getUser().getUserRole();
                if (role != null) {
                    redirectRoot();
                }

            } else {
                showErrorWindow("Неверный формат данных");
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

    private void nameFieldChangeListener() {
        if (!checkNameValue(nameField.getValue())) {
            showErrorWindow("Имя должно содержать только русские буквы");
        }
    }

    private void surnameFieldChangeListener() {
        if (!checkNameValue(surnameField.getValue())) {
            showErrorWindow("Фамилия должна содержать только русские буквы");
        }
    }

    private void secondnameFieldChangeListener() {
        if (!checkNameValue(secondNameField.getValue())) {
            showErrorWindow("Отчество должно содержать только русские буквы");
        }
    }

    private void loginFieldChangeListener() {
        if (!checkLogin(login.getValue())) {
            showErrorWindow("Логин может содержать только буквы, цифры и символы -,_");
        }
    }

    private void passwordFieldChangeListener() {
        if (!checkLogin(passwordField.getValue())) {
            showErrorWindow("Пароль может содержать только буквы, цифры и символы -,_");
        }
    }


    private boolean checkNameValue(String value) {
        return value.matches("^[А-я]+$");
    }

    private boolean checkLogin(String loginStr) {
        return loginStr.matches("^[А-яA-z0-9-_]+$");
    }

    private boolean checkValues() {
        return checkNameValue(nameField.getValue()) && checkNameValue(surnameField.getValue()) &&
                checkNameValue(secondNameField.getValue()) && checkLogin(login.getValue()) &&
                checkLogin(passwordField.getValue());
    }
}
