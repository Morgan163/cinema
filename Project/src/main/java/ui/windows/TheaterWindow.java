package ui.windows;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.Line;
import model.Seat;
import model.SeatType;
import model.Theater;
import model.comparators.SeatComparator;
import model.user.User;
import model.user.UserRole;
import modeloperations.DataManager;
import org.apache.commons.collections4.IteratorUtils;
import ui.utils.LineSize;

import java.util.*;

public class TheaterWindow extends AbstractCreateWindow {
    private static final int GENERIC_BUTTON_WIDTH = 5;
    private static final int VIP_BUTTON_WIDTH = 11;
    private static final int MAX_LINES_COUNT = 15;
    private static final int MAX_SEATS_COUNT = 12;
    private static final int MIN_LINES_COUNT = 5;

    private Theater theater;
    private final List<HorizontalLayout> lines = new ArrayList<>();
    private final List<LineSize> size = new ArrayList<>();
    private int selectedLine;

    private final HorizontalLayout mainLayout = new HorizontalLayout();
    private final VerticalLayout panelLayout = new VerticalLayout();
    private final Panel panel = new Panel("Экран");
    private final Button newLineButton = new Button("Новый ряд");
    private final Button deleteLineButton = new Button("Удалить ряд");
    private final NativeSelect<Integer> lineNumberSelect = new NativeSelect<>("Текущий ряд");
    private final Button deleteSeatButton = new Button("Удалить место");
    private final Button genericButton = new Button();
    private final Button vipButton = new Button();
    private final Button saveButton = new Button("Сохранить");
    private final Button cancelButton = new Button("Отменить");

    private final Label genericLabel = new Label("Добавить стандартное место");
    private final Label vipLabel = new Label("Добавить VIP место");


    public TheaterWindow(UI rootUI, User user, DataManager dataManager, Theater theater) {
        super("Редактирование зала", rootUI, user, dataManager);
        this.theater = theater;
        initValues();
        init();
    }

    public TheaterWindow(UI rootUI, User user, DataManager dataManager) {
        super("Создание зала", rootUI, user, dataManager);
        init();
    }

    private void init() {
        mainLayout.setSizeUndefined();
        setResizable(false);
        setSizeFull();
        center();
        setModal(true);

        HorizontalLayout lineControlLayout = new HorizontalLayout();
        lineControlLayout.setSizeUndefined();
        newLineButton.addClickListener(e -> newLineButtonClickListener());
        deleteLineButton.addClickListener(e -> deleteLineButtonClickListener());
        lineControlLayout.addComponents(newLineButton, deleteLineButton);

        HorizontalLayout seatControlLayout = new HorizontalLayout();
        seatControlLayout.setSizeUndefined();
        initSelector();
        lineNumberSelect.addSelectionListener(e -> lineNumberSelectionListener());
        deleteSeatButton.addClickListener(e -> deleteSeatButtonClickListener());
        seatControlLayout.addComponents(lineNumberSelect, deleteSeatButton);

        HorizontalLayout genericLayout = new HorizontalLayout();
        genericLayout.setSizeUndefined();
        genericButton.setWidth(5, Unit.EM);
        genericButton.addClickListener(e -> genericButtonClickListener());
        genericLayout.addComponents(genericButton, genericLabel);

        HorizontalLayout vipLayout = new HorizontalLayout();
        vipLayout.setSizeUndefined();
        vipButton.setWidth(11, Unit.EM);
        vipButton.addClickListener(e -> vipButtonClickListener());
        vipLayout.addComponents(vipButton, vipLabel);

        saveButton.addClickListener(e -> saveButtonClickListener());
        cancelButton.addClickListener(e -> cancellButtonClickListener());

        VerticalLayout controlLayout = new VerticalLayout();
        controlLayout.addComponents(lineControlLayout, seatControlLayout, genericLayout, vipLayout, saveButton,
                cancelButton);

        panel.setContent(panelLayout);

        mainLayout.addComponents(controlLayout, panel);

        setContent(mainLayout);


    }

    private void initValues() {
        for (Line line : theater.getLines()) {
            HorizontalLayout newLineLayout = new HorizontalLayout();
            newLineLayout.setWidth("100%");
            List<Seat> seats = line.getSeats();
            seats.sort(new SeatComparator());
            int i = 0;
            for (Seat seat : seats) {
                Button seatButton = new Button(String.valueOf(seat.getNumber()));
                seatButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                seatButton.setWidth(seat.getSeatType() == SeatType.GENERIC ? GENERIC_BUTTON_WIDTH :
                        VIP_BUTTON_WIDTH, Unit.EM);
                i += seat.getSeatType() == SeatType.GENERIC ? 1 : 2;
                newLineLayout.addComponent(seatButton);
            }
            panelLayout.addComponent(newLineLayout);
            lines.add(newLineLayout);
            size.add(new LineSize(i));
        }
    }

    private void initSelector() {
        List<Integer> lineNumbers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            lineNumbers.add(i + 1);
        }
        lineNumberSelect.setItems(lineNumbers);
        lineNumberSelect.setSelectedItem(null);
    }

    private void newLineButtonClickListener() {
        if (lines.size() + 1 <= MAX_LINES_COUNT) {
            HorizontalLayout newLine = new HorizontalLayout();
            panelLayout.addComponent(newLine);
            lines.add(newLine);
            size.add(new LineSize(0));
            initSelector();
            panel.setContent(panelLayout);
        } else {
            showErrorWindow("Максимальное количество рядов = " + MAX_LINES_COUNT);
        }
    }

    private void deleteLineButtonClickListener() {
        if (lines.size() != 0) {
            if(lineNumberSelect.getSelectedItem().isPresent()) {
                int lineNumber = lineNumberSelect.getSelectedItem().get();
                panelLayout.removeComponent(lines.get(lineNumber - 1));
                panel.setContent(panelLayout);
                lines.remove(lineNumber - 1);
                size.remove(lineNumber - 1);
                initSelector();
            }else{
                showErrorWindow("Выберите ряд для удаления");
            }
        } else {
            showErrorWindow("Количество рядов = 0");
        }
    }

    private void lineNumberSelectionListener() {
        if ((lines.size() != 0)&&(selectedLine<lines.size())) {
            HorizontalLayout layout = lines.get(selectedLine);
            for (Component button : IteratorUtils.toList(layout.iterator())) {
                ((Button) button).setStyleName(ValoTheme.BUTTON_FRIENDLY);
            }
        }
        if (lineNumberSelect.getSelectedItem().isPresent()) {
            selectedLine = lineNumberSelect.getSelectedItem().get() - 1;
            HorizontalLayout layout = lines.get(selectedLine);
            for (Component button : IteratorUtils.toList(layout.iterator())) {
                ((Button) button).setStyleName(ValoTheme.BUTTON_PRIMARY);
            }
        }
    }

    private void deleteSeatButtonClickListener() {
        if (lineNumberSelect.getSelectedItem().isPresent()) {
            if (lines.get(lineNumberSelect.getSelectedItem().get() - 1).getComponentCount() != 0) {
                HorizontalLayout horizontalLayout = lines.get(lineNumberSelect.getSelectedItem().get() - 1);
                LineSize value = size.get(lineNumberSelect.getSelectedItem().get() - 1);
                value.setSize(value.getSize() - (horizontalLayout.getComponent(
                        horizontalLayout.getComponentCount() - 1).getWidth()
                        == GENERIC_BUTTON_WIDTH ? 1 : 2));
                horizontalLayout.removeComponent(horizontalLayout.getComponent(
                        horizontalLayout.getComponentCount() - 1));
            } else {
                showErrorWindow("Количество мест в ряду = 0");
            }
        } else {
            showErrorWindow("Выберите ряд для удаления мест");
        }
    }

    private void genericButtonClickListener() {
        addSeat(GENERIC_BUTTON_WIDTH);
    }

    private void vipButtonClickListener() {
        addSeat(VIP_BUTTON_WIDTH);
    }

    private void addSeat(int width) {
        if (lineNumberSelect.getSelectedItem().isPresent()) {
            if (size.get(lineNumberSelect.getSelectedItem().get() - 1).getSize() +
                    (width == GENERIC_BUTTON_WIDTH ? 1 : 2) <= MAX_SEATS_COUNT) {
                HorizontalLayout horizontalLayout = lines.get(lineNumberSelect.getSelectedItem().get() - 1);
                Button seatButton = new Button(String.valueOf(horizontalLayout.getComponentCount() + 1));
                seatButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
                seatButton.setWidth(width, Unit.EM);
                horizontalLayout.addComponent(seatButton);
                size.get(lineNumberSelect.getSelectedItem().get() - 1).setSize(size.get(lineNumberSelect.
                        getSelectedItem().get() - 1).getSize() + (width == GENERIC_BUTTON_WIDTH ? 1 : 2));
            } else {
                showErrorWindow("Максимальный размер ряда - " + MAX_SEATS_COUNT + " обычных или " +
                        MAX_SEATS_COUNT / 2 + " VIP мест");
            }
        } else {
            showErrorWindow("Выберете ряд для добавления мест мест");
        }
    }

    private void saveButtonClickListener() {
        if (lines.size() >= MIN_LINES_COUNT) {
            Theater newTheater = new Theater(super.getDataManager().getAllTheaters().size() + 1);
            if (theater != null) {
                newTheater.setTheaterNumber(theater.getTheaterNumber());
                newTheater.setTheaterID(theater.getTheaterID());
            }
            List<Line> newLines = new ArrayList<>();
            int i = 1;
            for (HorizontalLayout horizontalLayout : lines) {
                Line line = new Line(i, newTheater);
                if (theater != null) {
                    if (i - 1 < theater.getLines().size()) {
                        line.setLineID(theater.getLines().get(i - 1).getLineID());
                    }
                }
                int j = 1;
                List<Seat> seats = new ArrayList<>();
                for (Component button : IteratorUtils.toList(horizontalLayout.iterator())) {
                    Seat seat = new Seat(button.getWidth() == GENERIC_BUTTON_WIDTH ? SeatType.GENERIC : SeatType.VIP, line, j);
                    if (theater != null) {
                        if ((i - 1 < theater.getLines().size()) && (j - 1 < theater.getLines().get(i - 1).getSeats().size())) {
                            seat.setSeatID(theater.getLines().get(i - 1).getSeats().get(j - 1).getSeatID());
                        }
                    }
                    seats.add(seat);
                    j++;
                }
                line.setSeats(seats);
                i++;
                newLines.add(line);
            }
            newTheater.addLines(newLines);
            if (theater == null) {
                super.getDataManager().createTheater(newTheater);
            } else {
                super.getDataManager().updateTheater(newTheater);
            }
            UserRole role = super.getUser().getUserRole();
            if (role != null) {
                redirectRoot();
            }
        } else {
            showErrorWindow("Количество рядов должно быть не менее " + MIN_LINES_COUNT);
        }
    }

    private void cancellButtonClickListener() {
        close();
    }

    private void redirectRoot() {
        String currentLocation = super.getRootUI().getPage().getLocation().toString();
        super.getRootUI().getPage().setLocation(currentLocation.substring(0, currentLocation.lastIndexOf("/") + 1)
                + super.getUser().getUserRole().getRoleName().toLowerCase());
    }

    private void showErrorWindow(String message) {
        Window errorWindow = new ErrorWindow(message);
        UI.getCurrent().addWindow(errorWindow);
    }

}
