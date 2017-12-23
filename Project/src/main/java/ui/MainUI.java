package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 * Created by niict on 24.12.2017.
 */
@CDIUI("")
@Theme("mytheme")
public class MainUI extends UI {
    private VerticalLayout mainLayout;
    public MainUI(){
        mainLayout = new VerticalLayout();
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initTopBar();
        setContent(mainLayout);
    }

    private void initTopBar() {
        HorizontalLayout topBarLayout = new HorizontalLayout();
        Label filtersLabel = new Label("Фильтры:");
        topBarLayout.addComponents(filtersLabel);
        mainLayout.addComponent(topBarLayout);
    }
}
