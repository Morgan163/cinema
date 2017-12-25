package ui.windows;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import model.user.User;
import modeloperations.DataManager;

public class AbstractCreateWindow extends Window {
    private UI rootUI;
    private User user;
    private DataManager dataManager;

    public UI getRootUI() {
        return rootUI;
    }

    public void setRootUI(UI rootUI) {
        this.rootUI = rootUI;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public AbstractCreateWindow(String caption, UI rootUI, User user, DataManager dataManager) {

        super(caption);
        this.rootUI = rootUI;
        this.user = user;
        this.dataManager = dataManager;
    }
}
