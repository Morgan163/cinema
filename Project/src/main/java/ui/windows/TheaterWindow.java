package ui.windows;

import com.vaadin.ui.UI;
import model.Theater;
import model.user.User;
import modeloperations.DataManager;

public class TheaterWindow extends AbstractCreateWindow {
    private Theater theater;

    public TheaterWindow( UI rootUI, User user, DataManager dataManager, Theater theater) {
        super("Редактирование зала", rootUI, user, dataManager);
        this.theater = theater;
    }

    public TheaterWindow( UI rootUI, User user, DataManager dataManager) {
        super("Создание зала", rootUI, user, dataManager);
    }
}
