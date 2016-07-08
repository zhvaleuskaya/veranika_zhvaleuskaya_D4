package com.epam.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veranika_Zhvaleuskay on 7/6/2016.
 */
public class Building {

    private List<Floor> floors = new ArrayList<Floor>();

    private List<Lift> lifts = new ArrayList<Lift>();

    private int mFloors;

    private int nLifts;

    public Building(int mFloors, int nLifts) {
        this.mFloors = mFloors;
        this.nLifts = nLifts;
        for (int i = 0; i < mFloors; ++i) {
            if (i == 0) {
                Floor floor = new Floor(i+1);
                floors.add(floor);
                floor.addControlPanel(mFloors);
            } else {
                floors.add(new Floor(i+1));
            }
        }

        for (int i = 0; i < nLifts; ++i) {
            lifts.add(new Lift(mFloors, i + 1));
        }
    }

    public Lift selectFreeLift() {
        for (Lift lift:lifts) {
            if (lift.getControlPanel().getStateMovement() == ElevatorControlPanel.STATE_MOVEMENT.WAITING) {
                return lift;
            }
        }
        return null;
    }


    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber-1);
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Lift> getLifts() {
        return lifts;
    }

    public void setLifts(List<Lift> lifts) {
        this.lifts = lifts;
    }

    public int getmFloors() {
        return mFloors;
    }

    public void setmFloors(int mFloors) {
        this.mFloors = mFloors;
    }

    public int getnLifts() {
        return nLifts;
    }

    public void setnLifts(int nLifts) {
        this.nLifts = nLifts;
    }
}