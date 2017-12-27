package ui.windows;

import com.vaadin.ui.*;
import model.Seance;
import model.Seat;
import modeloperations.ModelOperations;
import ui.OperatorUI;

import java.util.Collection;

/**
 * Created by niict on 25.12.2017.
 */
public class RefundWindow extends Window {
    private ModelOperations modelOperations;
    private Collection<Seat> seatsForRefund;
    private Seance selectedSeance;
    private OperatorUI root;
    private VerticalLayout mainLayout;


    public RefundWindow(ModelOperations modelOperations, Collection<Seat> seatsForRefund, Seance selectedSeance, OperatorUI root) {
        super("Возврат");
        mainLayout = new VerticalLayout();
        this.modelOperations = modelOperations;
        this.seatsForRefund = seatsForRefund;
        this.selectedSeance = selectedSeance;
        this.root = root;
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
        double price = modelOperations.calculatePriceForSeatsAndSeance(seatsForRefund, selectedSeance);
        mainLayout.addComponent(new Label(String.format("Сумма к возврату - %.2f рублей", price)));
        initButtons();
    }

    private void initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button refundButton = new Button("Возврат");
        Button cancelWindow = new Button("Отмена");
        refundButton.addClickListener((Button.ClickListener) clickEvent -> refundAndClose());
        cancelWindow.addClickListener((Button.ClickListener) clickEvent -> close());
        buttonsLayout.addComponents(refundButton, cancelWindow);
        mainLayout.addComponent(buttonsLayout);
    }

    private void refundAndClose() {
        modelOperations.refund(seatsForRefund, selectedSeance);
        root.updateTheaterPanel();
        close();
    }
}
