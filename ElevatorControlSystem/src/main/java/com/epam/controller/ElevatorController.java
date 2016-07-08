package com.epam.controller;

import com.epam.model.*;

/**
 * Created by Veranika_Zhvaleuskay on 7/7/2016.
 */
public class ElevatorController {
    public enum STATE {
       WAITING
    }

   public void buttonPressedOnTheFirstFloor(Building building, Floor floor, Button button) {
        System.out.println(String.format("Button pressed %s on the floor %s", button.getNumber(),
                floor.getNumber()));
        // send message to the lifting system of selected elevator
        Lift lift = building.selectFreeLift();
        //message to the elevator control
        lift.getControlPanel().goToTheFloor(1);
        if (lift != null) {
            lift.moveLiftToTheFloor(1);
            lift.getElevatorMotor().setCurrentFloor(1);
            lift.getControlPanel().setStateMovement(ElevatorControlPanel.STATE_MOVEMENT.DOWN);


        }
       lift.getControlPanel().goToTheFloor(button.getNumber());
       lift.moveLiftToTheFloor(button.getNumber());
       lift.getControlPanel().setStateMovement(ElevatorControlPanel.STATE_MOVEMENT.WAITING);

    }


    public Lift directionButtonPressedOnTheFloor(Building building, Floor floor, Floor.DIRECTION_BUTTONS buttonDirection) {
        System.out.println(String.format("Button pressed %s on the floor %s", buttonDirection,
                floor.getNumber()));
        // send message to the lifting system of selected elevator
        Lift lift = building.selectFreeLift();
        //message to the elevator control
        lift.getControlPanel().goToTheFloor(floor.getNumber());
        if (lift != null) {
            lift.moveLiftToTheFloor(floor.getNumber());
            lift.getElevatorMotor().setCurrentFloor(floor.getNumber());
            if (lift.getElevatorMotor().getCurrentFloor() < floor.getNumber()) {
                lift.getControlPanel().setStateMovement(ElevatorControlPanel.STATE_MOVEMENT.UP);
            } else {
                lift.getControlPanel().setStateMovement(ElevatorControlPanel.STATE_MOVEMENT.DOWN);
            }

        }
        return lift;
    }

    public void buttonPressedInTheLift(Building building, Lift lift, Button button) {
        System.out.println(String.format("Button pressed %s in the lift %s", button.getNumber(),
                lift.getNumber()));
        // send message to the lifting system of selected elevator

        lift.getControlPanel().goToTheFloor(button.getNumber());
        //message to the elevator control
        lift.moveLiftToTheFloor(button.getNumber());
        lift.getElevatorMotor().setCurrentFloor(button.getNumber());
        lift.getControlPanel().setStateMovement(ElevatorControlPanel.STATE_MOVEMENT.WAITING);
    }



}
