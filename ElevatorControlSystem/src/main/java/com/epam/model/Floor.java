package com.epam.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Veranika_Zhvaleuskay on 7/5/2016.
 */
public class Floor {
    public enum DIRECTION_BUTTONS {
        UP, DOWN
    }

    private int number;
    private int height;
    private List<DIRECTION_BUTTONS> directionButtons;
    private ElevatorControlPanel controlPanel;

    public Floor(int number) {
        this.number = number;
    }

    public void addControlPanel(int controlPanelFloors) {
        controlPanel = new ElevatorControlPanel(controlPanelFloors);
        List<Button> buttons = new ArrayList<Button>();
        for (int i = 0; i < controlPanelFloors; ++i) {
            buttons.add(new Button(i + 1));
        }
        controlPanel.setButtons(buttons);

    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ElevatorControlPanel getControlPanel() {
        return controlPanel;
    }

    public void addControlPanel(ElevatorControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }
}
