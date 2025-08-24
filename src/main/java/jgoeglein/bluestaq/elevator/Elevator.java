package jgoeglein.bluestaq.elevator;

import java.util.Map;

public class Elevator {

    public Elevator() {}

    public Elevator(
            int floors,
            int maxPassengers,
            int currentFloor,
            Integer targetFloor,
            Map<Integer, Integer> waiting,
            Map<Integer, Integer> passengers,
            int speedPerFloorS,
            int doorOpenCloseSpeedS,
            int personOnOffSpeedS) {
        this.floors = floors;
        this.maxPassengers = maxPassengers;
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
        this.waiting = waiting;
        this.passengers = passengers;
        this.speedPerFloorS = speedPerFloorS;
        this.doorOpenCloseSpeedS = doorOpenCloseSpeedS;
        this.personOnOffSpeedS = personOnOffSpeedS;
    }

    private int floors;
    private int maxPassengers;
    private int currentFloor;
    private Integer targetFloor;
    private Map<Integer, Integer> waiting;
    private Map<Integer, Integer> passengers;
    private int speedPerFloorS;
    private int doorOpenCloseSpeedS;
    private int personOnOffSpeedS;

    public Integer getTargetFloor() {
        return this.targetFloor;
    }

    public void setTargetFloor(Integer targetFloor) {
        this.targetFloor = targetFloor;
    }

    public Map<Integer, Integer> getWaiting() {
        return this.waiting;
    }

    public void setWaiting(Map<Integer, Integer> waiting) {
        this.waiting = waiting;
    }

    public Map<Integer, Integer> getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Map<Integer, Integer> passengers) {
        this.passengers = passengers;
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getFloors() {
        return this.floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getSpeedPerFloorS() {
        return this.speedPerFloorS;
    }

    public void setSpeedPerFloorS(int speedPerFloorS) {
        this.speedPerFloorS = speedPerFloorS;
    }

    public int getMaxPassengers() {
        return this.maxPassengers;
    }

    public void setMaxPassengers(int capacityKG) {
        this.maxPassengers = capacityKG;
    }

    public int getDoorOpenCloseSpeedS() {
        return this.doorOpenCloseSpeedS;
    }

    public void setDoorOpenCloseSpeedS(int doorOpenCloseSpeedS) {
        this.doorOpenCloseSpeedS = doorOpenCloseSpeedS;
    }

    public int getPersonOnOffSpeedS() {
        return this.personOnOffSpeedS;
    }

    public void setPersonOnOffSpeedS(int personOnOffSpeedS) {
        this.personOnOffSpeedS = personOnOffSpeedS;
    }

    public Elevator floors(int floors) {
        setFloors(floors);
        return this;
    }

    public Elevator maxPassengers(int maxPassengers) {
        setMaxPassengers(maxPassengers);
        return this;
    }

    public Elevator currentFloor(int currentFloor) {
        setCurrentFloor(currentFloor);
        return this;
    }

    public Elevator targetFloor(Integer targetFloor) {
        setTargetFloor(targetFloor);
        return this;
    }

    public Elevator waiting(Map<Integer, Integer> waiting) {
        setWaiting(waiting);
        return this;
    }

    public Elevator passengers(Map<Integer, Integer> passengers) {
        setPassengers(passengers);
        return this;
    }

    public Elevator speedPerFloorS(int speedPerFloorS) {
        setSpeedPerFloorS(speedPerFloorS);
        return this;
    }

    public Elevator doorOpenCloseSpeedS(int doorOpenCloseSpeedS) {
        setDoorOpenCloseSpeedS(doorOpenCloseSpeedS);
        return this;
    }

    public Elevator personOnOffSpeedS(int personOnOffSpeedS) {
        setPersonOnOffSpeedS(personOnOffSpeedS);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int floor = floors; floor >= 1; floor--) {
            sb.append("Floor ").append(floor).append(": ");
            if (floor == currentFloor) {
                sb.append("[E] ");
            } else {
                sb.append("    ");
            }
            int waitingCount = waiting != null ? waiting.getOrDefault(floor, 0) : 0;
            sb.append("x".repeat(waitingCount));
            sb.append("\n");
        }
        return sb.toString();
    }
}
