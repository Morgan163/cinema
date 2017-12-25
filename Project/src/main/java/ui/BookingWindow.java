package ui;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import exceptions.SendMailException;
import model.*;
import model.comparators.LineComparator;
import model.comparators.SeatComparator;
import modeloperations.DataManager;
import modeloperations.ModelOperations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niict on 24.12.2017.
 */
public class BookingWindow extends Window {
    private static final String SMTH_WRONG = "К сожалению, что-то пошло не так. Попробуйте позже";
    private static final String ILLEGAL_EMAIL = "Неверный формат адреса!";
    private static final String BOOKED = "Места успешно забронированы. \nКод подтверждения выслан на почтовый ящик";
    private Theater theater;
    private Seance seance;
    private DataManager dataManager;
    private ModelOperations modelOperations;
    private HorizontalLayout mainLayout;
    private List<Seat> selectedSeats;

    public BookingWindow(Seance seance, DataManager dataManager, ModelOperations modelOperations) {
        super("Бронирование билетов");
        this.seance = seance;
        this.dataManager = dataManager;
        this.modelOperations = modelOperations;
        selectedSeats = new ArrayList<>();
        init();
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        setSizeUndefined();
        center();
        setModal(true);
    }

    private void init() {
        mainLayout = new HorizontalLayout();
        initTheaterPanel();
        initControls();
        setContent(mainLayout);
    }

    private void initTheaterPanel(){
        Panel theaterPanel = new Panel(seance.getFilm().getFilmName());
        Collection<SeatSeanceStatusMapper> mappersBySeance = dataManager.getSeatSeanceStatusMappersBySeance(seance);
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
                SeatButton seatButton = new SeatButton(String.valueOf(seat.getNumber()), seat);
                seatButton.setWidth(seat.getSeatType() == SeatType.GENERIC ? 5 : 11, Unit.EM);
                for (SeatSeanceStatusMapper mapper: mappersBySeance){
                    if (seat.getSeatID() == mapper.getSeat().getSeatID()){
                        if (mapper.getSeatSeanceStatus() == SeatSeanceStatus.FREE) {
                            seatButton.setEnabled(true);
                            seatButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                        }
                        else {
                            seatButton.setEnabled(false);
                        }
                    }

                }
                seatButton.addClickListener(this::handleClickSeatButtonEvent);
                lineLayout.addComponent(seatButton);
                lineLayout.setWidth("100%");
            }
            theaterLayout.addComponent(lineLayout);
        }
        theaterPanel.setContent(theaterLayout);
        mainLayout.addComponent(theaterPanel);
    }

    private void initControls() {
        VerticalLayout controlLayout = new VerticalLayout();
        HorizontalLayout genericSeatLayout = new HorizontalLayout();
        Button genericButton = new Button();
        genericButton.setWidth(5, Unit.EM);
        Label genericLabel = new Label("Стандарт- " + seance.getPriceValue() + " рублей");
        HorizontalLayout vipSeatLayout = new HorizontalLayout();
        Button vipButton = new Button();
        vipButton.setWidth(11, Unit.EM);
        Label vipLabel = new Label("VIP - " + seance.getPriceValue()*1.5 + " рублей");
        TextField email = new TextField("Email:");
        Button bookButton = new Button("Бронь");
        Button closeButton = new Button("Отмена");
        bookButton.addClickListener(clickEvent -> handleClickBookButton(email.getValue()));
        closeButton.addClickListener(clickEvent -> close());
        genericSeatLayout.addComponents(genericButton, genericLabel);
        vipSeatLayout.addComponents(vipButton, vipLabel);
        controlLayout.addComponents(genericSeatLayout, vipSeatLayout, email, bookButton, closeButton);
        mainLayout.addComponents(controlLayout);
    }

    private void handleClickBookButton(String email) {
        Pattern p = Pattern.compile(".+@.+\\..+");
        Matcher m = p.matcher(email);
        if (m.find()) {
            try {
                modelOperations.bookSeatsForSeance(selectedSeats, seance, email);
                showInfoWindow(BOOKED);
            } catch (SendMailException e) {
                showErrorWindow(SMTH_WRONG);
            }
        } else {
            showErrorWindow(ILLEGAL_EMAIL);
        }
    }

    private void showErrorWindow(String message) {
        Window errorWindow = new ErrorWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }

    private void showInfoWindow(String message) {
        Window errorWindow = new InfoWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }

    private void handleClickSeatButtonEvent(Button.ClickEvent clickEvent) {
        SeatButton sourceButton = (SeatButton)clickEvent.getButton();
        if (sourceButton.getStyleName().equals(ValoTheme.BUTTON_DANGER)){
            selectedSeats.remove(sourceButton.getSeat());
            sourceButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        }
        else {
            sourceButton.setStyleName(ValoTheme.BUTTON_DANGER);
            selectedSeats.add(sourceButton.getSeat());
        }
    }

    private class SeatButton extends Button{
        private Seat seat;

        public SeatButton(String caption, Seat seat) {
            super(caption);
            this.seat = seat;
        }

        public Seat getSeat(){
            return seat;
        }
    }
}
