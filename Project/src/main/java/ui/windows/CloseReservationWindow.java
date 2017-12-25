package ui.windows;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import model.Seance;
import model.Seat;
import modeloperations.ModelOperations;
import org.apache.commons.collections4.CollectionUtils;
import ui.OperatorUI;

import java.util.Collection;

/**
 * Created by niict on 25.12.2017.
 */
public class CloseReservationWindow extends Window {
    ModelOperations modelOperations;
    OperatorUI root;
    private VerticalLayout mainLayout;
    private Label priceLabel;
    private Button confirmButton;
    private String reservationCode;

    public CloseReservationWindow(ModelOperations modelOperations, OperatorUI operatorUI) {
        super("Закрыть бронь");
        this.modelOperations = modelOperations;
        this.root = operatorUI;
        init();
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        setResizable(false);
        setSizeUndefined();
        center();
        setModal(true);
        setContent(mainLayout);
    }

    private void init() {
        mainLayout = new VerticalLayout();
        priceLabel = new Label();
        mainLayout.addComponent(buildCodeControls());
        mainLayout.addComponent(priceLabel);
        mainLayout.addComponent(buildButtons());
    }

    private HorizontalLayout buildCodeControls() {
        HorizontalLayout controls = new HorizontalLayout();
        TextField codeField = new TextField();
        Button okButton = new Button("ОК");
        codeField.addValueChangeListener(this::handleCodeFieldValueChangedEvent);
        okButton.addClickListener(clickEvent -> handleOkButtonClockListener(codeField));
        controls.addComponents(codeField, okButton);
        codeField.setWidth("80%");
        okButton.setWidth("20%");
        return controls;
    }

    private void handleCodeFieldValueChangedEvent(HasValue.ValueChangeEvent<String> valueChangeEvent) {
        confirmButton.setVisible(false);
        priceLabel.setCaption("");
    }

    private void handleOkButtonClockListener(TextField codeField) {
        reservationCode = codeField.getValue();
        Collection<Seat> seats = modelOperations.getSeatsByBookingKey(reservationCode);
        if (CollectionUtils.isEmpty(seats)){
            priceLabel.setCaption("Не найдено брони по данному коду");
        }
        else {
            Seance seance = modelOperations.getSeanceByBookingKey(reservationCode);
            double price = modelOperations.calculatePriceForSeatsAndSeance(seats, seance);
            priceLabel.setCaption(String.format("К оплате: %f рублей", price));
            confirmButton.setVisible(true);
        }
    }

    private HorizontalLayout buildButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        confirmButton = new Button("Оплачено");
        confirmButton.addClickListener(clickEvent -> handleConfirmButtonClick());
        Button cancelButton = new Button("Отмена");
        cancelButton.addClickListener(clickEvent -> close(false));
        buttonsLayout.addComponents(confirmButton, cancelButton);
        confirmButton.setVisible(false);
        return buttonsLayout;
    }

    private void handleConfirmButtonClick() {
        modelOperations.closeReservationByKey(reservationCode);
        close(true);
    }

    private void close(boolean reservationWasClosed) {
        if (reservationWasClosed){
            root.updateTheaterPanel();
        }
        close();
    }
}
