package ui;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.*;
import model.comparators.LineComparator;
import model.comparators.SeatComparator;
import modeloperations.DataManager;
import modeloperations.ModelOperations;

import java.util.*;

/**
 * Created by niict on 24.12.2017.
 */
public class BookingWindow extends Window {
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
        //initControls();
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
                seatButton.setWidth(seat.getSeatType() == SeatType.GENERIC ? 10 : 20, Unit.PIXELS);
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
            }
            theaterLayout.addComponent(lineLayout);
        }
        theaterPanel.setContent(theaterLayout);
        mainLayout.addComponent(theaterPanel);
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
