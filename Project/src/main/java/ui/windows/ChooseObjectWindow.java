package ui.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;

public class ChooseObjectWindow extends AbstractCreateWindow{

    private final FormLayout formLayout = new FormLayout();
    private final Button theaterButton = new Button("Зал");
    private final Button seanceButton = new Button("Сеанс");
    private final Button filmButton = new Button("Фильм");
    private final Button filmTypeButton = new Button("Жанр");
    private final Button operatorButton = new Button("Оператор");

    public ChooseObjectWindow(UI rootUI, User user, DataManager dataManager) {
        super("Выберите объект для создания",rootUI,user,dataManager);
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
        theaterButton.addClickListener(e->theaterButtonClickListener());
        seanceButton.addClickListener(e->seanceButtonClickListener());
        filmButton.addClickListener(e->filmButtonClickListener());
        filmTypeButton.addClickListener(e->filmTypeButtonClickListener());
        operatorButton.addClickListener(e->operatorButtonClickListener());
        formLayout.addComponents(theaterButton,seanceButton,filmButton,filmTypeButton,operatorButton);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setContent(formLayout);
    }

    private void redirectRoot() {
        String currentLocation = super.getRootUI().getPage().getLocation().toString();
        super.getRootUI().getPage().setLocation(currentLocation.substring(0, currentLocation.lastIndexOf("/") + 1)
                + super.getUser().getUserRole().getRoleName().toLowerCase());
    }

    private void theaterButtonClickListener(){
        Window createTheaterWindow = new TheaterWindow(UI.getCurrent(),super.getUser(), super.getDataManager());
        UI.getCurrent().addWindow(createTheaterWindow);
    }

    private void seanceButtonClickListener(){
        Window createSeanceWindow = new SeanceWindow(UI.getCurrent(),super.getUser(), super.getDataManager());
        UI.getCurrent().addWindow(createSeanceWindow);
    }

    private void filmButtonClickListener(){
        Window createFilmWindow = new FilmWindow(UI.getCurrent(),super.getUser(), super.getDataManager());
        UI.getCurrent().addWindow(createFilmWindow);
    }

    private void filmTypeButtonClickListener(){
        Window createFilmTypeWindow = new FilmTypeWindow(UI.getCurrent(),super.getUser(), super.getDataManager());
        UI.getCurrent().addWindow(createFilmTypeWindow);
    }

    private void operatorButtonClickListener(){
        Window createOperatorWindow = new OperatorWindow(UI.getCurrent(),super.getUser(), super.getDataManager());
        UI.getCurrent().addWindow(createOperatorWindow);
    }
}
