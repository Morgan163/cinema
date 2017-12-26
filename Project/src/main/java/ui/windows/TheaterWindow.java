package ui.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import model.Theater;
import model.user.User;
import modeloperations.DataManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TheaterWindow extends AbstractCreateWindow {
    private Theater theater;
    private final List<List<Button>> lines = new ArrayList<>();

    private final HorizontalLayout mainLayout =  new HorizontalLayout();
    private final Button newLineButton = new Button("Новый ряд");
    private final Button deleteLineButton = new Button("Удалить ряд");
    private final Button genericButton = new Button();
    private final Button vipButton = new Button();
    private final Button saveButton = new Button("Сохранить");
    private final Button cancelButton = new Button("Отменить");

    private final Label genericLabel = new Label("Добавить стандартное место");
    private final Label vipLabel = new Label("Добавить VIP место");


    public TheaterWindow( UI rootUI, User user, DataManager dataManager, Theater theater) {
        super("Редактирование зала", rootUI, user, dataManager);
        this.theater = theater;
    }

    public TheaterWindow( UI rootUI, User user, DataManager dataManager) {
        super("Создание зала", rootUI, user, dataManager);
    }

    private void init(){
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        setResizable(false);
        setSizeUndefined();
        center();
        setModal(true);
    }
}
