package ui.windows;

import com.vaadin.ui.*;

public class HelpWindow extends Window {

    private String message;
    private FormLayout formLayout;

    public HelpWindow(String message) {
        super("Помощь");
        this.message = message;
        init();
        formLayout.setMargin(true);
        formLayout.setSizeFull();
        center();
        setHeight("70%");
        setWidth("70%");
        setModal(true);

    }

    private void init() {
        formLayout = new FormLayout();
        Button okButton = new Button("ОК");
        okButton.addClickListener(clickEvent -> close());
        TextArea textArea = new TextArea();
        textArea.setEnabled(false);
        textArea.setValue(message);
        textArea.setSizeFull();
        textArea.setRows(12);
        formLayout.addComponents(textArea, okButton);
        setContent(formLayout);
    }
}
