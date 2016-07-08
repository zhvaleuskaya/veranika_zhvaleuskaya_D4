package com.epam.model;

import java.util.List;
import java.util.Random;

/**
 * Created by Veranika_Zhvaleuskay on 7/5/2016.
 */
public class Lift {
    private ElevatorMotor elevatorMotor;
    private ElevatorControlPanel controlPanel;
    private int speed;
    private int number;

    public Lift(int mFloors,int number) {
        this.elevatorMotor = new ElevatorMotor(mFloors);
        this.controlPanel = new ElevatorControlPanel(mFloors);
        Random rn = new Random();
        this.speed = rn.nextInt(10);
        this.number = number;
    }

    public void moveLiftToTheFloor(int floorNumber) {
        System.out.println(String.format("Lift %s moved from %s to the floor %s",this.getNumber() ,this.getElevatorMotor().getCurrentFloor(), floorNumber) );
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ElevatorMotor getElevatorMotor() {
        return elevatorMotor;
    }

    public void setElevatorMotor(ElevatorMotor elevatorMotor) {
        this.elevatorMotor = elevatorMotor;
    }

    public ElevatorControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ElevatorControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
