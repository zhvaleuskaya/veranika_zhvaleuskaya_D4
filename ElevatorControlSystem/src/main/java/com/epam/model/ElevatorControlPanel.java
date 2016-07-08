package com.epam.model;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Veranika_Zhvaleuskay on 7/5/2016.
 */
public class ElevatorControlPanel {
    public enum STATE_MOVEMENT {
        UP, DOWN, WAITING
    }

    private List<Button> buttons;

    private Monitor monitor;

    private STATE_MOVEMENT stateMovement;

    public ElevatorControlPanel(int mFloors) {
        List<Button> buttons = new ArrayList<Button>();
        for (int i = 0; i < mFloors; ++i) {
            buttons.add(new Button(i + 1));
        }
        this.setButtons(buttons);
        stateMovement = STATE_MOVEMENT.WAITING;

    }


    public STATE_MOVEMENT getStateMovement() {
        return stateMovement;
    }

    public void setStateMovement(STATE_MOVEMENT stateMovement) {
        this.stateMovement = stateMovement;
    }

    public void goToTheFloor(int floorNumber) {
        System.out.println(String.format("Lift goes to Floor %s", floorNumber));
     }


    public Button getButton(int buttonNumber ) {
        return buttons.get(buttonNumber - 1);
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public List<Button> getButtons() {
        return buttons;
    }
}