package com.epam.model;

/**
 * Created by Veranika_Zhvaleuskay on 7/5/2016.
 */
public class ElevatorMotor {
    private int currentFloor;

    public ElevatorMotor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
