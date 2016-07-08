package com.epam.test;

import com.epam.controller.ElevatorController;
import com.epam.model.Building;
import com.epam.model.Button;
import com.epam.model.Floor;
import com.epam.model.Lift;

import java.util.List;

/**
 * Created by Veranika_Zhvaleuskay on 7/5/2016.
 */
public class TestClass {


    public static void main(String[] args) {

        ElevatorController elevatorController = new ElevatorController();
        Building building = new Building(Integer.valueOf(args[0]), Integer.valueOf(args[1]));

        Floor firstFloor = building.getFloor(1);
        Button destinationButtonFromFirstFloor = firstFloor.getControlPanel().getButton(8);;
        elevatorController.buttonPressedOnTheFirstFloor(building, firstFloor, destinationButtonFromFirstFloor);





        Floor floor = building.getFloor(2);
        Lift lift = elevatorController.directionButtonPressedOnTheFloor(building, floor, Floor.DIRECTION_BUTTONS.UP);
        Button destinationButton = lift.getControlPanel().getButton(4);
        elevatorController.buttonPressedInTheLift(building, lift, destinationButton);

        //Floor thirdFloor = building.getFloor(3);
        //elevatorController.buttonPressedOnTheFloor(building, thirdFloor, Floor.DIRECTION_BUTTONS.UP);
    }


}