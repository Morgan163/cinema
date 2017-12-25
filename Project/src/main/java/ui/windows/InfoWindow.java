package ui.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * Created by niict on 25.12.2017.
 */
public class InfoWindow extends Window {
    private String message;
    private FormLayout formLayout;

    public InfoWindow(String message) {
        super("Информация");
        this.message = message;
        init();
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        setSizeUndefined();
        center();
        setModal(true);

    }

    private void init() {
        formLayout = new FormLayout();
        Button okButton = new Button("ОК");
        okButton.addClickListener(clickEvent -> close());
        formLayout.addComponents(new Label(message), okButton);
        setContent(formLayout);
    }
}
