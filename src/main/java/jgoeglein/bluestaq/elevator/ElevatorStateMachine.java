package jgoeglein.bluestaq.elevator;

public class ElevatorStateMachine {
    private ElevatorState currentState = ElevatorState.STOPPED;
    private Direction direction = Direction.IDLE;
    private int totalTime = 0;
    private final Elevator elevator;

    public ElevatorStateMachine(Elevator elevator) {
        this.elevator = elevator;
    }

    public boolean hasWork() {
        return !elevator.getPassengers().isEmpty() || !elevator.getWaiting().isEmpty();
    }

    public void doWork() {
        switch (currentState) {
            case STOPPED:
                openDoors();
                break;
            case DOORS_OPEN:
                startLoading();
                break;
            case LOADING:
                closeDoors();
                break;
            case DOORS_CLOSING:
                finishClosing();
                setTargetFloor();
                break;
            case HAS_TARGET_FLOOR:
                startMoving();
                break;
            case MOVING:
                stopMoving();
                break;
        }
    }

    private void openDoors() {
        if (currentState == ElevatorState.STOPPED) {
            currentState = ElevatorState.DOORS_OPEN;
            totalTime += elevator.getDoorOpenCloseSpeedS();
        }
    }

    private void startLoading() {
        if (currentState == ElevatorState.DOORS_OPEN) {
            currentState = ElevatorState.LOADING;
            processPassengers();
        }
    }

    private void processPassengers() {
        int currentFloor = elevator.getCurrentFloor();

        // Unload passengers at current floor
        int passengersToUnload = elevator.getPassengers().getOrDefault(currentFloor, 0);
        if (passengersToUnload > 0) {
            elevator.getPassengers().remove(currentFloor);
        }

        // Load waiting passengers at current floor
        int passengersToLoad = 0;
        if (elevator.getWaiting().containsKey(currentFloor)) {
            int waitingCount = elevator.getWaiting().get(currentFloor);
            int currentPassengerCount =
                    elevator.getPassengers().values().stream().mapToInt(Integer::intValue).sum();
            int availableSpace = elevator.getMaxPassengers() - currentPassengerCount;
            passengersToLoad = Math.min(waitingCount, availableSpace);

            if (passengersToLoad > 0) {
                if (currentFloor == 1) {
                    // Assign each passenger from first floor to a random destination floor 2 or
                    // higher
                    for (int i = 0; i < passengersToLoad; i++) {
                        int destinationFloor =
                                2 + (int) (Math.random() * (elevator.getFloors() - 1));

                        elevator.getPassengers()
                                .put(
                                        destinationFloor,
                                        elevator.getPassengers().getOrDefault(destinationFloor, 0)
                                                + 1);
                    }
                    direction = Direction.UP;
                } else {
                    // Passengers from other floors go to first floor
                    elevator.getPassengers()
                            .put(1, elevator.getPassengers().getOrDefault(1, 0) + passengersToLoad);
                    direction = Direction.DOWN;
                }
            }

            if (passengersToLoad == waitingCount) {
                elevator.getWaiting().remove(currentFloor);
            } else {
                elevator.getWaiting().put(currentFloor, waitingCount - passengersToLoad);
            }
        }
        if (elevator.getPassengers().isEmpty() && elevator.getWaiting().isEmpty()) {
            direction = Direction.IDLE;
        }

        totalTime += (passengersToUnload + passengersToLoad) * elevator.getPersonOnOffSpeedS();
    }

    private void closeDoors() {
        if (currentState == ElevatorState.LOADING) {
            currentState = ElevatorState.DOORS_CLOSING;
            totalTime += elevator.getDoorOpenCloseSpeedS();
        }
    }

    private void finishClosing() {
        if (currentState == ElevatorState.DOORS_CLOSING) {
            currentState = ElevatorState.STOPPED;
        }
    }

    private void setTargetFloor() {
        if (currentState == ElevatorState.STOPPED) {
            Integer target = determineNextTarget();
            elevator.setTargetFloor(target);
            if (target != null) {
                currentState = ElevatorState.HAS_TARGET_FLOOR;
            }
        }
    }

    private Integer determineNextTarget() {
        if (elevator.getPassengers().size() > 0 && direction != Direction.IDLE) {
            if (direction == Direction.UP) {
                return elevator.getPassengers().keySet().stream()
                        .filter(floor -> floor > elevator.getCurrentFloor())
                        .min(Integer::compareTo)
                        .orElse(null);
            } else {
                // Going DOWN: check both passengers and waiting passengers, pick closest
                Integer passengerFloor =
                        elevator.getPassengers().keySet().stream()
                                .filter(floor -> floor < elevator.getCurrentFloor())
                                .max(Integer::compareTo)
                                .orElse(null);
                Integer waitingFloor =
                        elevator.getWaiting().keySet().stream()
                                .filter(floor -> floor < elevator.getCurrentFloor())
                                .max(Integer::compareTo)
                                .orElse(null);

                if (passengerFloor == null) return waitingFloor;
                if (waitingFloor == null) return passengerFloor;
                return Math.max(passengerFloor, waitingFloor);
            }
        } else {
            // Go to highest floor with waiting passengers
            return elevator.getWaiting().keySet().stream().max(Integer::compareTo).orElse(null);
        }
    }

    private void startMoving() {
        if (currentState == ElevatorState.HAS_TARGET_FLOOR && elevator.getTargetFloor() != null) {
            currentState = ElevatorState.MOVING;
            int floors = Math.abs(elevator.getTargetFloor() - elevator.getCurrentFloor());
            totalTime += floors * elevator.getSpeedPerFloorS();
            elevator.setCurrentFloor(elevator.getTargetFloor());
        }
    }

    private void stopMoving() {
        if (currentState == ElevatorState.MOVING) {
            currentState = ElevatorState.STOPPED;
            elevator.setTargetFloor(null);
        }
    }

    public ElevatorState getCurrentState() {
        return currentState;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
