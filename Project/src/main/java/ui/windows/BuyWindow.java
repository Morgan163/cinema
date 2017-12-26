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
public class BuyWindow extends Window {
    private ModelOperations modelOperations;
    private Collection<Seat> seatsForBuy;
    private Seance selectedSeance;
    private OperatorUI root;
    private VerticalLayout mainLayout;


    public BuyWindow(ModelOperations modelOperations, Collection<Seat> seatsForBuy, Seance selectedSeance, OperatorUI root) {
        super("Покупка");
        mainLayout = new VerticalLayout();
        this.modelOperations = modelOperations;
        this.seatsForBuy = seatsForBuy;
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
        double price = modelOperations.calculatePriceForSeatsAndSeance(seatsForBuy, selectedSeance);
        mainLayout.addComponent(new Label(String.format("Сумма к оплате - %2f рублей", price)));
        initButtons();
    }

    private void initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button buyButton = new Button("Покупка");
        Button cancelWindow = new Button("Отмена");
        buyButton.addClickListener((Button.ClickListener) clickEvent -> buyAndClose());
        cancelWindow.addClickListener((Button.ClickListener) clickEvent -> close());
        buttonsLayout.addComponents(buyButton, cancelWindow);
        mainLayout.addComponent(buttonsLayout);
    }

    private void buyAndClose() {
        modelOperations.buySeatsForSeance(seatsForBuy, selectedSeance);
        root.updateTheaterPanel();
        close();
    }
}
