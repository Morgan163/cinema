package ui;

import com.vaadin.ui.*;
import model.user.User;
import model.user.UserRole;
import modeloperations.ModelOperations;

import javax.inject.Inject;

/**
 * Created by niict on 24.12.2017.
 */
public class LoginWindow extends Window {
    private static final String EMPTY_FIELDS_MESSAGE = "Необходимо заполнить все поля!";
    private static final String WRONG_CREDENTIALS = "Неверный логин или пароль!";
    private User user;
    private UI rootUI;
    private ModelOperations modelOperations;
    private TextField loginField;
    private PasswordField passwordField;
    private FormLayout formLayout;
    
    public LoginWindow(User user, ModelOperations modelOperations, UI root){
        super("Авторизация");
        this.rootUI = root;
        this.user = user;
        this.modelOperations = modelOperations;
        init();
    }
    
    private void init(){
        formLayout = new FormLayout();
        initContent();
        initButtons();
        addCloseListener((CloseListener) closeEvent -> {
            UserRole role = user.getUserRole();
            if (role != null){
                redirectRoot();
            }
        });
        setModal(true);
        setResizable(false);
        center();
        setWidth("50%");
        formLayout.setSizeFull();
    }

    private void initContent() {
        loginField = new TextField("Логин:");
        passwordField = new PasswordField("Пароль:");
        formLayout.addComponents(loginField, passwordField);
    }

    private void initButtons(){
        VerticalLayout buttonsLayout = new VerticalLayout();
        Button singInButton = new Button("Вход");
        Button cancelWindow = new Button("Отмена");
        singInButton.addClickListener((Button.ClickListener) clickEvent -> handleSingInClick());
        cancelWindow.addClickListener((Button.ClickListener) clickEvent -> handleCloseClick());
        buttonsLayout.addComponents(singInButton, cancelWindow);
        formLayout.addComponent(buttonsLayout);
    }

    private void handleSingInClick(){
        if (loginField.isEmpty() || passwordField.isEmpty()){
            showErrorWindow(EMPTY_FIELDS_MESSAGE);
        }
        else {
            user.setLogin(loginField.getValue());
            user.setPassword(passwordField.getValue());
            if (modelOperations.isUserExists(user)){
                modelOperations.authorize(user);
                close();
            }
            else {
                showErrorWindow(WRONG_CREDENTIALS);
            }
        }
    }

    private void showErrorWindow(String message) {
        Window errorWindow = new ErrorWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }

    private void handleCloseClick(){
        close();
    }

    private void redirectRoot() {
        String currentLocation = rootUI.getPage().getLocation().toString();
        rootUI.getPage().setLocation(currentLocation.substring(0, currentLocation.lastIndexOf("'/")) + user.getUserRole().getRoleName().toLowerCase());
    }
}
