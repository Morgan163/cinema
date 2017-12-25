package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.*;
import model.comparators.LineComparator;
import model.comparators.SeatComparator;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import modeloperations.ModelOperations;
import org.apache.commons.collections4.CollectionUtils;
import ui.windows.RefundWindow;
import ui.windows.BuyWindow;
import ui.windows.CloseReservationWindow;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


/**
 * Created by niict on 24.12.2017.
 */
@CDIUI("operator")
@Theme("mytheme")
public class OperatorUI extends UI {
    @Inject
    private User user;
    @Inject
    private DataManager dataManager;
    @Inject
    private ModelOperations modelOperations;
    private Collection<Seance> seances;
    private VerticalLayout mainLayout;
    private VerticalLayout theaterLayout;
    private Collection<Seat> selectedSeatsForBuy;
    private Collection<Seat> selectedSeatsForRefund;
    private Seance selectedSeance;
    private Label genericPriceLabel;
    private Label vipPriceLabel;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        genericPriceLabel = new Label();
        vipPriceLabel = new Label();
        checkUserRoleAndRedirectIfNeeded();
        mainLayout = new VerticalLayout();
        initTopBar();
        initContent();
        setContent(mainLayout);
    }

    private void initTopBar() {
        HorizontalLayout topBarLayout = new HorizontalLayout();
        topBarLayout.addComponent(buildCloseReservationButton());
        Label userName = new Label(user.getLogin());
        topBarLayout.addComponent(userName);
        topBarLayout.setComponentAlignment(userName, Alignment.MIDDLE_CENTER);
        Button loginButton = buildLogoutButton();
        topBarLayout.addComponent(loginButton);
        topBarLayout.setComponentAlignment(loginButton, Alignment.MIDDLE_RIGHT);
        topBarLayout.setSizeFull();
        mainLayout.addComponent(topBarLayout);
    }

    private Button buildCloseReservationButton() {
        Button closeReservationButton = new Button("Закрыть бронь");
        closeReservationButton.addClickListener(clickEvent -> showCloseReservationWindow());
        return closeReservationButton;
    }

    private void showCloseReservationWindow() {
        Window closeReservationWindow = new CloseReservationWindow(modelOperations, this);
        addWindow(closeReservationWindow);
    }

    private Button buildLogoutButton() {
        Button logoutButton = new Button("Выход");
        logoutButton.addClickListener(clickEvent -> logOut());
        return logoutButton;
    }

    private void logOut() {
        user.setLogin("");
        user.setPassword("");
        user.setUserID(-1);
        user.setUserRole(null);
        checkUserRoleAndRedirectIfNeeded();
    }

    private void initContent() {
        VerticalLayout content = new VerticalLayout();
        content.addComponent(buildSeancesComboBox());
        content.addComponent(buildControls());
        mainLayout.addComponent(content);
    }

    private HorizontalLayout buildControls() {
        HorizontalLayout controls = new HorizontalLayout();
        controls.addComponent(theaterLayout);
        controls.addComponent(buildDescriptionAndButtons());
        return controls;
    }

    private VerticalLayout buildDescriptionAndButtons() {
        VerticalLayout descriptionAndButtons = new VerticalLayout();
        HorizontalLayout genericSeatLayout = new HorizontalLayout();
        Button genericButton = new Button();
        genericButton.setWidth(5, Unit.EM);
        HorizontalLayout vipSeatLayout = new HorizontalLayout();
        Button vipButton = new Button();
        vipButton.setWidth(11, Unit.EM);
        Button buyButton = new Button("Покупка");
        Button refundButton = new Button("Возврат");
        Button clearButton = new Button("Сброс");
        buyButton.addClickListener(clickEvent -> handleClickBuyButton());
        refundButton.addClickListener(clickEvent -> handleClickRefundButton());
        clearButton.addClickListener(clickEvent -> handleClickClearButton());
        genericSeatLayout.addComponents(genericButton, genericPriceLabel);
        vipSeatLayout.addComponents(vipButton, vipPriceLabel);
        descriptionAndButtons.addComponents(genericSeatLayout, vipSeatLayout, buyButton, refundButton, clearButton);
        return  descriptionAndButtons;
    }

    private void handleClickClearButton() {
        redrawForSeance(selectedSeance);
    }

    private void handleClickRefundButton() {
        showRefundWindow();
    }

    private void showBuyWindow() {
        if (CollectionUtils.isNotEmpty(selectedSeatsForBuy)) {
            Window buyWindow = new BuyWindow(modelOperations, selectedSeatsForBuy, selectedSeance, this);
            addWindow(buyWindow);
        }
    }

    private void handleClickBuyButton() {
        showBuyWindow();
    }

    private void showRefundWindow() {
        if (CollectionUtils.isNotEmpty(selectedSeatsForRefund)) {
            Window refundWindow = new RefundWindow(modelOperations, selectedSeatsForRefund, selectedSeance, this);
            addWindow(refundWindow);
        }
    }

    private ComboBox<Seance> buildSeancesComboBox() {
        seances = dataManager.getAllSeances();
        ComboBox<Seance> seancesComboBox =  new ComboBox<>("Сеанс: ");
        seancesComboBox.setItems(seances);
        seancesComboBox.setItemCaptionGenerator((ItemCaptionGenerator<Seance>) this::applyNameForSeance);
        seancesComboBox.addValueChangeListener(valueChangeEvent -> {
            redrawForSeance(valueChangeEvent.getValue());
            selectedSeance = valueChangeEvent.getValue();
        });
        if (seances.size() > 0){
            selectedSeance = seances.iterator().next();
            seancesComboBox.setSelectedItem(selectedSeance);
        }
        seancesComboBox.setWidth("100%");
        return seancesComboBox;
    }

    private String applyNameForSeance(Seance seance){
        return seance.getFilm().getFilmName()+ " - " +seance.getSeanceStartDate().getTime();
    }

    private void checkUserRoleAndRedirectIfNeeded(){
        if (user.getUserRole() == null || !user.getUserRole().equals(UserRole.OPERATOR)){
            redirectToMainPage();
        }
    }

    private void redirectToMainPage(){
        String currentLocation = getUI().getPage().getLocation().toString();
        String newLocation = currentLocation.substring(0, currentLocation.lastIndexOf("/"));
        getUI().getPage().setLocation(newLocation);
    }

    public void updateTheaterPanel(){
        redrawForSeance(selectedSeance);
    }

    private void redrawForSeance(Seance selectedSeance) {
        if (selectedSeance == null){
            return;
        }
        if (theaterLayout == null){
            theaterLayout = new VerticalLayout();
        }
        theaterLayout.removeAllComponents();
        theaterLayout.addComponent(buildTheaterPanel(selectedSeance));
        genericPriceLabel.setCaption("Стандарт - " + selectedSeance.getPriceValue() + " рублей");
        vipPriceLabel.setCaption("VIP - " + selectedSeance.getPriceValue()*1.5 + " рублей");
    }

    private Panel buildTheaterPanel(Seance selectedSeance) {
        selectedSeatsForBuy = new ArrayList<>();
        selectedSeatsForRefund = new ArrayList<>();
        Panel theaterPanel = new Panel(selectedSeance.getFilm().getFilmName());
        Collection<SeatSeanceStatusMapper> mappersBySeance = dataManager.getSeatSeanceStatusMappersBySeance(selectedSeance);
        Collection<Line> lines = new HashSet<>();
        for(SeatSeanceStatusMapper mapper: mappersBySeance){
            lines.add(dataManager.getLineBySeat(mapper.getSeat()));
        }
        List<Line> lineList = new ArrayList<>(lines);
        lineList.sort(new LineComparator());
        VerticalLayout theaterLayout = new VerticalLayout();
        for (Line line : lines){
            HorizontalLayout lineLayout = new HorizontalLayout();
            List<Seat> seats = line.getSeats();
            seats.sort(new SeatComparator());
            for (Seat seat : seats){
                Button seatButton = new Button(String.valueOf(seat.getNumber()));
                seatButton.setWidth(seat.getSeatType() == SeatType.GENERIC ? 5 : 11, Unit.EM);
                for (SeatSeanceStatusMapper mapper: mappersBySeance){
                    if (seat.getSeatID() == mapper.getSeat().getSeatID()){
                        if (mapper.getSeatSeanceStatus() == SeatSeanceStatus.FREE) {
                            seatButton.setEnabled(true);
                            seatButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                        }
                        else if (mapper.getSeatSeanceStatus() == SeatSeanceStatus.SOLD_OUT){
                            seatButton.setEnabled(true);
                            seatButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
                        }
                        else {
                            seatButton.setEnabled(false);
                        }
                    }

                }
                seatButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        handleClickSeatButtonEvent(clickEvent, seat);
                    }
                });
                lineLayout.addComponent(seatButton);
                lineLayout.setWidth("100%");
            }
            theaterLayout.addComponent(lineLayout);
        }
        theaterPanel.setContent(theaterLayout);
        return theaterPanel;
    }

    private void handleClickSeatButtonEvent(Button.ClickEvent clickEvent, Seat seat) {
        Button sourceButton = clickEvent.getButton();
        if (sourceButton.getStyleName().equals(ValoTheme.BUTTON_DANGER)){
            selectedSeatsForBuy.remove(seat);
            sourceButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        }
        else if (sourceButton.getStyleName().equals(ValoTheme.BUTTON_FRIENDLY)){
            sourceButton.setStyleName(ValoTheme.BUTTON_DANGER);
            selectedSeatsForBuy.add(seat);
        }
        if (sourceButton.getStyleName().equals(ValoTheme.BUTTON_BORDERLESS)){
            selectedSeatsForRefund.add(seat);
            sourceButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        }
        else if (sourceButton.getStyleName().equals(ValoTheme.BUTTON_PRIMARY)){
            sourceButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            selectedSeatsForRefund.remove(seat);
        }
    }
}
